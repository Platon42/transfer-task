package mercy.digital.transfer.utils;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class H2Utils {

    private static Connection prodConnection;
    private static Connection testConnection;

    private static void initTcpDb() throws SQLException, FileNotFoundException {
        Server.createTcpServer("-tcpPort", "9092", "-tcp", "-tcpAllowOthers", "-ifNotExists").start();
        prodConnection = DriverManager.getConnection(
                System.getProperty("db.url"), "sa", "");
        RunScript.execute(prodConnection, new FileReader("./config/init.sql"));
        prodConnection.close();
    }

    private static void initInMemDb() {
        try {
            testConnection = DriverManager.getConnection(
                    System.getProperty("db.url"), "sa", "");
            RunScript.execute(testConnection, new FileReader("./config/init.sql"));
            testConnection.close();
        } catch (SQLException | IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static void startDb(Environment environment) {
        try {
            if (environment.equals(Environment.TEST)) {
                initInMemDb();
            }
            if (environment.equals(Environment.PRODUCTION)) {
                initTcpDb();
            }
        } catch (SQLException | FileNotFoundException e) {
            log.error("Cannot create " + environment + " database instance");
        }
    }

    public static void stopDb(Environment environment) {
        try {
            if (environment.equals(Environment.TEST)) {
                testConnection.createStatement().execute("SHUTDOWN");
                testConnection.close();
            }
            if (environment.equals(Environment.PRODUCTION)) {
                prodConnection.createStatement().execute("SHUTDOWN");
                prodConnection.close();
            }
        } catch (SQLException e) {
            log.error("Cannot shutdown " + environment + "database instance");
        }
    }
}
