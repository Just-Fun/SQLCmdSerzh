package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by serzh on 5/19/16.
 */
public class HelpTest {
    DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        view = Mockito.mock(View.class);
        command = new Help(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("help");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongCommand() throws Exception {
        boolean canNotProcess = command.canProcess("helpBadWay");
        assertFalse(canNotProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("help");
        verify(view).write("Существующие команды:");
    }

}