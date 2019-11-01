package mercy.digital.transfer.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropUtils {

    private PropUtils() {
    }

    private static final String dbUrlParamName = "db.url";
    private static final String dbUserParamName = "db.user";
    private static final String ddPasswordParamName = "db.url";

    public static void setProperties(Environment environment) {
        try {
            if (environment.equals(Environment.TEST)) {
                Properties properties = new Properties();
                properties.load((PropUtils.class.getResourceAsStream("/test.properties")));
                System.setProperty(dbUrlParamName, properties.getProperty(dbUrlParamName));
                System.setProperty("db.user", properties.getProperty("db.user"));
                System.setProperty("db.password", properties.getProperty("db.password"));

            }
            if (environment.equals(Environment.PRODUCTION)) {
                Properties properties = new Properties();
                properties.load((PropUtils.class.getResourceAsStream("/prod.properties")));
                System.setProperty(dbUrlParamName, properties.getProperty(dbUrlParamName));
                System.setProperty(dbUserParamName, properties.getProperty(dbUserParamName));
                System.setProperty(ddPasswordParamName, properties.getProperty(ddPasswordParamName));
            }
        } catch (IOException e) {
            log.error("Cannot load properties file");
        }
    }
}
