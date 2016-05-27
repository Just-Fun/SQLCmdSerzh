package ua.com.juja.serzh.sqlcmd.model;

import java.util.*;

/**
 * Created by serzh on 5/11/16.
 */
//TODO:Иван можно выпилить DataSet потому что он просто оборачивает в себя  Map<String,Object>
//TODO:Иван и использовать уже Map где нужно
public class DataSet {

    private Map<String, Object> data = new LinkedHashMap<>();

    public void put(String name, Object value) {
        data.put(name, value);
    }

    public List<Object> getValues() {
        return new ArrayList<>(data.values());
    }

    public Set<String> getNames() { return data.keySet(); }

    public Map<String, Object> returnData() { return data; }

    @Override
    public String toString() {
        return "{" +
                "names:" + getNames().toString() + ", " +
                "values:" + getValues().toString() +
                "}";
    }
}
