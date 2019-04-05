package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.StudentEntity;

import javax.inject.Singleton;

@Singleton
public class StudentFacade extends AbstractFacade<StudentEntity> {

    public StudentFacade() {
        super(StudentEntity.class);
    }
}
