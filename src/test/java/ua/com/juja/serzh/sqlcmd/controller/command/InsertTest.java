package ua.com.juja.serzh.sqlcmd.controller.command;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Created by serzh on 5/19/16.
 */
public class InsertTest {
    DatabaseManager manager;
    View view;
    Command command;

    @Before
    public void setup() {
        manager = Mockito.mock(DatabaseManager.class);
        view = Mockito.mock(View.class);
        command = new Insert(manager, view);
        Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j from text table formatter
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("insert|");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongInput() throws Exception {
        boolean canNotProcess = command.canProcess("inser|");
        assertFalse(canNotProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("insert|user|name|Vasia|password|****|id|22");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[The table 'user' has been successfully added record:, " +
                "+-----+--------+--+\n" +
                "|name |password|id|\n" +
                "+-----+--------+--+\n" +
                "|Vasia|****    |22|\n" +
                "+-----+--------+--+]", captor.getAllValues().toString());
    }

    @Test
    public void testWithWrongParameters() throws Exception {
        try {
            command.process("insert|user|name");
            fail("Expected IllegalArgumentException...");
        } catch (Exception e) {
            assertEquals("Must be an even number of parameters in a format " +
                    "'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                    "but you typed: 'insert|user|name'", e.getMessage());
        }
    }

    @Test
    public void testWithoutParameters() throws Exception {
        try {
            command.process("insert|");
            fail("Expected IllegalArgumentException...");
        } catch (Exception e) {
            assertEquals("Must be an even number of parameters in a format " +
                    "'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                    "but you typed: 'insert|'", e.getMessage());
        }
    }
}