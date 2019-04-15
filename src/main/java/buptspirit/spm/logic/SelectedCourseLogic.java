package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SelectedCourseCreationMessage;
import buptspirit.spm.message.SelectedCourseMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.SelectedCourseFacade;
import buptspirit.spm.rest.filter.Role;
import org.apache.logging.log4j.Logger;

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

    @Inject
    private CourseFacade courseFacade;

    public SelectedCourseMessage getScore(int studentId, int courseId) {
        SelectedCourseEntity selectedCourseEntity = transactional(
                em -> selectedCourseFacade.findByStudentUserIdAndCourseId(em, studentId, courseId),
                "failed to find selected course"
        );
        return transactional(
                em -> messageMapper.intoScoreMessage(em, selectedCourseEntity),
                "failed to convert course to message"
        );
    }

    public List<SelectedCourseMessage> getStudentSelectedCourses(String username, SessionMessage sessionMessage) throws ServiceException {
        if (!username.equals(sessionMessage.getUserInfo().getUsername()) ||
                !sessionMessage.getUserInfo().getRole().equals(Role.Student.getName()))
            throw ServiceError.FORBIDDEN.toException();
        return transactional(
                em -> selectedCourseFacade.findByStudentId(em, sessionMessage.getUserInfo().getId()).stream().map(
                        score -> messageMapper.intoScoreMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find selected courses"
        );
    }

    public List<SelectedCourseMessage> getTeacherSelectedCourses(int courseId, String username, SessionMessage sessionMessage) throws ServiceException {
        boolean allow = transactional(
                em -> courseFacade.find(em, courseId).getTeacherUserId() == sessionMessage.getUserInfo().getId(),
                "failed to find this course"
        );
        if (!username.equals(sessionMessage.getUserInfo().getUsername()) ||
                !sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) || !allow)
            throw ServiceError.FORBIDDEN.toException();
        return transactional(
                em -> selectedCourseFacade.findByCourseId(em, courseId).stream().map(
                        score -> messageMapper.intoScoreMessage(em, score)
                ).collect(Collectors.toList()),
                "failed to find selected courses"
        );
    }

    public SelectedCourseMessage editSelectedCourse(SelectedCourseCreationMessage selectedCourseCreationMessage,
                                                    int studentUserId, int courseId,
                                                    SessionMessage sessionMessage, String username) throws ServiceException {
        /* 验证是否是有权限录入成绩的老师 */
        boolean allow = transactional(
                em -> courseFacade.find(em, courseId).getTeacherUserId() == sessionMessage.getUserInfo().getId(),
                "failed to find this course"
        );
        if (!username.equals(sessionMessage.getUserInfo().getUsername()) ||
                !sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) || !allow)
            throw ServiceError.FORBIDDEN.toException();
        SelectedCourseEntity selectedCourseEntity = transactional(
                em -> selectedCourseFacade.findByStudentUserIdAndCourseId(em, studentUserId, courseId),
                "failed to find selected courses"
        );

        /* 计算总成绩并存入数据库 */
        selectedCourseEntity.setAvgOnlineScore(selectedCourseCreationMessage.getAvgOnlineScore());
        selectedCourseEntity.setMidScore(selectedCourseCreationMessage.getMidScore());
        selectedCourseEntity.setFinalScore(selectedCourseCreationMessage.getFinalScore());
        BigDecimal tempAvgOnlineScore = selectedCourseEntity.getAvgOnlineScore().multiply(new BigDecimal(0.3));
        BigDecimal tempMidScore = selectedCourseEntity.getMidScore().multiply(new BigDecimal(0.1));
        BigDecimal tempFinalScore = selectedCourseEntity.getFinalScore().multiply(new BigDecimal(0.6));
        BigDecimal totalScore = tempFinalScore.add(tempAvgOnlineScore.add(tempMidScore)).setScale(1, BigDecimal.ROUND_HALF_DOWN);
        selectedCourseEntity.setTotalScore(totalScore);
        return transactional(
                em -> {
                    selectedCourseFacade.edit(em, selectedCourseEntity);
                    return messageMapper.intoScoreMessage(em, selectedCourseEntity);
                },
                "failed to add selected course"
        );
    }
}
