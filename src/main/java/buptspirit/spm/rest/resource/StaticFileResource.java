package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.StaticFileLogic;
import buptspirit.spm.message.FileSourceMessage;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;
import buptspirit.spm.utility.FileManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
        try {
            String filename = URLDecoder.decode(fileDetail.getFileName(), StandardCharsets.UTF_8.name());
            return fileLogic.upload(inputStream, filename);
        } catch (UnsupportedEncodingException e) {
            // impossible in normal state
            logger.error("unsupported encoding utf-8", e);
            throw ServiceError.UNKNOWN.toException();
        }
    }

    @GET
    @Path("{identifier}")
    public Response download(
            @PathParam("identifier") String identifier,
            @DefaultValue("false") @QueryParam("download") boolean download) throws ServiceException {
        File identifierFile;
        FileSourceMessage fileSourceMessage = fileLogic.download(identifier);
        try {
            identifierFile = fileManager.getFile(identifier);
        } catch (IOException e) {
            logger.warn("failed to find identifier" + e);
            throw ServiceError.GET_STATIC_FILE_FAILED_TO_DOWNLOAD_FILE.toException();
        }
        Response.ResponseBuilder builder = Response.ok(identifierFile);
        if (download) {
            ContentDisposition contentDisposition = ContentDisposition.type("attachment")
                    .fileName(fileSourceMessage.getFilename()).build();
            builder.header("Content-Disposition", contentDisposition)
                    .type(MediaType.APPLICATION_OCTET_STREAM);
        } else {
            builder.type(fileSourceMessage.getFileType());
        }
        return builder.build();
    }
}
