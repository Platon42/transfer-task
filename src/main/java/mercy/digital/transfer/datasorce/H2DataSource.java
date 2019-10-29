package mercy.digital.transfer.datasorce;

import com.j256.ormlite.db.H2DatabaseType;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class H2DataSource implements H2DataSourceService {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(System.getProperty("db.url") + ";IFEXISTS=TRUE" + "DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        config.setPassword("");
        config.setSchema("TRANSFER");
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(20);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public DataSourceConnectionSource getConnectionSource() {
        DataSourceConnectionSource dataSourceConnectionSource = null;
        try {
            dataSourceConnectionSource = new DataSourceConnectionSource(ds, new H2DatabaseType());
        } catch (SQLException e) {
            log.error("Cannot create datasource " + e.getLocalizedMessage());
        }
        return dataSourceConnectionSource;
    }
}