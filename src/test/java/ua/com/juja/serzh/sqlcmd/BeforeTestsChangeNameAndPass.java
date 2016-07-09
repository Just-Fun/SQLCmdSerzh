package ua.com.juja.serzh.sqlcmd;

/**
 * Created by serzh on 5/16/16.
 */
public class BeforeTestsChangeNameAndPass {

    private static String DATABASE = "sqlcmd5hope5never5exist"; // не менять имя базы!!! :)

    // Before use tests change USER(name) and PASSWORD of your database !!! Only next two lines!!!
    private static String USER = "postgres";
    private static String PASSWORD = "postgres";

    public static String getDatabaseName() {
        return DATABASE;
    }

    public static String getUserName() {
        return USER;
    }

    public static String getPassword() {
        return PASSWORD;
    }

}
