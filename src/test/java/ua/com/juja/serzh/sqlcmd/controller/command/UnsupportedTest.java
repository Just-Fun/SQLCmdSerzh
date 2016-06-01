package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by serzh on 13.05.16.
 */
public class UnsupportedTest {
    private View view;
    private Command command;

    @Before
    public void setup() {
        view = mock(View.class);
        command = new Unsupported(view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProceed = command.canProcess("ifDontMatchAnyCommand");
        assertTrue(canProceed);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("ifDontMatchAnyCommand");
        verify(view).write("Несуществующая команда: ifDontMatchAnyCommand");
    }

    @Test
    public void testDescription() throws Exception {
        String description = command.description();
        assertEquals(null, description);
    }

    @Test
    public void testCommandFormat() throws Exception {
        String description = command.commandFormat();
        assertEquals(null, description);
    }
}