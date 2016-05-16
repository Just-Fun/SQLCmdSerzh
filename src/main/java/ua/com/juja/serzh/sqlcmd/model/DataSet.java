package ua.com.juja.serzh.sqlcmd.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by serzh on 5/11/16.
 */
public class DataSet {

    static class Data {
        private String name;
        private Object value;

        public Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }

    public List<Data> data = new LinkedList<>();

    public void put(String name, Object value) {
        for (Data d : data) {
            if (d.getName().equals(name)) {
                d.value = value;
                return;
            }
        }
        data.add(new Data(name, value));
    }

    public Object[] getValues() {
        Object[] result = new Object[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).getName();
        }
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "names:" + Arrays.toString(getNames()) + ", " +
                "values:" + Arrays.toString(getValues()) +
                "}";
    }
}
