package ua.com.juja.serzh.sqlcmd.model;

import org.junit.*;
import ua.com.juja.serzh.sqlcmd.Configuration;
import ua.com.juja.serzh.sqlcmd.Support;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by serzh on 5/11/16.
 */
public class DatabaseManagerTest {

    private static DatabaseManager manager;
    private static Support support;

    private static Configuration configuration = new Configuration();
    private static final String DATABASE = configuration.getDatabase();
    private static final String USER = configuration.getUser();
    private static final String PASSWORD = configuration.getPassword();

    @BeforeClass
    public static void setup() {
        manager = new PostgresManager();
        support = new Support();
        support.setupData(manager);
    }

    @AfterClass
    public static void dropDatabase() {
        support.dropData(manager);
    }

    @Test
    public void testGetAllTableNames() {
        Set<String> tableNames = manager.getTableNames();
        assertEquals("[users, users2, test1]", tableNames.toString());
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("users");
        // when
        Map<String, Object> input = new LinkedHashMap<>();
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
        Map<String, Object> input = new LinkedHashMap<>();
        input.put("name", "Stiven");
        input.put("password", "****");
        input.put("id", 11);
        manager.insert("users", input);

        Map<String, Object> input2 = new LinkedHashMap<>();
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

        Map<String, Object> input = new LinkedHashMap<>();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 15);
        manager.insert("users", input);
        // when
        Map<String, Object> newValue = new LinkedHashMap<>();
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
        // when
        Set<String> columnNames = manager.getTableColumns("users");
        // then
        assertEquals("[name, password, id]", columnNames.toString());
    }

    @Test
    public void getTableSize() {
        int size = manager.getTableSize("users");
        assertEquals(1, size);
    }

    @Test
    public void clearTable() {
        // given
        Map<String, Object> input = new LinkedHashMap<>();
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
    public void dropTable() {
        manager.dropTable("test1");
        Set<String> tables = manager.getTableNames();
        assertEquals("[users, users2]", tables.toString());
        manager.createTable("test1(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))");
    }

    @Test
    public void getters() {
        assertEquals(DATABASE, manager.getDatabaseName());
        assertEquals(USER, manager.getUser());
        assertEquals(PASSWORD, manager.getPassword());
    }
}
