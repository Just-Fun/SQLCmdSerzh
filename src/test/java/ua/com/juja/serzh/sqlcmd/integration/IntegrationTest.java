package ua.com.juja.serzh.sqlcmd.integration;

import org.junit.*;
import ua.com.juja.serzh.sqlcmd.BeforeTestsChangeNameAndPass;
import ua.com.juja.serzh.sqlcmd.Main;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.PostgresManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by serzh on 5/11/16.
 */
public class IntegrationTest {

    private static final String DATABASE = BeforeTestsChangeNameAndPass.DATABASE;
    private static final String USER = BeforeTestsChangeNameAndPass.USER;
    private static final String PASSWORD = BeforeTestsChangeNameAndPass.PASSWORD;

    private final String commandConnect = "connect|" + DATABASE + "|" + USER + "|" + PASSWORD;
    private final String pleaseConnect = "Введите имя базы данных, с которой будем работать, имя пользователя и пароль в формате: " +
            "connect|database|userName|password\n";

    private static DatabaseManager manager;
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @BeforeClass
    public static void buildDatabase() {
        manager = new PostgresManager();
        try {
            manager.connect("", USER, PASSWORD);
        } catch (RuntimeException e) {
            throw new RuntimeException("Для работы тестов измените имя и пароль в классе BeforeTestsChangeNameAndPass."
                    + "\n" + e.getCause());
        }
        try {
            manager.dropDatabase(DATABASE);
        } catch (Exception e) {
            // do nothing
        }
        manager.createDatabase(DATABASE);
        manager.connect(DATABASE, USER, PASSWORD);
        createTablesWithData();
        manager.connect("", USER, PASSWORD);
    }

    private static void createTablesWithData() {
        manager.createTable("users" +
                " (name VARCHAR (50) UNIQUE NOT NULL, password VARCHAR (50) NOT NULL, id SERIAL PRIMARY KEY)");
        manager.createTable("test1 (id SERIAL PRIMARY KEY)");
        Map<String, Object> dataSet = new LinkedHashMap<>();
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

    @AfterClass
    public static void dropDatabase() {
        try {
            manager.connect("", USER, PASSWORD);
//            manager.dropDatabase(DATABASE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                "\tcreateDB|databaseName\n" +
                "\t\tдля создания новой Database. Имя базы должно начинаться с буквы.\n" +
                "\tdropDB|databaseName\n" +
                "\t\tдля удаления Database. База должна быть свободна от любого конекшина.\n" +
                "\tcreateTable|tableName(column1,column2,...,columnN)\n" +
                "\t\tдля создания новой таблицы, в круглых скобках вставить опиание колонок в SQL формате, пример:\n" +
                "\t\tcreateTable|user(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))\n" +
                "\tclear|tableName\n" +
                "\t\tдля очистки всей таблицы\n" +
                "\tdropTable|tableName\n" +
                "\t\tдля удаления таблицы\n" +
                "\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN\n" +
                "\t\tдля создания записи в существующей таблице\n" +
                "\ttables\n" +
                "\t\tдля получения списка всех таблиц базы, к которой подключились\n" +
                "\tfind|tableName\n" +
                "\t\tдля получения содержимого таблицы 'tableName'\n" +
                "\thelp\n" +
                "\t\tдля вывода этого списка на экран\n" +
                "\texit\n" +
                "\t\tдля выхода из программы\n" +
                "Введи команду (или help для помощи):\n" +
                "До скорой встречи!\n", getData());
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
                "До скорой встречи!\n", getData());
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
                "Вы не можете пользоваться командой 'tables' пока не подключитесь с помощью комманды connect|databaseName|userName|password\n" +
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
        in.add(commandConnect);
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
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // tables
                "[users, test1]\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
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
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // tables
                "[users, test1]\n" +
                "Введи команду (или help для помощи):\n" +
                // connect test
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // tables
                "[qwe]\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
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
                "Неудача! по причине: Формат команды 'connect|databaseName|userName|password', а ты ввел: connect|sqlcmd5hope5never5exist\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
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
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // clear|users
                "Таблица users была успешно очищена.\n" +
                "Введи команду (или help для помощи):\n" +
                // insert|users|id|13|name|Stiven|password|*****
                "В таблице 'users' была успешно добавлена запись:\n" +
                "+--+------+--------+\n" +
                "|id|name  |password|\n" +
                "+--+------+--------+\n" +
                "|13|Stiven|*****   |\n" +
                "+--+------+--------+\n" +
                "Введи команду (или help для помощи):\n" +
                // insert|users|id|14|name|Eva|password|+++++
                "В таблице 'users' была успешно добавлена запись:\n" +
                "+--+----+--------+\n" +
                "|id|name|password|\n" +
                "+--+----+--------+\n" +
                "|14|Eva |+++++   |\n" +
                "+--+----+--------+\n" +
                "Введи команду (или help для помощи):\n" +
                // find|users
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
        in.add(commandConnect);
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
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
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
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // insert|user|error
                "Неудача! по причине: Должно быть четное количество параметров в формате 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', а ты прислал: 'insert|user|error'\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Ignore // тест занимает много времени, половина от всех вместе взятых...
    @Test
    public void testCreateDropDatabase() {
        // given
        in.add(commandConnect);
        in.add("createDB|sqlcmd9hope9never9exist");
        in.add("dropDB|sqlcmd9hope9never9exist");
        in.add("y");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then

        assertEquals(pleaseConnect +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                "Database sqlcmd9hope9never9exist была успешно создана.\n" +
                "Введи команду (или help для помощи):\n" +
                "Вы уверены, что хотите удалить sqlcmd9hope9never9exist? Y/N\n" +
                "Database 'sqlcmd9hope9never9exist' была успешно удалена.\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

}
