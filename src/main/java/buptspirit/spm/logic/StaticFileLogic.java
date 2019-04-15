package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.FileSourceMessage;
import buptspirit.spm.persistence.entity.FileSourceEntity;
import buptspirit.spm.persistence.facade.FileSourceFacade;
import buptspirit.spm.utility.FileManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

import static buptspirit.spm.persistence.JpaUtility.transactional;


@Singleton
public class StaticFileLogic {

    @Inject
    private FileManager fileManager;

    @Inject
    private FileSourceFacade fileSourceFacade;

    @Inject
    private Logger logger;


    public FileSourceMessage download(String identifier) throws ServiceException {
        FileSourceEntity fileSourceEntity = transactional(
                em -> fileSourceFacade.findByIdentifier(em, identifier),
                "failed to find fileSource"
        );
        if (fileSourceEntity == null)
            throw ServiceError.GET_STATIC_FILE_FAILED_TO_DOWNLOAD_FILE.toException();
        return FileSourceMessage.fromEntity(fileSourceEntity);
    }

    // TODO better handling delete and its resource
    void delete(String identifier) throws ServiceException {
        FileSourceEntity fileSourceEntity = transactional(
                em -> fileSourceFacade.findByIdentifier(em, identifier),
                "failed to find fileSource"
        );
        if (fileSourceEntity == null)
            throw ServiceError.DELETE_STATIC_FILE_NO_SUCH_FILE.toException();
        try {
            fileManager.delete(fileSourceEntity.getIdentifier());
        } catch (IOException e) {
            logger.error("io exception when deleting file", e);
            throw ServiceError.DELETE_STATIC_FILE_FAILED_DELETE.toException();
        }
        transactional(
                em -> {
                    fileSourceFacade.remove(em, fileSourceEntity);
                    return null;
                },
                "failed to remove file source entity"
        );
    }

    public FileSourceMessage upload(InputStream inputStream,
                                    String filename) throws ServiceException {
        String fileType = suffixToMediaType(filename);

        String identifier = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            fileManager.store(identifier, inputStream);
        } catch (IOException e) {
            logger.warn(" Failed to store file", e);
            throw ServiceError.POST_STATIC_FILE_FAILED_TO_STORE.toException();
        }
        FileSourceEntity entity = fileDetailToEntity(filename, fileType, identifier);
        try {
            transactional(
                    em -> {
                        fileSourceFacade.create(em, entity);
                        return null;
                    },
                    "fuck"
            );
        } catch (IllegalStateException e) {
            try {
                fileManager.delete(identifier);
            } catch (IOException ex) {
                logger.warn("unexpected to exception:" + ex.getMessage());
            }
            logger.warn("failed to insert file mate data to db");
            throw ServiceError.POST_STATIC_FILE_FAILED_TO_INSERT_DB.toException();
        }
        return FileSourceMessage.fromEntity(entity);
    }

    private FileSourceEntity fileDetailToEntity(String name, String fileType, String identifier) {
        FileSourceEntity entity = new FileSourceEntity();
        entity.setFilename(name);
        entity.setFileType(fileType);
        entity.setIdentifier(identifier);
        return entity;
    }

    private String suffixToMediaType(String fileName) throws ServiceException {
        String[] nameParts = fileName.split("\\.");
        if (nameParts.length == 1 || !fileSuffixToMIMEType.containsKey(nameParts[nameParts.length - 1]))
            throw ServiceError.POST_STATIC_FILE_UNACCEPTABLE_FILE_TYPE.toException();
        return fileSuffixToMIMEType.get(nameParts[nameParts.length - 1]);
    }

    private static final HashMap<String, String> fileSuffixToMIMEType = new HashMap<String, String>() {
        {
            put("pdf", "application/pdf");
            put("ppt", "application/vnd.ms-powerpoint");
            put("mp4", "video/mp4");
        }
    };

}
