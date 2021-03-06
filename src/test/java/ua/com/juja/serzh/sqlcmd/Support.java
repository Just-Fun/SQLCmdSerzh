package ua.com.juja.serzh.sqlcmd;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by serzh on 04.06.16.
 */
public class Support {

    private Configuration configuration = new Configuration();
    private final String DATABASE = configuration.getDatabase();
    private final String USER = configuration.getUser();
    private final String PASSWORD = configuration.getPassword();

    public void setupData(DatabaseManager manager) {
        try {
            manager.connect("", USER, PASSWORD);
        } catch (RuntimeException e) {
            throw new RuntimeException("For testing, change the name and password in a file config.properties."
                    + "\n" + e.getCause());
        }
        manager.createDatabase(DATABASE);
        manager.connect(DATABASE, USER, PASSWORD);
        createTablesWithData(manager);
    }

    public void dropData(DatabaseManager manager) {
        try {
            manager.connect("", USER, PASSWORD);
            manager.dropDatabase(DATABASE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createTablesWithData(DatabaseManager manager) {
        manager.createTable("users" +
                " (name VARCHAR (50) UNIQUE NOT NULL, password VARCHAR (50) NOT NULL, id SERIAL PRIMARY KEY)");
        manager.createTable("test1 (id SERIAL PRIMARY KEY)");
        manager.createTable("users2" +
                " (id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))");
        Map<String, Object> dataSet = new LinkedHashMap<>();
        dataSet.put("name", "Vasia");
        dataSet.put("password", "****");
        dataSet.put("id", "22");
        manager.insert("users", dataSet);
    }
}
