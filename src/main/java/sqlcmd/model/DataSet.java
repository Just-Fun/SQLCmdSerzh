package sqlcmd.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by indigo on 21.08.2015.
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

    // переписать все под List<Map<String, Object>>

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

    public Object get(String name) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equals(name)) {
                return data.get(i).getValue();
            }
        }
        return null;
    }

    public void updateFrom(DataSet newValue) {
        for (int index = 0; index < newValue.data.size(); index++) {
            Data data = newValue.data.get(index); // не понял что тут натворил и что это, разобраться, если вообще этот метод надо..
            this.put(data.name, data.value);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "names:" + Arrays.toString(getNames()) + ", " +
                "values:" + Arrays.toString(getValues()) +
                "}";
    }

   /* public Data[] data = new Data[100]; // TODO remove magic number 100
    public int freeIndex = 0;

    public void put(String name, Object value) {
        for (int index = 0; index < freeIndex; index++) {
            if (data[index].getName().equals(name)) {
                data[index].value = value;
                return;
            }
        }

        data[freeIndex++] = new Data(name, value);
    }

    public Object[] getValues() {
        Object[] result = new Object[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    public Object get(String name) {
        for (int i = 0; i < freeIndex; i++) {
            if (data[i].getName().equals(name)) {
                return data[i].getValue();
            }
        }
        return null;
    }

    public void updateFrom(DataSet newValue) {
        for (int index = 0; index < newValue.freeIndex; index++) {
            Data data = newValue.data[index];
            this.put(data.name, data.value);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "names:" + Arrays.toString(getNames()) + ", " +
                "values:" + Arrays.toString(getValues()) +
                "}";
    }*/

}
