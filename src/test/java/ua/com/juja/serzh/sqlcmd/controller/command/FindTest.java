package ua.com.juja.serzh.sqlcmd.controller.command;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.*;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

/**
 * Created by serzh on 5/11/16.
 */
public class FindTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(manager, view);

        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j from text table formatter
    }

    @Test
    public void testPrintTableData() {
        // given
        when(manager.getTableColumns("user")).thenReturn(new LinkedHashSet<>(Arrays.asList("id", "name", "password")));

        Map<String, Object> user1 = new HashMap<>();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");

        Map<String, Object> user2 = new HashMap<>();
        user2.put("id", 13);
        user2.put("name", "Eva");
        user2.put("password", "+++++");

        List<Map<String, Object>> data = new LinkedList<>();
        data.add(user1);
        data.add(user2);

        when(manager.getTableData("user"))
                .thenReturn(data);
        // when
        command.process("find|user");

        // then
        shouldPrint("[+-----+------+--------+\n" +
                "|id   |name  |password|\n" +
                "+-----+------+--------+\n" +
                "|*****|Stiven|12      |\n" +
                "+-----+------+--------+\n" +
                "|+++++|Eva   |13      |\n" +
                "+-----+------+--------+]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

    @Test
    public void testCanProcessFindWithParametersString() {
        // when
        boolean canProcess = command.canProcess("find|user");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessFindWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("find");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testCantProcessQweString() {
        // when
        boolean canProcess = command.canProcess("qwe|user");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintEmptyTableData() {
        // given
        when(manager.getTableColumns("user")).thenReturn(new LinkedHashSet<>(Arrays.asList("id", "name", "password")));

        when(manager.getTableData("user")).thenReturn(new LinkedList<Map<String, Object>>());

        // when
        command.process("find|user");

        // then
        shouldPrint("[+--+----+--------+\n" +
                "|id|name|password|\n" +
                "+--+----+--------+]");
    }

    @Test
    public void testPrintTableDataWithOneColumn() {
        // given
        when(manager.getTableColumns("test")).thenReturn(new LinkedHashSet<>(Arrays.asList("id")));

        Map<String, Object> user1 = new HashMap<>();
        user1.put("id", 12);

        Map<String, Object> user2 = new HashMap<>();
        user2.put("id", 13);

        List<Map<String, Object>> data = new LinkedList<>();
        data.add(user1);
        data.add(user2);

        when(manager.getTableData("test")).thenReturn(data);

        // when
        command.process("find|test");

        // then
        shouldPrint("[+--+\n" +
                "|id|\n" +
                "+--+\n" +
                "|12|\n" +
                "+--+\n" +
                "|13|\n" +
                "+--+]");
    }
}
