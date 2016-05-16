package ua.com.juja.serzh.sqlcmd.model;

import java.sql.*;
import java.util.*;

/**
 * Created by serzh on 5/11/16.
 */
public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connection;

    @Override // try-with-resources statement ensures that each resource is closed at the end of the statement
    public void connect(String database, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");//грузим драйвер,  loads a class, including running its static initializers
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add dependency postgresql version 9.4.1207.jre7 to project.");
        }
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection( // коннектимся к базе
                    "jdbc:postgresql://localhost:5432/" + database, userName, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s", database, userName), e);
        }
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet tableData = stmt.executeQuery("SELECT * FROM public." + tableName)) {

            ResultSetMetaData rsmd = tableData.getMetaData();
            List<Map<String, Object>> result = new LinkedList<>();
            while (tableData.next()) {
                Map<String, Object> data = new LinkedHashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    data.put(rsmd.getColumnName(i), tableData.getObject(i));
                }
                result.add(data);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    @Override
    public void createDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO имя не может начинаться с цифры и...
        }
    }

    @Override
    public void dropDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DROP DATABASE IF EXISTS " + databaseName + " CASCADE");
            statement.executeUpdate("DROP DATABASE " + databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getTableNames() {
        try (Statement stmt = connection.createStatement();
             ResultSet tableNames = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' " +
                     "AND table_type='BASE TABLE'")) {
            String[] tables = new String[100];
            int index = 0;
            while (tableNames.next()) {
                tables[index++] = tableNames.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }


    @Override
    public void clear(String tableName) throws RuntimeException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(String tableName, DataSet input) {
        try (Statement stmt = connection.createStatement()) {
            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            stmt.executeUpdate("INSERT INTO public." + tableName + " (" + tableNames + ")" +
                    "VALUES (" + values + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTable(String table_name) {
        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table_name);
            statement.executeUpdate("CREATE TABLE " + table_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable(String table_name) {
        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DROP TABLE IF EXISTS " + table_name + " CASCADE");
            statement.executeUpdate("DROP TABLE " + table_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet tableColumns = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE " +
                     "table_schema = 'public' AND table_name = '" + tableName + "'")) {
            Set<String> tables = new LinkedHashSet<>();
            while (tableColumns.next()) {
                tables.add(tableColumns.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedHashSet<>();
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override // TODO заготовка, может как-нибудь реализовать в userInerface, тест написан
    public void update(String tableName, int id, DataSet newValue) {
        String tableNames = getNameFormated(newValue, "%s = ?,");

        String updateTable = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateTable)) {
            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO заготовка, может как-нибудь реализовать в userInerface
    private int getSize(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet tableSize = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName)) {
            tableSize.next();
            int size = tableSize.getInt(1);
            return size;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
