package edu.book.position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;


public class DatabaseConfigReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfigReader.class);

    private static Properties properties = new Properties();

    private static final String APPLICATION_PROPERTIES = "application.properties";

    static {
        String dbConfigFileName = System.getProperty(APPLICATION_PROPERTIES);

        if (Objects.isNull(dbConfigFileName)) {
            dbConfigFileName = APPLICATION_PROPERTIES;
        }
        loadConfig(dbConfigFileName);
    }

    private DatabaseConfigReader() {
    }

    private static void loadConfig(String fileName) {
        if (Objects.isNull(fileName)) {
            LOGGER.warn("Database config file is missing.");
        } else {
            try {
                LOGGER.info("Loading config file: [{}] ", fileName);
                final InputStream fileResourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
                properties.load(fileResourceStream);
            } catch (FileNotFoundException fileNameNotFoundException) {
                LOGGER.error("File name [{}] not found ", fileName, fileNameNotFoundException);
            } catch (IOException ioException) {
                LOGGER.error("Error when reading the file [{}] ", fileName, ioException);
            }
        }
    }

    public static String getProperty(String key) {
        final String value = properties.getProperty(key);
        return (Objects.isNull(value)) ? System.getProperty(key) : value;
    }
}