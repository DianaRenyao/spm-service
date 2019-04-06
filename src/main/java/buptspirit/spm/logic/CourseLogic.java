package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.message.CourseMessage;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.TeacherEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class CourseLogic {
    @Inject
    private CourseFacade courseFacade;

    @Inject
    private UserInfoFacade userInfoFacade;


    public CourseMessage createCourse(CourseMessage courseMessage) throws ServiceAssertionException {
        courseMessage.enforce();
        CourseEntity newCourse = new CourseEntity();
        newCourse.setCourseName(courseMessage.getCourseName());
        newCourse.setDescription(courseMessage.getDescription());
        newCourse.setPeriod(courseMessage.getPeriod());
        newCourse.setFinishDate(courseMessage.getFinishDate());
        newCourse.setStartDate(courseMessage.getStartDate());
        transactional(
                em -> {
                    UserInfoEntity teacher = userInfoFacade.findByUsername(em, courseMessage.getTeacherUsername());
                    newCourse.setTeacherUserId(teacher.getUserId());
                    courseFacade.create(em, newCourse);
                    return null;
                },
                "failed to create course"
        );
        return courseMessage;
    }
}
