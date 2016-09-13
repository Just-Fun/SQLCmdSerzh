package ua.com.juja.serzh.sqlcmd;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by serzh on 5/16/16.
 */
public class Configuration {

    private final static String PROPERTIES_FILE = "src/main/resources/config.properties";

    private String database;
    private String user;
    private String password;

    public Configuration() {
        loadProperties();
    }

    private void loadProperties() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            property.load(fis);
            database = property.getProperty("database");
            user = property.getProperty("user");
            password = property.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException("Properties do not loaded. " + e.getCause());
        }
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
