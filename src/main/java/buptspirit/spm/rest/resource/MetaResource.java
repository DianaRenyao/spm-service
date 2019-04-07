package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.message.StudentRegisterMessage;
import buptspirit.spm.message.TeacherRegisterMessage;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("meta")
public class MetaResource {

    private static final String DEFAULT_MOCK_PASSWORD = "bupt-spirit";
    @Inject
    UserLogic userLogic;

    @GET
    @Path("errors")
    @Produces(MediaType.TEXT_PLAIN)
    public String getErrors() {
        StringBuilder builder = new StringBuilder();
        builder.append("enum ErrorCode {\n");
        for (ServiceError error : ServiceError.values()) {
            builder.append("    ");
            builder.append(error.name());
            builder.append(" = ");
            builder.append(error.getCode());
            builder.append(",\n");
        }
        builder.append("}\n");
        return builder.toString();
    }

    @SuppressWarnings("SameReturnValue")
    @GET
    @Path("is-working")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean getWorkingStatus() {
        return true;
    }

    @POST

    @Path("mock")
    @Secured({Role.Administrator})
    public Response createMockData() throws ServiceException, ServiceAssertionException {

        TeacherRegisterMessage teacher1 = new TeacherRegisterMessage();
        teacher1.setUsername("1000000001");
        teacher1.setEmail("han.wanjiang@bupt.edu.cn");
        teacher1.setPhone("10010010010");
        teacher1.setPassword(DEFAULT_MOCK_PASSWORD);
        teacher1.setRealName("韩万江");
        userLogic.createTeacher(teacher1);

        TeacherRegisterMessage teacher2 = new TeacherRegisterMessage();
        teacher2.setUsername("1000000002");
        teacher2.setEmail("zhang.xiaoyan@bupt.edu.cn");
        teacher2.setPhone("10010010010");
        teacher2.setPassword(DEFAULT_MOCK_PASSWORD);
        teacher2.setRealName("张笑燕");
        userLogic.createTeacher(teacher2);

        StudentRegisterMessage student1 = new StudentRegisterMessage();
        student1.setUsername("2000000001");
        student1.setClazz("2016211505");
        student1.setEmail("sun.hao@bupt.edu.cn");
        student1.setCollege("BUPT");
        student1.setPassword(DEFAULT_MOCK_PASSWORD);
        student1.setNickname("浩");
        student1.setRealName("孙浩");
        student1.setPhone("13300000000");
        userLogic.createStudent(student1);

        return Response.noContent().build();
    }
}
