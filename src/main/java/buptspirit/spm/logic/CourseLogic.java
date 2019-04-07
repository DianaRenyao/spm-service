package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.CourseCreationMessage;
import buptspirit.spm.message.CourseMessage;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class CourseLogic {
    @Inject
    private CourseFacade courseFacade;

    @Inject
    private UserInfoFacade userInfoFacade;

    @Inject
    private MessageMapper messageMapper;

    public CourseCreationMessage createCourse(CourseCreationMessage courseCreationMessage) throws ServiceAssertionException {
        courseCreationMessage.enforce();
        CourseEntity newCourse = new CourseEntity();
        newCourse.setCourseName(courseCreationMessage.getCourseName());
        newCourse.setDescription(courseCreationMessage.getDescription());
        newCourse.setPeriod(courseCreationMessage.getPeriod());
        newCourse.setFinishDate(courseCreationMessage.getFinishDate());
        newCourse.setStartDate(courseCreationMessage.getStartDate());
        transactional(
                em -> {
                    UserInfoEntity teacher = userInfoFacade.findByUsername(em, courseCreationMessage.getTeacherUsername());
                    newCourse.setTeacherUserId(teacher.getUserId());
                    courseFacade.create(em, newCourse);
                    return null;
                },
                "failed to create course"
        );
        return courseCreationMessage;
    }

    public List<CourseMessage> getAllCourses() throws ServiceException {
        List<CourseMessage> messages = transactional(
                em -> {
                    List<CourseEntity> courses = courseFacade.findAll(em);
                    return courses.stream().map(
                            course -> messageMapper.intoMessage(em,course)
                    ).collect(Collectors.toList());
                },
                "failed to find teacher"
        );
        if (messages == null)
            throw ServiceError.GET_COURSE_NO_SUCH_COURSE.toException();
        return messages;
    }

    public List<CourseMessage> getOptionalCourses() throws ServiceException {
        List<CourseMessage> messages = transactional(
                em -> {
                    List<CourseEntity> courses = courseFacade.findOptionalCourses(em);
                    return courses.stream().map(
                            course -> messageMapper.intoMessage(em,course)
                    ).collect(Collectors.toList());
                },
                "failed to find teacher"
        );
        if (messages == null)
            throw ServiceError.GET_COURSE_NO_SUCH_COURSE.toException();
        return messages;
    }

    public List<CourseMessage> getTeacherCourses(String condition) throws ServiceException {
        List<CourseMessage> messages = transactional(
                em -> {
                    UserInfoEntity teacher = userInfoFacade.findByUsername(em, condition);
                    List<CourseEntity> courses = courseFacade.findTeacherCourses(em,teacher);
                    return courses.stream().map(
                            course -> messageMapper.intoMessage(em,course)
                    ).collect(Collectors.toList());
                },
                "failed to find teacher's course"
        );
        if (messages == null)
            throw ServiceError.GET_COURSE_NO_SUCH_COURSE.toException();
        return messages;
    }
}
