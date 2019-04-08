package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.*;
import org.apache.logging.log4j.Logger;
;
import buptspirit.spm.persistence.facade.ScoreFacade;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ScoreLogic {

    @Inject
    private ScoreFacade scoreFacade;


    @Inject
    private MessageMapper messageMapper;

    @Inject
    private Logger logger;

    public List<ScoreMessage> getAllScores() {
        return transactional(
                em -> scoreFacade.findAll(em).stream().map(
                        score -> messageMapper.intoMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find scores"
        );
    }

    public List<ScoreMessage> getStudentScores(String username) throws ServiceException {
        return transactional(
                em -> scoreFacade.findBystudentUserName(em, username).stream().map(
                        score -> messageMapper.intoMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find scores"
        );
    }

    public List<ScoreMessage> getCourseScores(int id) throws ServiceException {
        return transactional(
                em -> scoreFacade.findBycourseCourseId(em, id).stream().map(
                        score -> messageMapper.intoMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find scores"
        );
    }


    public ScoreMessage createScore(ScoreCreateMessage scoreCreateMessage, int studentUserId, int courseCourseId) throws ServiceException, ServiceAssertionException {

        SelectedCourseEntity selectedCourseEntity = transactional(
                em -> scoreFacade.findBystudentUserIdAndCourseId(em, studentUserId, courseCourseId),
                "failed to find notice"
        );

        selectedCourseEntity.setAvgOnlineScore(scoreCreateMessage.getAvgOnlineScore());
        selectedCourseEntity.setMidScore(scoreCreateMessage.getMidScore());
        selectedCourseEntity.setFinalScore(scoreCreateMessage.getFinalScore());

        return transactional(
                em -> {
                    scoreFacade.edit(em, selectedCourseEntity);
                    return messageMapper.intoMessage(em, selectedCourseEntity);
                },
                "failed to add score"
        );
    }

}
