package mercy.digital.transfer.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class H2DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:h2:mem:transfer_db");
        //config.setUsername( "database_username" );
        //config.setPassword( "database_password" );
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(20);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private H2DataSource() {}

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect to H2 " + e.getLocalizedMessage());
        }
        return connection;
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
