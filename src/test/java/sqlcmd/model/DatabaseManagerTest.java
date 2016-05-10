package sqlcmd.model;

import org.junit.After;
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

   /* @After
    public void closeAfter() {
        manager.disconnectFromDatabase();
    }
*/
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
        // given
        // when
        // then
        assertTrue(manager.isConnected());
    }
}
