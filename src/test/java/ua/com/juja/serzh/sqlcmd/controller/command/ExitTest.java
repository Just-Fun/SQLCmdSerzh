package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Created by serzh on 5/11/16.
 */
public class ExitTest {
    private View view = Mockito.mock(View.class);
    private Command command;

    @Before
    public void setup() {
        view = Mockito.mock(View.class);
        command = new Exit(view);
    }

    @Test
    public void testCanProcessExitString() {
        boolean canProcess = command.canProcess(("exit"));
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessQweString() {
        boolean canNotProcess = command.canProcess(("qwe"));
        assertFalse(canNotProcess);
    }

    @Test
    public void testProcessExitCommand_thowsExitException() {
        try {
            command.process(("exit"));
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        }
        Mockito.verify(view).write("До скорой встречи!");
    }
}
