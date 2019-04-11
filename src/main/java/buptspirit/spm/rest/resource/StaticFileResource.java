package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.StaticFileLogic;
import buptspirit.spm.message.FileSourceMessage;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;
import buptspirit.spm.utility.FileManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Path("static-files")
public class StaticFileResource {

    @Inject
    private StaticFileLogic fileLogic;

    @Inject
    private FileManager fileManager;
    @Inject
    private Logger logger;


    @POST
    @Secured(Role.Teacher)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public FileSourceMessage upload(
            @FormDataParam("file") InputStream inputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail
    ) throws ServiceException {
        return fileLogic.upload(inputStream, convert(fileDetail));
    }

    private FileSourceMessage convert(FormDataContentDisposition fileDetail) {
        FileSourceMessage message = new FileSourceMessage();
        message.setFilename(fileDetail.getFileName());// only file name is necessary
        return message;
    }

    @GET
    public Response download(
            @DefaultValue("false") @QueryParam("download") boolean download,
            @QueryParam("identifier") String identifier) throws ServiceException {
        if (download) {
            File identifierFile;
            try {
                identifierFile = fileManager.getFile(identifier);
            } catch (IOException e) {
                logger.warn("failed to find identifier" + e);
                throw ServiceError.GET_STATIC_FILE_FAILED_TO_DOWNLOAD_FILE.toException();
            }
            return Response.ok(identifierFile)
                    .type(MediaType.APPLICATION_OCTET_STREAM)
                    .build();
        } else {
            FileSourceMessage fileSourceMessage = fileLogic.download(identifier);
            return Response.status(Response.Status.OK)
                    .entity(fileSourceMessage)
                    .build();
        }


    }
}
