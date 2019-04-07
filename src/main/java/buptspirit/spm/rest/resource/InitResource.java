package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.message.StudentRegisterMessage;
import buptspirit.spm.message.TeacherRegisterMessage;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("Init")
public class InitResource {
    @Inject
    UserLogic userLogic;

    private static final String DEFAULT_ADMIN_PASSWORD = "bupt-spirit";

    @POST
    @Secured({Role.Administrator})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean init() throws ServiceAssertionException, ServiceException, ServiceAssertionException {
        StudentRegisterMessage studentRegisterMessage = new StudentRegisterMessage();
        studentRegisterMessage.setClazz("2016211501");
        studentRegisterMessage.setEmail("student@bupt.edu.cn");
        studentRegisterMessage.setCollege("BUPT");
        studentRegisterMessage.setUsername("0000000002");
        studentRegisterMessage.setPassword(DEFAULT_ADMIN_PASSWORD);
        studentRegisterMessage.setRealName("学生");
        studentRegisterMessage.setPhone("");
        userLogic.createStudent(studentRegisterMessage);

        TeacherRegisterMessage teacherRegisterMessage = new TeacherRegisterMessage();
        teacherRegisterMessage.setEmail("teacher@bupt.edu.cn");
        teacherRegisterMessage.setPhone("");
        teacherRegisterMessage.setUsername("0000000001");
        teacherRegisterMessage.setPassword(DEFAULT_ADMIN_PASSWORD);
        teacherRegisterMessage.setRealName("老师");
        userLogic.createTeacher(teacherRegisterMessage);

        return true;
    }

}
