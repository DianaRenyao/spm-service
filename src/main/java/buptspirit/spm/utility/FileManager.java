package buptspirit.spm.utility;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Singleton
public class FileManager {

    public static final String staticFileDic = "/staticFiles";

    @Inject
    private Logger logger;

    private String generateFileName(String identifier) {
        return staticFileDic + "/" + identifier;
    }

    public void store(String identifier, InputStream inputStream) throws IOException {
        Files.copy(inputStream, Paths.get(generateFileName(identifier)));
    }

    public OutputStream read(String identifier) throws IOException {
        File file = new File(generateFileName(identifier));
        if (!file.exists()) {
            throw new IOException("no such file:" + identifier);
        }
        return new FileOutputStream(file);

    }

    public void delete(String identifier) throws IOException {
        Files.delete(Paths.get(generateFileName(identifier)));
    }
}
