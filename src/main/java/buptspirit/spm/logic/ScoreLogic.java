package buptspirit.spm.logic;

import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.ScoreMessage;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;;
import buptspirit.spm.persistence.facade.ScoreFacade;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ScoreLogic {

    @Inject
    private ScoreFacade scoreFacade;

    @Inject
    private MessageMapper messageMapper;


    public List<ScoreMessage> getAllScores() {
        return transactional(
                em -> scoreFacade.findAll(em).stream().map(
                        score -> messageMapper.intoMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find scores"
        );
    }
}
