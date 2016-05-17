package ua.com.juja.serzh.sqlcmd.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by serzh on 5/11/16.
 */
public class DataSet {

    private List<Data> data = new LinkedList<>();

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

    public void put(String name, Object value) {
        for (Data d : data) {
            if (d.getName().equals(name)) {
                d.value = value;
                return;
            }
        }
        data.add(new Data(name, value));
    }

    public List<Object> getValues() {
        List<Object> result = new LinkedList<>();
        for (Data d : data){
            result.add(d.getValue());
        }
        return result;
    }

    public List<String> getNames() {
        List<String> result = new LinkedList<>();
        for (Data d : data){
            result.add(d.getName());
        }
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "names:" + getNames().toString() + ", " +
                "values:" + getValues().toString() +
                "}";
    }
}
