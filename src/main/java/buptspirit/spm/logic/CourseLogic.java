package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;

import buptspirit.spm.message.CourseCreationMessage;
import buptspirit.spm.message.CourseMessage;
import buptspirit.spm.message.CourseSummaryMessage;
import buptspirit.spm.message.ExperimentCreationMessage;
import buptspirit.spm.message.ExperimentMessage;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.message.UserInfoMessage;


import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.ExperimentEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;

import buptspirit.spm.persistence.facade.ExperimentFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;
import buptspirit.spm.rest.filter.AuthenticatedSession;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class CourseLogic {

    @Inject
    private CourseFacade courseFacade;

    @Inject
    private ExperimentFacade experimentFacade;

    @Inject
    private UserInfoFacade userInfoFacade;

    @Inject
    private MessageMapper messageMapper;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;


    public CourseMessage createCourse(SessionMessage sessionMessage, CourseCreationMessage courseCreationMessage) throws ServiceAssertionException {
        courseCreationMessage.enforce();
        CourseEntity newCourse = new CourseEntity();
        newCourse.setCourseName(courseCreationMessage.getCourseName());
        newCourse.setDescription(courseCreationMessage.getDescription());
        newCourse.setPeriod(courseCreationMessage.getPeriod());
        newCourse.setFinishDate(courseCreationMessage.getFinishDate());
        newCourse.setStartDate(courseCreationMessage.getStartDate());
        return transactional(
                em -> {
                    newCourse.setTeacherUserId(sessionMessage.getUserInfo().getId());
                    courseFacade.create(em, newCourse);
                    return messageMapper.intoCourseMessage(em, newCourse);
                },
                "failed to create course"
        );
    }

    public List<CourseSummaryMessage> getAllCourses() {
        return transactional(
                em -> courseFacade.findAll(em).stream().map(
                        course -> messageMapper.intoCourseSummaryMessage(em, course))
                        .collect(Collectors.toList()),
                "failed to find teacher"
        );
    }

    public List<CourseSummaryMessage> getSelectableCourses() {
        return transactional(
                em -> courseFacade.findSelectableCourses(em).stream().map(
                        course -> messageMapper.intoCourseSummaryMessage(em, course))
                        .collect(Collectors.toList()),
                "failed to find teacher"
        );
    }

    public List<CourseSummaryMessage> getTeacherCourses(String teacherUsername) {
        return transactional(
                em -> {
                    UserInfoEntity teacher = userInfoFacade.findByUsername(em, teacherUsername);
                    List<CourseEntity> courses = courseFacade.findTeacherCourses(em, teacher.getUserId());
                    return courses.stream().map(
                            course -> messageMapper.intoCourseSummaryMessage(em, course)
                    ).collect(Collectors.toList());
                },
                "failed to find teacher's course"
        );
    }

    public List<CourseSummaryMessage> getTeacherSelectableCourses(String teacherUsername) {
        return transactional(
                em -> {
                    UserInfoEntity teacher = userInfoFacade.findByUsername(em, teacherUsername);
                    List<CourseEntity> courses = courseFacade.findTeacherSelectableCourses(em, teacher.getUserId());
                    return courses.stream().map(
                            course -> messageMapper.intoCourseSummaryMessage(em, course)
                    ).collect(Collectors.toList());
                },
                "failed to find teacher's course"
        );
    }

    public CourseMessage getCourse(int id) throws ServiceException {
        CourseEntity entity = transactional(
                em -> courseFacade.find(em, id),
                "failed to find teacher's course"
        );
        if (entity == null)
            throw ServiceError.GET_COURSE_NO_SUCH_COURSE.toException();
        return transactional(
                em -> messageMapper.intoCourseMessage(em, entity),
                "failed to convert course to message"
        );
    }

    public ExperimentMessage createExperiment(int courseId, ExperimentCreationMessage experimentCreationMessage) throws ServiceException {
        UserInfoMessage teacherInfo = sessionMessage.getUserInfo();
        CourseEntity targetCourse = transactional(
                em -> courseFacade.findByCourseIdAndTercherId(em, courseId, teacherInfo.getId()),
                "failed to search course"
        );
        if (targetCourse == null) {
            throw ServiceError.POST_EXPERIMENT_NO_SUCH_COURSE.toException();
        }
        ExperimentEntity experimentEntity = new ExperimentEntity();
        experimentEntity.setCourseId(courseId);
        experimentEntity.setDescription(experimentCreationMessage.getDescription());
        experimentEntity.setExperimentName(experimentCreationMessage.getExperimentName());
        experimentEntity.setStartDate(experimentCreationMessage.getStartDate());
        experimentEntity.setFinishDate(experimentCreationMessage.getFinishDate());

        return transactional(
                em -> {
                    experimentFacade.create(em, experimentEntity);
                    return messageMapper.intoExperimentMessage(em, experimentEntity);
                },
                "failed to create "
        );
    }

    public List<ExperimentMessage> getExperiments(int courseId){
        return transactional(
                em -> {

                    List<ExperimentEntity> experiments = experimentFacade.findByCourseId(em,courseId);
                    return experiments.stream().map(
                            experiment -> messageMapper.intoExperimentMessage(em, experiment)
                    ).collect(Collectors.toList());
                },
                "failed to find experiments"
        );
    }
}
