package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by serzh on 5/11/16.
 */
public class ExitTest {
    private FakeView view = new FakeView();

    @Test
    public void testCanProcessExitString() {
        Command command = new Exit(view);
        boolean canProcess = command.canProcess("exit");
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessQweString() {
        Command command = new Exit(view);
        boolean canProcess = command.canProcess("qwe");
        assertFalse(canProcess);
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
        assertEquals("До скорой встречи!\n", view.getContent());
    }
}
