package ua.com.juja.serzh.sqlcmd.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by serzh on 5/11/16.
 */
public interface DatabaseManager {

    void disconnectFromDatabase();

    List<Map<String, Object>> getTableData(String tableName);

    Set<String> getTableNames();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void createDatabase(String databaseName);

    void dropDatabase(String databaseName);

    Set<String> getDatabases();

    void createTable(String query);

    void dropTable(String query);

    void insert(String tableName, Map<String, Object> input);

    void update(String tableName, int id, Map<String, Object> newValue);

    Set<String> getTableColumns(String tableName);

    boolean isConnected();

    int getSize(String tableName);

    String getUser();

    String getPassword();

    String getDatabase();
}
