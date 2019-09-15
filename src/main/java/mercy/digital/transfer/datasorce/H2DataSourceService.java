package mercy.digital.transfer.datasorce;

import com.j256.ormlite.jdbc.DataSourceConnectionSource;

public interface H2DataSourceService {
    DataSourceConnectionSource getConnectionSource();
}
