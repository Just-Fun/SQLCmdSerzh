package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.model.DataSet;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by serzh on 5/19/16.
 */
public class InsertTest {
    DatabaseManager manager;
    View view;
    Command command;

    @Before
    public void setup() {
        manager = Mockito.mock(DatabaseManager.class);
        view = Mockito.mock(View.class);
        command = new Insert(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("insert|");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongInput() throws Exception {
        boolean canNotProcess = command.canProcess("inser|");
        assertFalse(canNotProcess);
    }

    @Test
    public void testProcess() throws Exception {
       /* DataSet dataSet = new DataSet();
        dataSet.put("Vasia", 1);
        dataSet.put("Petia", 2);
        when(manager.insert("user", dataSet)).thenReturn();*/
    }

    @Test
    public void testDescription() throws Exception {

    }

    @Test
    public void testCommandFormat() throws Exception {

    }
}