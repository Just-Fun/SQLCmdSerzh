package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;

/**
 * Created by serzh on 06.06.16.
 */
public class ExitExceptionTest {
    private View view = Mockito.mock(View.class);
    Command command = new Exit(view);

    @Test(expected = ExitException.class)
    public void testProcessExitCommand_thowsExitException() {
        command.process("exit");
    }
}