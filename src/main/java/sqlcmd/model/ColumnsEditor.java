package sqlcmd.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by serzh on 1/10/16.
 */
// Добавить методов отсюда в Менеджер
public class ColumnsEditor  {


   /* public ColumnsEditor(String dbname, String username, String password) {
        super(dbname, username, password);
    }

    @Override
    public void InsertRecordInTable(String table, String columnA, String columnB, String value1, String valie2) {
        String sql = "INSERT INTO " + table + " (" + columnA + ", " + columnB + ") VALUES ('" + value1 + "', '" + valie2 + "')";
        executeAndClose(sql);
    }

    @Override
    public void updateValueInColumnOnId(String table, int id, String column, String newValue) {
        String query1 = "UPDATE " + table + " set " + column + "='" + newValue + "' where id=" + id;
        executeAndClose(query1); //String query1="update emp set name='ravi' where id=2";
    }

    @Override
    public void deleteRowOnId(String table, int id) {
        String query1 = "DELETE from " + table + " where id=" + id;
        executeAndClose(query1); // String query2 = "delete  from emp where id=1";
    }

    @Override
    public void insertDanger(String table, int id, String first_name, String last_name) {
        String query2 = String.format("insert into employees values( %s, '%s', '%s')", id, first_name, last_name);
        executeAndClose(query2); // String query3 = "insert into emp values (1,'ronak','manager')";

    }

    @Override
    public void searchColumnOnColumnLike(String table, String column, String seach, String printTo) {
        String query = "SELECT * from " + table + " where " + column + " like '" + seach + "%'";
        try {
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(printTo + " for query " + query + " are");
            while (rs.next()) {
                String name = rs.getString(printTo);
                String id = rs.getString("id");
                System.out.println("id#" + id + ": " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seachRowOnId(String table, int id, String column) {
        String query = "SELECT * FROM employees where id=" + id;
        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString(column);
                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void InsertRecordinTableTest() {

        String sql = "INSERT INTO employees (first_name, last_name) VALUES ('Vasia3', 'Pupkin')";
        executeAndClose(sql);
    }

    private void executeAndClose(String sql) {
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    /*
    public OldDatabaseManager(String dbname, String username, String password) {
        super(dbname, username, password);
    }

    protected void removeTable(String table) throws SQLException {
        String query3 = "drop table " + table;
        stmt.execute(query3);
        System.out.println(table + " table removed");
    }

    protected void removeColumn(String column) throws SQLException {
        String query2 = "ALTER TABLE employees DROP COLUMN " + column;
        stmt.execute(query2);
        System.out.println(column + " column removed from the table");
    }

    protected void addColumn(String column, int numOfChars) throws SQLException {
        String query1 = "ALTER TABLE employees ADD " + column + " CHAR (" + numOfChars + ") ";
        stmt.execute(query1);
        System.out.println(column + " column with " + numOfChars + "number of chars added to the table");
    }

    protected void createTable() throws SQLException {
        String query = "CREATE TABLE employees(id INTEGER PRIMARY KEY, first_name CHAR(50),last_name CHAR(75))";
        stmt.execute(query);
        System.out.println("Employee table created");
    }

    protected void getNumOfRowsInTable(String table) throws SQLException {
        int num_of_rows = 0;
        ResultSet rs = stmt.executeQuery
                ("SELECT * FROM " + table);
        while (rs.next()) {
            num_of_rows++;
        }
        System.out.println("There are " + num_of_rows
                + " record in the table");
    }

    protected void retrieveContentsOfaTable(String table, String columnA,String columnB) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            System.out.println("id " + columnA + " " + columnB);
            while (rs.next()) {
                int id = rs.getInt("id");
                String column1 = rs.getString(columnA);
                String column2 = rs.getString(columnB);
                System.out.println(id + " " + column1 + " " + column2);
            }
        } catch (SQLException e) {
            System.out.println("SQL exception occured" + e);
        }
    }

    * */
}
