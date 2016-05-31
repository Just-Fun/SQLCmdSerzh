package ua.com.juja.serzh.sqlcmd.integration;

import org.junit.*;
import ua.com.juja.serzh.sqlcmd.BeforeTestsChangeNameAndPass;
import ua.com.juja.serzh.sqlcmd.Main;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.PostgresManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by serzh on 5/11/16.
 */
@Ignore
public class IntegrationTest2 {

    private static final String DATABASE = BeforeTestsChangeNameAndPass.DATABASE;
    private static final String USER = BeforeTestsChangeNameAndPass.USER;
    private static final String PASSWORD = BeforeTestsChangeNameAndPass.PASSWORD;
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    private final String commandConnect = "connect|" + DATABASE + "|" + USER + "|" + PASSWORD;
    private final String pleaseConnect = "Введите имя базы данных, с которой будем работать, имя пользователя и пароль в формате: " +
            "connect|database|userName|password\n";

    private static DatabaseManager manager;

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

        manager.createTable("users" +
                " (name VARCHAR (50) UNIQUE NOT NULL, password VARCHAR (50) NOT NULL, id SERIAL PRIMARY KEY)");
        manager.createTable("test1 (id SERIAL PRIMARY KEY)");
    }

    @Before
    public void setup() {
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }
/*@AfterClass
    public static void dropDatabase() {
        try {
            manager.connect("", USER, PASSWORD);
            manager.dropDatabase(DATABASE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
*/


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
}
