package mercy.digital.transfer.utils;

public class PropUtils {

    public static void setProperties(Environment environment) {

        if (environment.equals(Environment.TEST)) {
            System.setProperty("db.url", "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1");

        }
        if (environment.equals(Environment.PRODUCTION)) {
            System.setProperty("db.url", "jdbc:h2:tcp://localhost:9092/mem:transfer;DB_CLOSE_DELAY=-1");
        }
    }
}
