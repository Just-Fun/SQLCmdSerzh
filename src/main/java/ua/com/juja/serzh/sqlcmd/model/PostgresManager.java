package ua.com.juja.serzh.sqlcmd.model;

import java.sql.*;
import java.util.*;

/**
 * Created by serzh on 5/11/16.
 */
public class PostgresManager implements DatabaseManager {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DriverException("Not installed PostgreSQL JDBC driver.", e);
        }
    }
    private static final String ERROR = "It is impossible because: ";
    private static final String HOST = "localhost";
    private static final String PORT = "5432";

    private Connection connection;
    private String user;
    private String password;
    private String database;

    @Override // try-with-resources statement ensures that each resource is closed at the end of the statement
    public void connect(String database, String user, String password) {
        if (this.user != null && password != null) {
            this.user = user;
            this.password = password;
        }
        this.database = database;

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DatabaseManagerException(ERROR, e);
            }
        }
        try {
            String url = String.format("jdbc:postgresql://%s:%s/%s", HOST, PORT, database);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s", database, user), e);
        }
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) {
        List<Map<String, Object>> result = new LinkedList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet tableData = stmt.executeQuery("SELECT * FROM public." + tableName)) {
            ResultSetMetaData metaData = tableData.getMetaData();

            while (tableData.next()) {
                Map<String, Object> data = new LinkedHashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    data.put(metaData.getColumnName(i), tableData.getObject(i));
                }
                result.add(data);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void createDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void dropDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DROP DATABASE IF EXISTS " + databaseName + " CASCADE");
            statement.executeUpdate("DROP DATABASE " + databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public Set<String> getTableNames() {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet tableNames = stmt.executeQuery("SELECT table_name FROM information_schema.tables " +
                     "WHERE table_schema='public' AND table_type='BASE TABLE'")) {
            while (tableNames.next()) {
                tables.add(tableNames.getString("table_name"));
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


    @Override
    public void clear(String tableName) throws RuntimeException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void insert(String tableName, Map<String, Object> input) {
        try (Statement stmt = connection.createStatement()) {
            String tableNames = getNameFormatted(input, "%s,");
            String values = getValuesFormatted(input, "'%s',");

            stmt.executeUpdate("INSERT INTO public." + tableName + " (" + tableNames + ")" +
                    "VALUES (" + values + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void createTable(String table_name) {
        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table_name);
            statement.executeUpdate("CREATE TABLE " + table_name);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void dropTable(String table_name) {
        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DROP TABLE IF EXISTS " + table_name + " CASCADE");
            statement.executeUpdate("DROP TABLE " + table_name);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet tableColumns = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE " +
                     "table_schema = 'public' AND table_name = '" + tableName + "'")) {
            while (tableColumns.next()) {
                tables.add(tableColumns.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    private String getNameFormatted(Map<String, Object> newValue, String format) {
        String string = "";
        for (String name : newValue.keySet()) {
            string += String.format(format, name); //TODO: Иван тут лучше использовать StringBuffer
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    private String getValuesFormatted(Map<String, Object> input, String format) {
        String values = "";
        for (Object value : input.values()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override // TODO заготовка, может как-нибудь реализовать в userInerface, тест написан
    public void update(String tableName, int id, Map<String, Object> newValue) {
        String tableNames = getNameFormatted(newValue, "%s = ?,");

        String updateTable = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateTable)) {
            int index = 1;
            for (Object value : newValue.values()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    // TODO заготовка, может как-нибудь реализовать в userInerface
    @Override
    public int getSize(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet tableSize = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName)) {
            tableSize.next();
            return tableSize.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }
}