package mercy.digital.transfer.utils;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class H2Utils {

    private static Connection prodConnection;
    private static Connection testConnection;

    private static void initTcpDb() {
        try {
            Server.createTcpServer("-tcpPort", "9092", "-tcp", "-tcpAllowOthers", "-ifNotExists").start();
            prodConnection = DriverManager.getConnection(
                    System.getProperty("db.url"), "sa", "");
            RunScript.execute(prodConnection, new FileReader("./config/init.sql"));
        } catch (SQLException | IOException e) {
            log.error(e.getLocalizedMessage());
        }
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
        if (environment.equals(Environment.TEST)) {
            initInMemDb();
        }
        if (environment.equals(Environment.PRODUCTION)) {
            initTcpDb();
        }
    }
}
