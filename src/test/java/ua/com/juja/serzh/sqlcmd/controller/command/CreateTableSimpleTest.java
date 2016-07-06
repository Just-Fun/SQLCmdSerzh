package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.PostgresManager;
import ua.com.juja.serzh.sqlcmd.view.Console;
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
    private View view;
    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = mock(PostgresManager.class);
        view = mock(Console.class);
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

    @Test
    public void testExit() throws Exception {
        when(view.read()).thenReturn("0");
        command.process("createTable");

        verify(view).write(("Выход в основное меню"));
    }

    @Test
    public void testCreateOnlyIdColumn() throws Exception {
        when(view.read()).thenReturn("users5")
                .thenReturn("id")
                .thenReturn("5");
        command.process("createTable");

        verify(view).write(("Введите имя для создаваемой таблицы(имя должно начинаться с буквы) или '0' для выхода в основное меню"));
        verify(view).write(("Имя новой базы: users5"));
        verify(view).write(("Введите имя для колонки PRIMARY KEY(имя должно начинаться с буквы) или '0' для выхода в основное меню"));
        verify(view).write(("Имя колонки PRIMARY KEY: id"));
        verify(view).write(("Введите имя для еще одной колонки(имя должно начинаться с буквы) или '5' для создания базы с введенными колонками или '0' для выхода в основное меню"));
        verify(view).write(("Таблица users5 была успешно создана."));
    }
}