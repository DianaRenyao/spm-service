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

    public List<SelectedCourseMessage> getAllScores() {
        return transactional(
                em -> selectedCourseFacade.findAll(em).stream().map(
                        score -> messageMapper.intoScoreMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find scores"
        );
    }

    public SelectedCourseMessage getScore(int studentId, int courseId) {
        SelectedCourseEntity selectedCourseEntity = transactional(
                em -> selectedCourseFacade.findBystudentUserIdAndCourseId(em, studentId, courseId),
                "failed to find score"
        );
        return transactional(
                em -> messageMapper.intoScoreMessage(em, selectedCourseEntity),
                "failed to convert course to message"
        );
    }


    public List<SelectedCourseMessage> getStudentScores(String username) {
        return transactional(
                em -> selectedCourseFacade.findBystudentUserName(em, username).stream().map(
                        score -> messageMapper.intoScoreMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find scores"
        );
    }

    public List<SelectedCourseMessage> getCourseScores(int id) {
        return transactional(
                em -> selectedCourseFacade.findBycourseCourseId(em, id).stream().map(
                        score -> messageMapper.intoScoreMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find scores"
        );
    }


    public SelectedCourseMessage createScore(ScoreCreateMessage scoreCreateMessage, int studentUserId, int courseCourseId) throws ServiceException, ServiceAssertionException {

        SelectedCourseEntity selectedCourseEntity = transactional(
                em -> selectedCourseFacade.findBystudentUserIdAndCourseId(em, studentUserId, courseCourseId),
                "failed to find notice"
        );

        selectedCourseEntity.setAvgOnlineScore(scoreCreateMessage.getAvgOnlineScore());
        selectedCourseEntity.setMidScore(scoreCreateMessage.getMidScore());
        selectedCourseEntity.setFinalScore(scoreCreateMessage.getFinalScore());
        selectedCourseEntity.setTotalScore(scoreCreateMessage.getTotalScore());
        return transactional(
                em -> {
                    selectedCourseFacade.edit(em, selectedCourseEntity);
                    return messageMapper.intoScoreMessage(em, selectedCourseEntity);
                },
                "failed to add score"
        );
    }

    public void calculateTotalScore(int courseId) {
        List<SelectedCourseMessage> toCalculateList = this.getCourseScores(courseId);
        for (int i = 0; i < toCalculateList.size(); i++) {

            int studentId = toCalculateList.get(i).getStudentUserId();

            SelectedCourseEntity selectedCourseEntity = transactional(
                    em -> selectedCourseFacade.findBystudentId(em, studentId),
                    "failed to find notice"
            );
            BigDecimal tempAvgOnlineScore = selectedCourseEntity.getAvgOnlineScore().multiply(new BigDecimal(0.3));
            BigDecimal tempMidScore = selectedCourseEntity.getMidScore().multiply(new BigDecimal(0.1));
            BigDecimal tempFinalScore = selectedCourseEntity.getFinalScore().multiply(new BigDecimal(0.6));
            BigDecimal totalScore = tempFinalScore.add(tempAvgOnlineScore.add(tempMidScore));

            selectedCourseEntity.setTotalScore(totalScore);

            transactional(
                    em -> {
                        selectedCourseFacade.edit(em, selectedCourseEntity);
                        return messageMapper.intoScoreMessage(em, selectedCourseEntity);
                    },
                    "failed to add score"
            );
        }
    }

//    public SelectedCourseMessage[] addTotalScore(ScoreCreateMessage scoreCreateMessageList[], int courseCourseId) {
//
//        // save entity findbystudentUserIdAndCourseId
//        //SelectedCourseEntity[] selectedCourseEntityList = new SelectedCourseEntity[scoreCreateMessageList.length];
//
//        for (int i = 0; i < scoreCreateMessageList.length; i = i + 1) {
//            ScoreCreateMessage scm=scoreCreateMessageList[i];
//            SelectedCourseEntity selectedCourseEntity = transactional(
//                    em -> selectedCourseFacade.findBystudentUserIdAndCourseId(em, scm.getStudentUserId(), courseCourseId),
//                    "failed to find notice"
//            );
//
//            selectedCourseEntity.setTotalScore(scm.getTotalScore());
//
//            transactional(
//                    em -> {
//                        selectedCourseFacade.edit(em, selectedCourseEntity);
//                        return messageMapper.intoScoreMessage(em, selectedCourseEntity);
//                    },
//                    "failed to add score"
//            );
//        }
//        return null;
//    }


}
