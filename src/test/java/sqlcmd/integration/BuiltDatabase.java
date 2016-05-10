package sqlcmd.integration;

import sqlcmd.Main;
import sqlcmd.controller.command.ExitException;
import sqlcmd.model.DatabaseManager;
import sqlcmd.model.JDBCDatabaseManager;

import java.io.ByteArrayOutputStream;

/**
 * Created by serzh on 5/10/16.
 */
public class BuiltDatabase {

    public static void main(String[] args) {
        buildDatabase();
    }

    public static void buildDatabase() {
        // when
        DatabaseManager manager = new JDBCDatabaseManager();
        manager.connect("", "postgres", "postgres");
        manager.dropDatabase("sqlcmd");
        manager.createDatabase("sqlcmd");
        manager.connect("sqlcmd", "postgres", "postgres");
//        manager.connect("sqlcmd", "", "");


        manager.createTable("users" + " (name VARCHAR (50) UNIQUE NOT NULL," +
                " password VARCHAR (50) NOT NULL," + " id SERIAL PRIMARY KEY)");
        manager.createTable("test1 (id SERIAL PRIMARY KEY)");
//        manager.connect("", "postgres", "postgres");

//        throw new ExitException();

//        manager.disconnectFromDatabase("sqlcmd");
        manager.disconnectFromDatabase2();
    }
}
