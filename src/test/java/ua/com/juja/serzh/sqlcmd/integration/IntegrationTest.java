package ua.com.juja.serzh.sqlcmd.integration;

import org.junit.*;
import ua.com.juja.serzh.sqlcmd.BeforeTestsChangeNameAndPass;
import ua.com.juja.serzh.sqlcmd.Main;
import ua.com.juja.serzh.sqlcmd.model.DataSet;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.JDBCDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by serzh on 5/11/16.
 */
public class IntegrationTest {

    private static final String DATABASE = BeforeTestsChangeNameAndPass.DATABASE;
    private static final String USER = BeforeTestsChangeNameAndPass.USER;
    private static final String PASSWORD = BeforeTestsChangeNameAndPass.PASSWORD;
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    private final String commandConnect = "connect|" + DATABASE + "|" + USER + "|" + PASSWORD;
    private final String pleaseConnect = "Введите имя базы данных, имя пользователя и пароль в формате: " +
            "connect|database|userName|password\r\n";

    @BeforeClass
    public static void buildDatabase() {
        DatabaseManager manager = new JDBCDatabaseManager();
        try {
            manager.connect("", USER, PASSWORD);
        } catch (RuntimeException e) {
            throw new RuntimeException("Для работы тестов измените имя и пароль в классе BeforeTestsChangeNameAndPass."
                    + "\r\n" + e.getCause());
        }
        try {
        manager.dropDatabase(DATABASE);
        } catch (Exception e) {
            // do nothing
        }
        manager.createDatabase(DATABASE);
        manager.connect(DATABASE, USER, PASSWORD);

        manager.createTable("users" +
                " (name VARCHAR (50) UNIQUE NOT NULL, password VARCHAR (50) NOT NULL, id SERIAL PRIMARY KEY)");
        manager.createTable("test1 (id SERIAL PRIMARY KEY)");
        DataSet dataSet = new DataSet();
        dataSet.put("name", "Vasia");
        dataSet.put("password", "****");
        dataSet.put("id", "22");
        manager.insert("users", dataSet);
    }

    @Before
    public void setup() {
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
                "Существующие команды:\r\n" +
                "\tconnect|databaseName|userName|password\r\n" +
                "\t\tдля подключения к базе данных, с которой будем работать\r\n" +
                "\tclear|tableName\r\n" +
                "\t\tдля очистки всей таблицы\r\n" +
                "\tcreateDB|databaseName\r\n" +
                "\t\tдля создания новой Database\r\n" +
                "\tdropDB|databaseName\r\n" +
                "\t\tдля удаления Database\r\n" +
                "\tcreateTable|tableName(column1,column2,...,columnN)\r\n" +
                "\t\tдля создания новой таблицы, в круглых скобках вставить опиание колонок в SQL формате, пример:\r\n" +
                "\t\tcreateTable|user(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))" +
                "\r\n\tdropTable|tableName\r\n" +
                "\t\tдля удаления таблицы\r\n" +
                "\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN\r\n" +
                "\t\tдля создания записи в существующей таблице\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tдля получения содержимого таблицы 'tableName'\r\n" +
                "\thelp\r\n" +
                "\t\tдля вывода этого списка на экран\r\n" +
                "\ttables\r\n" +
                "\t\tдля получения списка всех таблиц базы, к которой подключились\r\n" +
                "\texit\r\n" +
                "\t\tдля выхода из программы\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
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
                "До скорой встречи!\r\n", getData());
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
                "Вы не можете пользоваться командой 'tables' пока не подключитесь с помощью комманды connect|databaseName|userName|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
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
                "Вы не можете пользоваться командой 'find|user' пока не подключитесь с помощью комманды connect|databaseName|userName|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
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
                "Вы не можете пользоваться командой 'unsupported' пока не подключитесь с помощью комманды connect|databaseName|userName|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        // given
        in.add(commandConnect);
        in.add("unsupported");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // unsupported
                "Несуществующая команда: unsupported\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testTablesAfterConnect() {
        // given
        in.add(commandConnect);
        in.add("tables");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // tables
                "[users, test1]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.add(commandConnect);
        in.add("find|users");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "+-----+--------+--+\r\n" +
                "|name |password|id|\r\n" +
                "+-----+--------+--+\r\n" +
                "|Vasia|****    |22|\r\n" +
                "+-----+--------+--+\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testConnectAfterConnect() {
        // given
        in.add(commandConnect);
        in.add("tables");
        in.add("connect|test|" + USER + "|" + PASSWORD);
        in.add("tables");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // tables
                "[users, test1]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // connect test
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // tables
                "[qwe]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
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
                "Неудача! по причине: Формат команды 'connect|databaseName|userName|password', а ты ввел: connect|sqlcmd5hope5never5exist\r\n" +
                "Повтори попытку.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
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
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // clear|users
                "Таблица users была успешно очищена.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // insert|users|id|13|name|Stiven|password|*****
                "В таблице 'users' была успешно добавлена запись:\r\n" +
                "+--+------+--------+\r\n" +
                "|id|name  |password|\r\n" +
                "+--+------+--------+\r\n" +
                "|13|Stiven|*****   |\r\n" +
                "+--+------+--------+\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // insert|users|id|14|name|Eva|password|+++++
                "В таблице 'users' была успешно добавлена запись:\r\n" +
                "+--+----+--------+\r\n" +
                "|id|name|password|\r\n" +
                "+--+----+--------+\r\n" +
                "|14|Eva |+++++   |\r\n" +
                "+--+----+--------+\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // find|users
                "+------+--------+--+\r\n" +
                "|name  |password|id|\r\n" +
                "+------+--------+--+\r\n" +
                "|Stiven|*****   |13|\r\n" +
                "+------+--------+--+\r\n" +
                "|Eva   |+++++   |14|\r\n" +
                "+------+--------+--+\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Таблица users была успешно очищена.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testClearWithError() {
        // given
        in.add(commandConnect);
        in.add("clear|sadfasd|fsf|fdsf");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then

        assertEquals(pleaseConnect +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // clear|sadfasd|fsf|fdsf
                "Неудача! по причине: Формат команды 'clear|tableName', а ты ввел: clear|sadfasd|fsf|fdsf\r\n" +
                "Повтори попытку.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testCreateWithErrors() {
        // given
        in.add(commandConnect);
        in.add("insert|user|error");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(pleaseConnect +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // insert|user|error
                "Неудача! по причине: Должно быть четное количество параметров в формате 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', а ты прислал: 'insert|user|error'\r\n" +
                "Повтори попытку.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Ignore // тест занимает много времени, треть от всех вместе взятых...
    @Test
    public void testCreateDropDatabase() {
        // given
        in.add(commandConnect);
        in.add("createDB|databaseName");
        in.add("dropDB|databaseName");
        in.add("y");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then

        assertEquals(pleaseConnect +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Database databaseName была успешно создана.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Вы уверены, что хотите удалить таблицу databaseName? Y/N\r\n" +
                "Database databaseName была успешно удалена.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

}
