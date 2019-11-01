package mercy.digital.transfer.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropUtils {

    private PropUtils() {
    }

    private static final String DB_URL_PARAM_NAME = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASS_PARAM_NAME = "db.password";

    public static void setProperties(Environment environment) {
        try {
            if (environment.equals(Environment.TEST)) {
                Properties properties = new Properties();
                properties.load((PropUtils.class.getResourceAsStream("/test.properties")));
                System.setProperty(DB_URL_PARAM_NAME, properties.getProperty(DB_URL_PARAM_NAME));
                System.setProperty(DB_USER, properties.getProperty(DB_USER));
                System.setProperty(DB_PASS_PARAM_NAME, properties.getProperty(DB_PASS_PARAM_NAME));

            }
            if (environment.equals(Environment.PRODUCTION)) {
                Properties properties = new Properties();
                properties.load((PropUtils.class.getResourceAsStream("/prod.properties")));
                System.setProperty(DB_URL_PARAM_NAME, properties.getProperty(DB_URL_PARAM_NAME));
                System.setProperty(DB_USER, properties.getProperty(DB_USER));
                System.setProperty(DB_PASS_PARAM_NAME, properties.getProperty(DB_PASS_PARAM_NAME));
            }
        } catch (IOException e) {
            log.error("Cannot load properties file");
        }
    }
}
