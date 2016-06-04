package ua.com.juja.serzh.sqlcmd;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by serzh on 04.06.16.
 */
public class Support {

    private static final String DATABASE = BeforeTestsChangeNameAndPass.DATABASE;
    private static final String USER = BeforeTestsChangeNameAndPass.USER;
    private static final String PASSWORD = BeforeTestsChangeNameAndPass.PASSWORD;

    public static void setupData(DatabaseManager manager) {
        manager.connect("", USER, PASSWORD);
        manager.createDatabase(DATABASE);
        manager.connect(DATABASE, USER, PASSWORD);
        createTablesWithData(manager);
    }

    public static void dropData(DatabaseManager manager) {
        try {
            manager.connect("", USER, PASSWORD);
            manager.dropDatabase(DATABASE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTablesWithData(DatabaseManager manager) {
        manager.createTable("users" +
                " (name VARCHAR (50) UNIQUE NOT NULL, password VARCHAR (50) NOT NULL, id SERIAL PRIMARY KEY)");
        manager.createTable("test1 (id SERIAL PRIMARY KEY)");
        Map<String, Object> dataSet = new LinkedHashMap<>();
        dataSet.put("name", "Vasia");
        dataSet.put("password", "****");
        dataSet.put("id", "22");
        manager.insert("users", dataSet);
    }
}
