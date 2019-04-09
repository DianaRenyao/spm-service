package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.StaticFileLogic;
import buptspirit.spm.message.FileSourceMessage;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("static-files")
public class StaticFileResource {

    @Inject
    private StaticFileLogic fileLogic;

    @POST
    @Secured(Role.Teacher)
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public FileSourceMessage upload(
            @FormDataParam("file") InputStream inputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail
    ) throws ServiceException {
        return fileLogic.upload(inputStream,fileDetail);
    }
}
