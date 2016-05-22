package ua.com.juja.serzh.sqlcmd.controller.command;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
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
    CommandAbstract command;

    @Before
    public void setup() {
        manager = Mockito.mock(DatabaseManager.class);
        view = Mockito.mock(View.class);
        command = new Insert(manager, view);

        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j from text table formatter
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess(new UserInput("insert|"));
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongInput() throws Exception {
        boolean canNotProcess = command.canProcess(new UserInput("inser|"));
        assertFalse(canNotProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process(new UserInput("insert|user|name|Vasia|password|****|id|22"));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[В таблице 'user' была успешно добавлена запись:, " +
                "+-----+--------+--+\n" +
                "|name |password|id|\n" +
                "+-----+--------+--+\n" +
                "|Vasia|****    |22|\n" +
                "+-----+--------+--+]", captor.getAllValues().toString());
    }

    @Test
    public void testWithWrongParameters() throws Exception {
        try {
            command.process(new UserInput("insert|user|name"));
            fail("Expected IllegalArgumentException...");
        } catch (Exception e) {
            assertEquals("Должно быть четное количество параметров в формате " +
                    "'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                    "а ты прислал: 'insert|user|name'", e.getMessage());
        }
    }

    @Test
    public void testWithoutParameters() throws Exception {
        try {
            command.process(new UserInput("insert|"));
            fail("Expected IllegalArgumentException...");
        } catch (Exception e) {
            assertEquals("Должно быть четное количество параметров в формате " +
                    "'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                    "а ты прислал: 'insert|'", e.getMessage());
        }
    }
}