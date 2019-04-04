package buptspirit.spm.rest.resource;

import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.message.StudentMessage;
import buptspirit.spm.message.StudentRegisterMessage;
import buptspirit.spm.rest.exception.ServiceException;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("students")
public class StudentResource {

    @Inject
    private UserLogic userLogic;

    @POST
    public StudentMessage register(StudentRegisterMessage registerMessage) throws ServiceException {
        return userLogic.createStudent(registerMessage);
    }
}
