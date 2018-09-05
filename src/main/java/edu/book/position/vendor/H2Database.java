package edu.book.position.vendor;

import edu.book.position.DatabaseConfigReader;
import edu.book.position.DatabaseFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Database implements DatabaseFactory {
    private static final String H2_DRIVER = DatabaseConfigReader.getProperty("h2.driver");
    private static final String H2_CONNECTION_URL = DatabaseConfigReader.getProperty("h2.connection.url");
    private static final String H2_USER = DatabaseConfigReader.getProperty("h2.user");
    private static final String H2_PASSWORD = DatabaseConfigReader.getProperty("h2.password");
    private static final Logger LOGGER = Logger.getLogger(H2Database.class);

    private H2Database() {
    }

    public static void instantiateAndInitializeDB() {
        DbUtils.loadDriver(H2_DRIVER);
        insertTestDataIntoDB();
    }

    private static void insertTestDataIntoDB() {
        LOGGER.info("Inserting Test Data into DB");
        Connection conn = null;
        try {
            conn = getConnection();
            RunScript.execute(conn, new FileReader("src/test/resources/testdata.sql"));
        } catch (SQLException e) {
            LOGGER.error("Error populating testdata. Exception caught : [{}]", e);
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            LOGGER.error("Error populating testdata. Exception caught : [{}]", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(H2_CONNECTION_URL, H2_USER, H2_PASSWORD);
    }
}