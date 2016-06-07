package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by serzh on 6/6/16.
 */
public class CreateTableSimpleTest {

    private Command command;

    @Before
    public void setup() {
        DatabaseManager manager = mock(DatabaseManager.class);
        View view = mock(View.class);
        command = new CreateTableSimple(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("createTable");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canProcess = command.canProcess("createTable34");
        assertFalse(canProcess);
    }
}