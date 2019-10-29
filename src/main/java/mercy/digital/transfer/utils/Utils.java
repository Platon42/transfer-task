package mercy.digital.transfer.utils;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class Utils {

    public static void setProperties(Environment environment) {
        switch (environment) {
            case TEST:
                System.setProperty("db.url", "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1");
                break;
            case PRODUCTION:
                System.setProperty("db.url", "jdbc:h2:tcp://localhost:9092/mem:transfer;DB_CLOSE_DELAY=-1");
                break;
            default:
                break;
        }
    }

    public static void startDb(Environment environment) {
        Connection connection = null;
        switch (environment) {
            case PRODUCTION:
                try {
                    Server.createTcpServer("-tcpPort", "9092", "-tcp", "-tcpAllowOthers", "-ifNotExists").start();
                    connection = DriverManager.getConnection(
                            System.getProperty("db.url"), "sa", "sa");

                    RunScript.execute(connection, new FileReader("./config/init.sql"));
                } catch (SQLException | FileNotFoundException e) {
                    log.error("Cannot start PROD database " + e.getLocalizedMessage());
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            log.error(e.getMessage());
                        }
                    }
                }
                break;
            case TEST:
                try {
                    connection = DriverManager.getConnection(
                            System.getProperty("db.url"), "sa", "sa");
                    RunScript.execute(connection, new FileReader("./config/init.sql"));
                } catch (SQLException | FileNotFoundException e) {
                    log.error("Cannot start TEST database " + e.getLocalizedMessage());
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            log.error(e.getMessage());
                        }
                    }
                }
                break;
        }
    }
}
