package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Created by serzh on 04.06.16.
 */
public class TableSizeTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new TableSize(manager, view);
    }

    @Test
    public void testPrintGetTableSize() {
        when(manager.getTableSize("user")).thenReturn(3);
        command.process("size|user");
        shouldPrint("[Количество строк в таблице 'user': 3.]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

    @Test
    public void canProcessWithRightParameter() {
        boolean canProcess = command.canProcess("size|user");
        assertTrue(canProcess);
    }

    @Test
    public void canProcessWithWrongParameter() {
        boolean canNotProcess = command.canProcess("ghkk");
        assertFalse(canNotProcess);
    }

    @Test
    public void canProcessListWithoutParameter() {
        boolean canNotProcess = command.canProcess("");
        assertFalse(canNotProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        try {
            command.process("size|table|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'size|tableName', а ты ввел: size|table|qwe", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        try {
            command.process("size");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'size|tableName', а ты ввел: size", e.getMessage());
        }
    }

    @Test //
    public void testCanProcessFindWithOnlySlash() {
        try {
            command.process("size|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'size|tableName', а ты ввел: size|", e.getMessage());
        }
    }
}