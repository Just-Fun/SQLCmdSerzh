package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Created by serzh on 5/11/16.
 */
public class ExitTest {
    private View view = Mockito.mock(View.class);

    @Test
    public void testCanProcessExitString() {
        Command command = new Exit(view);
        boolean canProcess = command.canProcess("exit");
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessQweString() {
        Command command = new Exit(view);
        boolean canNotProcess = command.canProcess("qwe");
        assertFalse(canNotProcess);
    }

    @Test
    public void testProcessExitCommand_thowsExitException() {
        Command command = new Exit(view);

        try {
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        }
        Mockito.verify(view).write("До скорой встречи!");
    }
}
