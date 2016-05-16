package ua.com.juja.serzh.sqlcmd.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by serzh on 16.05.16.
 */
public class DataSetTest {

    @Test
    public void testPut() throws Exception {
        DataSet dataSet = new DataSet();
        dataSet.put("Happiness", 1);
        dataSet.put("Happiness2", 2);
        assertEquals("{names:[Happiness, Happiness2], values:[1, 2]}", dataSet.toString());
    }

    @Test
    public void testPutTheSameName() throws Exception {
        DataSet dataSet = new DataSet();
        dataSet.put("Happiness", 1);
        dataSet.put("Happiness", 1);
        assertEquals("{names:[Happiness], values:[1]}", dataSet.toString());
    }
}