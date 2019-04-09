package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.*;
import org.apache.logging.log4j.Logger;
;
import buptspirit.spm.persistence.facade.SelectedCourseFacade;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;

import javax.inject.Inject;
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


    public List<SelectedCourseMessage> getStudentScores(String username) throws ServiceException {
        return transactional(
                em -> selectedCourseFacade.findBystudentUserName(em, username).stream().map(
                        score -> messageMapper.intoScoreMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find scores"
        );
    }

    public List<SelectedCourseMessage> getCourseScores(int id) throws ServiceException {
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

        return transactional(
                em -> {
                    selectedCourseFacade.edit(em, selectedCourseEntity);
                    return messageMapper.intoScoreMessage(em, selectedCourseEntity);
                },
                "failed to add score"
        );
    }

}
