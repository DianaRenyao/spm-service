package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.StaticFileLogic;
import buptspirit.spm.message.FileSourceMessage;
import buptspirit.spm.utility.FileManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
//    @Secured(Role.Teacher)
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
        message.setFilename(fileDetail.getFileName());
        message.setFileType(fileDetail.getType());

        return message;
    }

    @GET
    @Path("{identifier}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("identifier") String identifier) throws ServiceException {
        File identifierFile;
        FileSourceMessage fileSourceMessage = fileLogic.download(identifier);
        try {
            identifierFile = fileManager.getFile(identifier);
        } catch (IOException e) {
            logger.warn("failed to find identifer" + e);
            throw ServiceError.GET_STATIC_FILE_FAILED_TO_DOWNLOAD_FILE.toException();
        }
        Response.ResponseBuilder builder = Response.ok(identifierFile);
        builder
                .header("Content-Disposition", "attachment; filename=" + identifierFile.getName())
                .type(fileSourceMessage.getFileType());
        return builder.build();
    }
}
