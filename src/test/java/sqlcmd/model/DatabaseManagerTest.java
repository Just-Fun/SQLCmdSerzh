package sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by indigo on 21.08.2015.
 */
public class DatabaseManagerTest {

    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = new JDBCDatabaseManager();
        manager.connect("sqlcmd", "postgres", "postgres");
    }

    @Test
    public void testGetAllTableNames() {
        String[] tableNames = manager.getTableNames();
        assertEquals("[user, test]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("user");

        // when
        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.insert("user", input);

        // then
        List<Map<String, Object>> users = manager.getTableData("user");

        assertEquals(1, users.size());

        Map<String, Object> user = users.get(0);

        assertEquals("[Stiven, pass, 13]", user.values().toString());
        assertEquals("[name, password, id]", user.keySet().toString());
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear("user");

        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.insert("user", input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        manager.update("user", 13, newValue);

        // then
        List<Map<String, Object>> users = manager.getTableData("user");
        assertEquals(1, users.size());

        Map<String, Object> user = users.get(0);
        assertEquals("[name, password, id]", user.keySet().toString());
        assertEquals("[Pup, pass2, 13]", user.values().toString());
    }

    @Test
    public void testGetColumnNames() {
        // given
        manager.clear("user");

        // when
        Set<String> columnNames = manager.getTableColumns("user");

        // then
        assertEquals("[name, password, id]", columnNames.toString());
    }

    @Test
    public void testisConnected() {
        // given
        // when
        // then
        assertTrue(manager.isConnected());
    }
}
