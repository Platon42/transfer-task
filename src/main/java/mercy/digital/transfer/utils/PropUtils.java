package mercy.digital.transfer.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropUtils {

    private PropUtils() {
    }

    public static void setProperties(Environment environment) {
        try {
            if (environment.equals(Environment.TEST)) {
                Properties properties = new Properties();
                properties.load((PropUtils.class.getResourceAsStream("/test.properties")));
                System.out.println(properties.getProperty("db.url"));
                System.setProperty("db.url", properties.getProperty("db.url"));
                System.setProperty("db.user", properties.getProperty("db.user"));
                System.setProperty("db.password", properties.getProperty("db.password"));

            }
            if (environment.equals(Environment.PRODUCTION)) {
                Properties properties = new Properties();
                properties.load((PropUtils.class.getResourceAsStream("/prod.properties")));
                System.setProperty("db.url", properties.getProperty("db.url"));
                System.setProperty("db.user", properties.getProperty("db.user"));
                System.setProperty("db.password", properties.getProperty("db.password"));
            }
        } catch (IOException e) {
            log.error("Cannot load properties file");
        }
    }
}
