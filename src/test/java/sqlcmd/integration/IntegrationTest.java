package sqlcmd.integration;

import org.junit.*;
import sqlcmd.Main;
import sqlcmd.model.DataSet;
import sqlcmd.model.DatabaseManager;
import sqlcmd.model.JDBCDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * Created by indigo on 28.08.2015.
 */
public class IntegrationTest {

    private DatabaseManager databaseManager;
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    //TODO Change user name and password of your database !!! Only next two lines!!!
    private final static String USER_NAME = "postgres";
    private final static String DB_PASSWORD = "postgres";

    private final static String DATABASE_NAME = "sqlcmd5hope5never5exist";
    String connect = "connect|" + DATABASE_NAME + "|" + USER_NAME + "|" + DB_PASSWORD;
    private final String pleaseConnect = "Введите имя базы данных, имя пользователя и пароль в формате: " +
            "connect|database|userName|password\n";

    @BeforeClass
    public static void builtTheBase() {
        buildDatabase();
    }

    @Before
    public void setup() {
        databaseManager = new JDBCDatabaseManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
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
                "Существующие команды:\n" +
                "\tconnect|databaseName|userName|password\n" +
                "\t\tдля подключения к базе данных, с которой будем работать\n" +
                "\tclear|tableName\n" +
                "\t\tдля очистки всей таблицы\n" +
                "\tcreateDB|databaseName\n" +
                "\t\tдля создания новой Database\n" +
                "\tdropDB|databaseName\n" +
                "\t\tдля удаления Database\n" +
                "\tcreateTable|tableName\n" +
                "\t\tдля создания новой таблицы\n" +
                "\tdropTable|tableName\n" +
                "\t\tдля удаления таблицы\n" +
                "\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN\n" +
                "\t\tдля создания записи в таблице\n" +
                "\tfind|tableName\n" +
                "\t\tдля получения содержимого таблицы 'tableName'\n" +
                "\thelp\n" +
                "\t\tдля вывода этого списка на экран\n" +
                "\tlist\n" +
                "\t\tдля получения списка всех таблиц базы, к которой подключились\n" +
                "\texit\n" +
                "\t\tдля выхода из программы\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
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
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testListWithoutConnect() {
        // given
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(pleaseConnect +
                // list
                "Вы не можете пользоваться командой 'list' пока не подключитесь с помощью комманды connect|databaseName|userName|password\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
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
                "Вы не можете пользоваться командой 'find|user' пока не подключитесь с помощью комманды connect|databaseName|userName|password\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
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
                "Вы не можете пользоваться командой 'unsupported' пока не подключитесь с помощью комманды connect|databaseName|userName|password\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        // given

        in.add(connect);
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(pleaseConnect +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // unsupported
                "Несуществующая команда: unsupported\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testListAfterConnect() {
        // given
        in.add(connect);
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(pleaseConnect +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // list
                "[users, test1]\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.add(connect);
        in.add("find|users");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(pleaseConnect +
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                "+-----+--------+--+\n" +
                "|name |password|id|\n" +
                "+-----+--------+--+\n" +
                "|Vasia|****    |22|\n" +
                "+-----+--------+--+\n" +
                "Введи команду (или help для помощи):\n" +
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testConnectAfterConnect() {
        // given
        in.add(connect);
        in.add("list");
        in.add("connect|test|postgres|postgres");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(pleaseConnect +
                // connect sqlcmd
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // list
                "[users, test1]\n" +
                "Введи команду (или help для помощи):\n" +
                // connect test
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // list
                "[qwe]\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testConnectWithError() {
        // given
        in.add("connect|" + DATABASE_NAME);
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(pleaseConnect +
                // connect sqlcmd
                "Неудача! по причине: Неверно количество параметров разделенных знаком '|', ожидается 4, но есть: 2\n" +
                "Повтори попытку.\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testFindAfterConnect_withData() {
        // given
        in.add(connect);
        in.add("clear|users");
        in.add("insert|users|id|13|name|Stiven|password|*****");
        in.add("insert|users|id|14|name|Eva|password|+++++");
        in.add("find|users");
        in.add("clear|users");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(pleaseConnect +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // clear|user
                "Таблица users была успешно очищена.\n" +
                "Введи команду (или help для помощи):\n" +
                // insert|user|id|13|name|Stiven|password|*****
                "Запись {names:[id, name, password], values:[13, Stiven, *****]} была успешно создана в таблице 'users'.\n" +
                "Введи команду (или help для помощи):\n" +
                // insert|user|id|14|name|Eva|password|+++++
                "Запись {names:[id, name, password], values:[14, Eva, +++++]} была успешно создана в таблице 'users'.\n" +
                "Введи команду (или help для помощи):\n" +
                // find|user
                "+------+--------+--+\n" +
                "|name  |password|id|\n" +
                "+------+--------+--+\n" +
                "|Stiven|*****   |13|\n" +
                "+------+--------+--+\n" +
                "|Eva   |+++++   |14|\n" +
                "+------+--------+--+\n" +
                "Введи команду (или help для помощи):\n" +
                "Таблица users была успешно очищена.\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testClearWithError() {
        // given
        in.add(connect);
        in.add("clear|sadfasd|fsf|fdsf");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then

        assertEquals(pleaseConnect +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // clear|sadfasd|fsf|fdsf
                "Неудача! по причине: Формат команды 'clear|tableName', а ты ввел: clear|sadfasd|fsf|fdsf\n" +
                "Повтори попытку.\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testCreateWithErrors() {
        // given
        in.add(connect);
        in.add("insert|user|error");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(pleaseConnect +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // insert|user|error
                "Неудача! по причине: Должно быть четное количество параметров в формате 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', а ты прислал: 'insert|user|error'\n" +
                "Повтори попытку.\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    public static void buildDatabase() {
        DatabaseManager manager = new JDBCDatabaseManager();
        manager.connect("", "postgres", "postgres");
        manager.dropDatabase(DATABASE_NAME);
        manager.createDatabase(DATABASE_NAME);
        manager.connect(DATABASE_NAME, USER_NAME, DB_PASSWORD);

        manager.createTable("users" + " (name VARCHAR (50) UNIQUE NOT NULL," +
                " password VARCHAR (50) NOT NULL," + " id SERIAL PRIMARY KEY)");
        manager.createTable("test1 (id SERIAL PRIMARY KEY)");
        DataSet dataSet = new DataSet();
        dataSet.put("name", "Vasia");
        dataSet.put("password", "****");
        dataSet.put("id", "22");
        manager.insert("users", dataSet);
    }
}
