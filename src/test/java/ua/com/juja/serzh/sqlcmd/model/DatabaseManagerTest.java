package ua.com.juja.serzh.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by serzh on 5/11/16.
 */
public class DatabaseManagerTest {

    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = new JDBCDatabaseManager();
        manager.connect("sqlcmd5hope5never5exist", "postgres", "postgres");
    }

    @Test
    public void testGetAllTableNames() {
        String[] tableNames = manager.getTableNames();
        assertEquals("[users, test1]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("users");
        // when
        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "****");
        input.put("id", 11);
        manager.insert("users", input);
        // then
        List<Map<String, Object>> users = manager.getTableData("users");
        assertEquals(1, users.size());

        Map<String, Object> user = users.get(0);
        assertEquals("[Stiven, ****, 11]", user.values().toString());
        assertEquals("[name, password, id]", user.keySet().toString());
    }

    @Test
    public void testGetTableData2() {
        // given
        manager.clear("users");
        // when
        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "****");
        input.put("id", 11);
        manager.insert("users", input);

        DataSet input2 = new DataSet();
        input2.put("name", "Stiven2");
        input2.put("password", "*****");
        input2.put("id", 12);
        manager.insert("users", input2);
        // then
        List<Map<String, Object>> users = manager.getTableData("users");
        assertEquals(2, users.size());

        Map<String, Object> user = users.get(0);
        assertEquals("[Stiven, ****, 11]", user.values().toString());
        assertEquals("[name, password, id]", user.keySet().toString());

        Map<String, Object> user2 = users.get(1);
        assertEquals("[Stiven2, *****, 12]", user2.values().toString());
        assertEquals("[name, password, id]", user2.keySet().toString());
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear("users");

        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 15);
        manager.insert("users", input);
        // when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        manager.update("users", 15, newValue);
        // then
        List<Map<String, Object>> users = manager.getTableData("users");
        assertEquals(1, users.size());

        Map<String, Object> user = users.get(0);
        assertEquals("[name, password, id]", user.keySet().toString());
        assertEquals("[Pup, pass2, 15]", user.values().toString());
    }

    @Test
    public void testGetColumnNames() {
        // given
        manager.clear("users");
        // when
        Set<String> columnNames = manager.getTableColumns("users");
        // then
        assertEquals("[name, password, id]", columnNames.toString());
    }

    @Test
    public void clearTable() {
        // given
        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 17);
        manager.insert("users", input);
        // when
        manager.clear("users");
        // then
        List<Map<String, Object>> users = manager.getTableData("users");
        assertEquals(0, users.size());
    }

    @Test
    public void testisConnected() {
        assertTrue(manager.isConnected());
    }

    @Test
    public void tablesList() {
        String[] tables = manager.getTableNames();
        assertEquals("[users, test1]", Arrays.toString(tables));
    }

    @Test
    public void dropTable() {
        manager.dropTable("test1");
        String[] tables = manager.getTableNames();
        assertEquals("[users]", Arrays.toString(tables));
        manager.createTable("test1(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))");
    }
}
