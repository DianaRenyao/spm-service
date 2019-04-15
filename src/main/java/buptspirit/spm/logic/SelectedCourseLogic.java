package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.*;
import org.apache.logging.log4j.Logger;

import buptspirit.spm.persistence.facade.SelectedCourseFacade;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class SelectedCourseLogic {

    @Inject
    private SelectedCourseFacade selectedCourseFacade;

    @Inject
    private MessageMapper messageMapper;

    @Inject
    private Logger logger;

    public SelectedCourseMessage getScore(int studentId, int courseId) {
        SelectedCourseEntity selectedCourseEntity = transactional(
                em -> selectedCourseFacade.findBystudentUserIdAndCourseId(em, studentId, courseId),
                "failed to find selected course"
        );
        return transactional(
                em -> messageMapper.intoScoreMessage(em, selectedCourseEntity),
                "failed to convert course to message"
        );
    }

    public List<SelectedCourseMessage> getStudentSelectedCourses(String username) {
        return transactional(
                em -> selectedCourseFacade.findBystudentUserName(em, username).stream().map(
                        score -> messageMapper.intoScoreMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find selected courses"
        );
    }

    public List<SelectedCourseMessage> getTeacherSelectedCourses(int id) {
        return transactional(
                em -> selectedCourseFacade.findBycourseCourseId(em, id).stream().map(
                        score -> messageMapper.intoScoreMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find selected courses"
        );
    }

    public SelectedCourseMessage createSelectedCourse(SelectedCourseCreationMessage selectedCourseCreationMessage,
                                                      int studentUserId,
                                                      int courseCourseId) throws ServiceException, ServiceAssertionException {

        SelectedCourseEntity selectedCourseEntity = transactional(
                em -> selectedCourseFacade.findBystudentUserIdAndCourseId(em, studentUserId, courseCourseId),
                "failed to find selected courses"
        );

        selectedCourseEntity.setAvgOnlineScore(selectedCourseCreationMessage.getAvgOnlineScore());
        selectedCourseEntity.setMidScore(selectedCourseCreationMessage.getMidScore());
        selectedCourseEntity.setFinalScore(selectedCourseCreationMessage.getFinalScore());

        // selectedCourseEntity.setTotalScore(selectedCourseCreationMessage.getTotalScore());
        // TODO 验证
        return transactional(
                em -> {
                    selectedCourseFacade.edit(em, selectedCourseEntity);
                    return messageMapper.intoScoreMessage(em, selectedCourseEntity);
                },
                "failed to add selected course"
        );
    }

    // TODO delete
    public boolean addTotalScore(SelectedCourseCreationMessage selectedCourseCreationMessage,
                                               int studentUserId,
                                               int courseId) {

            SelectedCourseEntity selectedCourseEntity = transactional(
                    em -> selectedCourseFacade.findBystudentUserIdAndCourseId(em, studentUserId,courseId),
                    "failed to find selected course"
            );
            BigDecimal tempAvgOnlineScore = selectedCourseEntity.getAvgOnlineScore().multiply(new BigDecimal(0.3));
            BigDecimal tempMidScore = selectedCourseEntity.getMidScore().multiply(new BigDecimal(0.1));
            BigDecimal tempFinalScore = selectedCourseEntity.getFinalScore().multiply(new BigDecimal(0.6));
            BigDecimal totalScore = tempFinalScore.add(tempAvgOnlineScore.add(tempMidScore)).setScale(1,BigDecimal.ROUND_HALF_DOWN);
            if(totalScore.equals(selectedCourseCreationMessage.getTotalScore())){
                selectedCourseEntity.setTotalScore(totalScore);
                transactional(
                        em -> {
                            selectedCourseFacade.edit(em, selectedCourseEntity);
                            return messageMapper.intoScoreMessage(em, selectedCourseEntity);
                        },
                        "failed to add score"
                );
                return true;
            }
            else return false;
    }
}
