package ua.com.juja.serzh.sqlcmd.controller.command;

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
import static org.junit.Assert.fail;
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
        Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j from text table formatter
    }

    @Test
    public void testPrintTableData() {
        // given
        setupTableColumns("user", "id", "name", "password");

        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");

        Map<String, Object> user2 = new LinkedHashMap<>();
        user2.put("id", 13);
        user2.put("name", "Eva");
        user2.put("password", "+++++");

        List<Map<String, Object>> data = new LinkedList<>();
        data.add(user1);
        data.add(user2);

        when(manager.getTableData("user")).thenReturn(data);
        // when
        command.process("find|user");
        // then
        shouldPrint("[+--+------+--------+\n" +
                "|id|name  |password|\n" +
                "+--+------+--------+\n" +
                "|12|Stiven|*****   |\n" +
                "+--+------+--------+\n" +
                "|13|Eva   |+++++   |\n" +
                "+--+------+--------+]");
    }

    @Test
    public void testPrintTableDataWithNullArgument() {
        // given
        setupTableColumns("user", "id", "name", "password");

        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", null);

        List<Map<String, Object>> data = new LinkedList<>();
        data.add(user1);

        when(manager.getTableData("user")).thenReturn(data);
        // when
        command.process("find|user");
        // then
        shouldPrint("[+--+------+--------+\n" +
                "|id|name  |password|\n" +
                "+--+------+--------+\n" +
                "|12|Stiven|        |\n" +
                "+--+------+--------+]");
    }

    private void setupTableColumns(String tableName, String... columns) {
        when(manager.getTableColumns(tableName)).thenReturn(new LinkedHashSet<>(Arrays.asList(columns)));

    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture()); // все что на view при вызове write - записать в captor
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
    public void testCanProcessFindWithoutParametersString() {
        // when
        boolean canNotProcess = command.canProcess("find");
        // then
        assertTrue(canNotProcess);
    }


    @Test
    public void testCantProcessQweString() {
        // when
        boolean canNotProcess = command.canProcess("qwe|user");
        // then
        assertFalse(canNotProcess);
    }

    @Test
    public void testPrintEmptyTableData() {
        // given
        setupTableColumns("user", "id", "name", "password");
        when(manager.getTableData("user")).thenReturn(new LinkedList<>());
        // when
        command.process("find|user");
        // then
        shouldPrint("[+--+----+--------+\n" +
                "|id|name|password|\n" +
                "+--+----+--------+]");
    }

    @Test
    public void testPrintTableDataWithOneColumn() {
        setupTableColumns("test", "id");

        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("id", 12);

        Map<String, Object> user2 = new LinkedHashMap<>();
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

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        try {
            command.process("find");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'find|tableName', а ты ввел: find", e.getMessage());
        }
    }

    @Test
    public void testCanProcessFindWithOnlySlash() {
        try {
            command.process("find|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'find|tableName', а ты ввел: find|", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        try {
            command.process("find|table|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'find|tableName', а ты ввел: find|table|qwe", e.getMessage());
        }
    }
}
