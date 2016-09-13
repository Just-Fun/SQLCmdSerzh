package ua.com.juja.serzh.sqlcmd.integration;

import org.junit.*;
import ua.com.juja.serzh.sqlcmd.Configuration;
import ua.com.juja.serzh.sqlcmd.Support;
import ua.com.juja.serzh.sqlcmd.Main;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.PostgresManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * Created by serzh on 5/11/16.
 */
public class IntegrationTest {

    private static Configuration configuration = new Configuration();
    private static final String DATABASE = configuration.getDatabase();
    private static final String USER = configuration.getUser();
    private static final String PASSWORD = configuration.getPassword();

    private final String commandConnect = "connect|" + DATABASE + "|" + USER + "|" + PASSWORD;
    private final String commandDisconnect = "connect|" + "" + "|" + USER + "|" + PASSWORD;
    private final String pleaseConnect = "Enter the name of the database, which will work, a user name and password in the format: " +
            "connect|database|userName|password\n";

    private static DatabaseManager manager;
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @BeforeClass
    public static void buildDatabase() {
        manager = new PostgresManager();
        Support.setupData(manager);
    }

    @Before
    public void setup() {
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @AfterClass
    public static void dropDatabase() {
        Support.dropData(manager);
    }

    @Test
    public void testHelp() {
        // given
        in.add("help");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // help
                "Current commands:\n" +
                "\tconnect|databaseName|userName|password\n" +
                "\t\tto connect to a database, which will work\n" +
                "\tdatabases\n" +
                "\t\tto obtain a list of databases\n" +
                "\ttables\n" +
                "\t\tfor a list of all database tables, which are connected to the\n" +
                "\tcreateDB|databaseName\n" +
                "\t\tto create a new Database. Database name must start with a letter.\n" +
                "\tdropDB|databaseName\n" +
                "\t\tto remove the database. The base must be free of any konekshina.\n" +
                "\tcreateTable\n" +
                "\t\tto create a new table in steps\n" +
                "\tcreateTableSQL|tableName(column1,column2,...,columnN)\n" +
                "\t\tto create a new table for those who know SQL, parentheses opianie insert columns in SQL format example:\n" +
                "\t\tcreateTableSQL|user1(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))\n" +
                "\tsize|tableName\n" +
                "\t\tThe number of rows in the table\n" +
                "\tclear|tableName\n" +
                "\t\tto clean up the entire table\n" +
                "\tdropTable|tableName\n" +
                "\t\tto delete the table\n" +
                "\tinsertTable|tableName\n" +
                "\t\tto step through the creation of records in the existing table\n" +
                "\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN\n" +
                "\t\tto create a record in the existing table\n" +
                "\tfind|tableName\n" +
                "\t\tto obtain the contents of the table 'tableName'\n" +
                "\thelp\n" +
                "\t\tto display that list on the screen\n" +
                "\texit\n" +
                "\t\tto exit from the program\n" +
                "Enter the command (or help for assistance):\n" +
                "See you soon!\n", getData());
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8").replaceAll("\r\n", "\n");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testExit() {
        // given
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testTablesWithoutConnect() {
        // given
        in.add("tables");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // tables
                "You can not use the commands until you connect using command connect|databaseName|userName|password\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testFindWithoutConnect() {
        // given
        in.add("find|user");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // find|user
                "You can not use the commands until you connect using command connect|databaseName|userName|password\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testUnsupported() {
        // given
        in.add("unsupported");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // unsupported
                "You can not use the commands until you connect using command connect|databaseName|userName|password\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        // given
        in.add(commandConnect);
        in.add("unsupported");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // unsupported
                "Nonexistent command: unsupported\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testTablesAfterConnect() {
        // given
        in.add(commandConnect);
        in.add("tables");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // tables
                "Existing table: users, test1, users2\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.add(commandConnect);
        in.add("find|users");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "+-----+--------+--+\n" +
                "|name |password|id|\n" +
                "+-----+--------+--+\n" +
                "|Vasia|****    |22|\n" +
                "+-----+--------+--+\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "See you soon!\n", getData());
    }

    @Test
    public void testConnectWithError() {
        // given
        in.add("connect|" + DATABASE);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Failure! because of: The command format is 'connect|databaseName|userName|password', but you typed: connect|sqlcmd5hope5never5exist\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testFindAfterConnect_withData() {
        // given
        in.add(commandConnect);
        in.add("clear|users");
        in.add("insert|users|id|13|name|Stiven|password|*****");
        in.add("insert|users|id|14|name|Eva|password|+++++");
        in.add("find|users");
        in.add("clear|users");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // clear|users
                "Table users has been successfully cleared.\n" +
                "Enter the command (or help for assistance):\n" +
                // insert|users|id|13|name|Stiven|password|*****
                "The table 'users' has been successfully added record:\n" +
                "+--+------+--------+\n" +
                "|id|name  |password|\n" +
                "+--+------+--------+\n" +
                "|13|Stiven|*****   |\n" +
                "+--+------+--------+\n" +
                "Enter the command (or help for assistance):\n" +
                // insert|users|id|14|name|Eva|password|+++++
                "The table 'users' has been successfully added record:\n" +
                "+--+----+--------+\n" +
                "|id|name|password|\n" +
                "+--+----+--------+\n" +
                "|14|Eva |+++++   |\n" +
                "+--+----+--------+\n" +
                "Enter the command (or help for assistance):\n" +
                // find|users
                "+------+--------+--+\n" +
                "|name  |password|id|\n" +
                "+------+--------+--+\n" +
                "|Stiven|*****   |13|\n" +
                "+------+--------+--+\n" +
                "|Eva   |+++++   |14|\n" +
                "+------+--------+--+\n" +
                "Enter the command (or help for assistance):\n" +
                "Table users has been successfully cleared.\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testClearWithError() {
        // given
        in.add(commandConnect);
        in.add("clear|sadfasd|fsf|fdsf");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then

        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // clear|sadfasd|fsf|fdsf
                "Failure! because of: The command format is 'clear|tableName', but you typed: clear|sadfasd|fsf|fdsf\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testCreateWithErrors() {
        // given
        in.add(commandConnect);
        in.add("insert|user|error");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // insert|user|error
                "Failure! because of: Must be an even number of parameters in a format 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', but you typed: 'insert|user|error'\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

    @Test
    public void testCreateTableSimple() {
        // given
        in.add(commandConnect);
        in.add("createTable");
        in.add("users5");
        in.add("id");
        in.add("name");
        in.add("password");
        in.add("5");
        in.add("dropTable|users5");
        in.add("Y");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "Enter a name for the table(name must start with a letter) or '0' to exit to the main menu\n" +
                "Name the new database: users5\n" +
                "Enter a name for the column PRIMARY KEY(name must start with a letter) or '0' to exit to the main menu\n" +
                "Column name PRIMARY KEY: id\n" +
                "Enter the name of another column(name must start with a letter) or '5' to create a database with the introduction column or '0' to exit to the main menu\n" +
                "Name for another column: name\n" +
                "Enter the name of another column(name must start with a letter) or '5' to create a database with the introduction column or '0' to exit to the main menu\n" +
                "Name for another column: password\n" +
                "Enter the name of another column(name must start with a letter) or '5' to create a database with the introduction column or '0' to exit to the main menu\n" +
                "Table users5 was successfully created.\n" +
                "+--+----+--------+\n" +
                "|id|name|password|\n" +
                "+--+----+--------+\n" +
                "Enter the command (or help for assistance):\n" +
                "Are you sure you want to delete users5? Y/N\n" +
                "Table users5 has been successfully removed.\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "See you soon!\n", getData());
    }

    @Test
    public void testTableSimpleExit() {
        // given
        in.add(commandConnect);
        in.add("createTable");
        in.add("");
        in.add("1");
        in.add("0");
        in.add("createTable");
        in.add("user6");
        in.add("");
        in.add("0");
        in.add("createTable");
        in.add("user6");
        in.add("id");
        in.add("");
        in.add("0");
        in.add("createTable");
        in.add("user6");
        in.add("id");
        in.add("name");
        in.add("0");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "Enter a name for the table(name must start with a letter) or '0' to exit to the main menu\n" +
                "You must enter a name for the table, and you enter an empty string\n" +
                "Enter a name for the table(name must start with a letter) or '0' to exit to the main menu\n" +
                "Name must start with a letter, and you start with '1'\n" +
                "Enter a name for the table(name must start with a letter) or '0' to exit to the main menu\n" +
                "Exit to the main menu\n" +
                "Enter the command (or help for assistance):\n" +
                "Enter a name for the table(name must start with a letter) or '0' to exit to the main menu\n" +
                "Name the new database: user6\n" +
                "Enter a name for the column PRIMARY KEY(name must start with a letter) or '0' to exit to the main menu\n" +
                "You must enter a name for the column PRIMARY KEY, and you enter an empty string\n" +
                "Enter a name for the column PRIMARY KEY(name must start with a letter) or '0' to exit to the main menu\n" +
                "Exit to the main menu\n" +
                "Enter the command (or help for assistance):\n" +
                "Enter a name for the table(name must start with a letter) or '0' to exit to the main menu\n" +
                "Name the new database: user6\n" +
                "Enter a name for the column PRIMARY KEY(name must start with a letter) or '0' to exit to the main menu\n" +
                "Column name PRIMARY KEY: id\n" +
                "Enter the name of another column(name must start with a letter) or '5' to create a database with the introduction column or '0' to exit to the main menu\n" +
                "You must enter a name for the column, and you enter an empty string\n" +
                "Enter the name of another column(name must start with a letter) or '5' to create a database with the introduction column or '0' to exit to the main menu\n" +
                "Exit to the main menu\n" +
                "Enter the command (or help for assistance):\n" +
                "Enter a name for the table(name must start with a letter) or '0' to exit to the main menu\n" +
                "Name the new database: user6\n" +
                "Enter a name for the column PRIMARY KEY(name must start with a letter) or '0' to exit to the main menu\n" +
                "Column name PRIMARY KEY: id\n" +
                "Enter the name of another column(name must start with a letter) or '5' to create a database with the introduction column or '0' to exit to the main menu\n" +
                "Name for another column: name\n" +
                "Enter the name of another column(name must start with a letter) or '5' to create a database with the introduction column or '0' to exit to the main menu\n" +
                "Exit to the main menu\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "See you soon!\n", getData());
    }

    @Test
    public void testInsertSimple() {
        // given
        in.add(commandConnect);
        in.add("insertTable|users2");
        in.add("1");
        in.add("Frank");
        in.add("****");
        in.add("clear|users2");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "Enter a value in the field  'id' or enter '0' to exit to the main menu.\n" +
                "Enter a value in the field  'username' or enter '0' to exit to the main menu.\n" +
                "Enter a value in the field  'password' or enter '0' to exit to the main menu.\n" +
                "The table 'users2' has been successfully added record:\n" +
                "+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+\n" +
                "|1 |Frank   |****    |\n" +
                "+--+--------+--------+\n" +
                "Enter the command (or help for assistance):\n" +
                "Table users2 has been successfully cleared.\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "See you soon!\n", getData());
    }

    @Test
    public void testInsertSimpleExit() {
        // given
        in.add(commandConnect);
        in.add("insertTable|users2");
        in.add("");
        in.add("1");
        in.add("0");
        in.add("insertTable|users2");
        in.add("1");
        in.add("");
        in.add("Frank");
        in.add("0");
        in.add("insertTable|users2");
        in.add("1");
        in.add("Frank");
        in.add("");
        in.add("0");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "Enter a value in the field  'id' or enter '0' to exit to the main menu.\n" +
                "You must enter a name for the column PRIMARY KEY, and you enter an empty string\n" +
                "Enter a value in the field  'id' or enter '0' to exit to the main menu.\n" +
                "Enter a value in the field  'username' or enter '0' to exit to the main menu.\n" +
                "Exit to the main menu\n" +
                "Enter the command (or help for assistance):\n" +
                "Enter a value in the field  'id' or enter '0' to exit to the main menu.\n" +
                "Enter a value in the field  'username' or enter '0' to exit to the main menu.\n" +
                "You must enter a name for the column PRIMARY KEY, and you enter an empty string\n" +
                "Enter a value in the field  'username' or enter '0' to exit to the main menu.\n" +
                "Enter a value in the field  'password' or enter '0' to exit to the main menu.\n" +
                "Exit to the main menu\n" +
                "Enter the command (or help for assistance):\n" +
                "Enter a value in the field  'id' or enter '0' to exit to the main menu.\n" +
                "Enter a value in the field  'username' or enter '0' to exit to the main menu.\n" +
                "Enter a value in the field  'password' or enter '0' to exit to the main menu.\n" +
                "You must enter a name for the column PRIMARY KEY, and you enter an empty string\n" +
                "Enter a value in the field  'password' or enter '0' to exit to the main menu.\n" +
                "Exit to the main menu\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "See you soon!\n", getData());
    }

    @Test
    public void testDropDatabaseException() {
        // given
        in.add(commandConnect);
        in.add("dropDB|sqlcmd965823756925");
        in.add("y");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then

        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "Are you sure you want to delete sqlcmd965823756925? Y/N\n" +
                "Failure! because of: ERROR: database \"sqlcmd965823756925\" does not exist\n" +
                "Enter the command (or help for assistance):\n" +
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "See you soon!\n", getData());
    }

    @Ignore // test takes a long time
    @Test
    public void testCreateDropDatabase() {
        // given
        in.add(commandConnect);
        in.add("createDB|sqlcmd9hope9never9exist");
        in.add("dropDB|sqlcmd9hope9never9exist");
        in.add("y");
        in.add(commandDisconnect);
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then

        assertEquals(pleaseConnect +
                // connect
                "Success!\n" +
                "Enter the command (or help for assistance):\n" +
                "Database sqlcmd9hope9never9exist was successfully created.\n" +
                "Enter the command (or help for assistance):\n" +
                "Are you sure you want to delete sqlcmd9hope9never9exist? Y/N\n" +
                "Database 'sqlcmd9hope9never9exist' has been successfully removed.\n" +
                "Enter the command (or help for assistance):\n" +
                // exit
                "See you soon!\n", getData());
    }

}
