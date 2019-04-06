package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.CourseEntity;

public class CourseFacade extends AbstractFacade<CourseEntity> {
    public CourseFacade() {
        super(CourseEntity.class);
    }
}
