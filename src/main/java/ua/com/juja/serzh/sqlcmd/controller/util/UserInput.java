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

    public void validationParameters(String commandFormat) {
        int formatLength = (commandFormat.split("\\|")).length;
        if (formatLength != parametersLength()) {
            throw new IllegalArgumentException(String.format("Формат команды '%s', а ты ввел: %s", commandFormat, userCommand));
        }
    }

    public void validationPairParameters(UserInput input, Command command) {
        if (parametersLength() % 2 != 0) {
            throw new IllegalArgumentException(String.format("Должно быть четное количество параметров " +
                    "в формате '%s', а ты прислал: '%s'",command.commandFormat(), input.toString()));
        }
    }

    private int parametersLength() {
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
