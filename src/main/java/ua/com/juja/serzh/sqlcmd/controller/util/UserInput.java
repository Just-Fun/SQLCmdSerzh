package ua.com.juja.serzh.sqlcmd.controller.util;

import ua.com.juja.serzh.sqlcmd.controller.command.Command;
import ua.com.juja.serzh.sqlcmd.model.TableConstructor;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by serzh on 19.05.16.
 */
public class UserInput {
    String userCommand;

    public UserInput(String userCommand) {
        this.userCommand = userCommand;

    }

    public int parametersLength() {
        return (userCommand.split("\\|")).length;
    }

    public String[] splitInput() {
        return userCommand.split("\\|");
    }

    @Override
    public String toString() {
        return userCommand;
    }

    public TableConstructor getTableConstructorFromDataSet(Map<String, Object> dataSet) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.putAll(dataSet);

        List<Map<String, Object>> tableData = new LinkedList<>();
        tableData.add(map);
        return new TableConstructor(dataSet.keySet(), tableData);
    }
}
