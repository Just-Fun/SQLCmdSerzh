package sqlcmd.model;

import java.sql.*;
import java.util.*;

/**
 * Created by indigo on 21.08.2015.
 */
public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;
    private String user;
    private String password;

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
    @Override // проба1
    public void disconnectFromDatabase(String databaseName) {
//        isConnected = false;
//        connect("", user, password);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate( "SELECT pg_terminate_backend(pg_stat_activity.pid)" +
                    " FROM pg_stat_activity WHERE pg_stat_activity.datname = " + "'databaseName'" +
                    " AND pid <> pg_backend_pid()" );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        };

    }


    @Override // проба2
    public void disconnectFromDatabase2() {
//        isConnected = false;
//        connect("", user, password);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        };

    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            List<Map<String, Object>> result = new LinkedList<>();
            while (rs.next()) {
                Map<String, Object> data = new LinkedHashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    data.put(rsmd.getColumnName(i), rs.getObject(i));
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
    public void dropDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP DATABASE IF EXISTS " + databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   /* private int getSize(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName)) {
            rsCount.next();
            int size = rsCount.getInt(1);
            return size;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }*/

    @Override
    public String[] getTableNames() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' " +
                     "AND table_type='BASE TABLE'")) {
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    @Override
    public void clear(String tableName) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM /*public.*/" + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Override // new
    public void createDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override // new
    public void createTable(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        String tableNames = getNameFormated(newValue, "%s = ?,");

        String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE " +
                     "table_schema = 'public' AND table_name = '" + tableName + "'")) {
            Set<String> tables = new LinkedHashSet<>();
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
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
}
