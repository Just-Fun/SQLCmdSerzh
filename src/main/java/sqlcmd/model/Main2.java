package sqlcmd.model;

/**
 * Created by serzh on 5/10/16.
 */
public class Main2 {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new JDBCDatabaseManager();
//        databaseManager.connect("", "postgres", "postgres");
//        databaseManager.dropDatabase("sqlcmd");
//        databaseManager.createDatabase("sqlcmd");
//         String DATABASE_NAME = "sqlcmd";
//         String TABLE_NAME = "user";
//        databaseManager.dropDatabase("sqlcmd");
//        databaseManager.createDatabase("sqlcmd");
        databaseManager.connect("sqlcmd", "postgres", "postgres");
       /* String SQL_CREATE_TABLE = "users (id SERIAL PRIMARY KEY," +
                " username VARCHAR (50) UNIQUE NOT NULL," +
                " password VARCHAR (50) NOT NULL)";*/
//        databaseManager.createTable(SQL_CREATE_TABLE);
        databaseManager.createTable("test1 (id SERIAL PRIMARY KEY)");
        databaseManager.createTable("users ( name VARCHAR (50) UNIQUE NOT NULL, " +
                "password VARCHAR (50) NOT NULL, id SERIAL PRIMARY KEY)");
    }
}
