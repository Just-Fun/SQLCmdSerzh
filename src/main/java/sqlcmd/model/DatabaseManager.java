package sqlcmd.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by indigo on 25.08.2015.
 */
public interface DatabaseManager {

   List<Map<String, Object>> getTableData(String tableName);

    void dropDatabase(String databaseName);

    String[] getTableNames();

    void connect(String database, String userName, String password);

//    void disconnectFromDatabase();

    void clear(String tableName);

    void createDatabase(String databaseName);

    void createTable(String query);

    void insert(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    Set<String> getTableColumns(String tableName);

    boolean isConnected();
}
