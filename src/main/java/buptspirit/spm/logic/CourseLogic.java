package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.*;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
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


}
