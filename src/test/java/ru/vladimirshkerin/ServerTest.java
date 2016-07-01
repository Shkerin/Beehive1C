package ru.vladimirshkerin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.vladimirshkerin.model.Command;
import ru.vladimirshkerin.model.Schedule;
import ru.vladimirshkerin.model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The class for testing class Server.
 *
 * @author Vladimir Shkerin
 * @since 27.06.2016
 */
public class ServerTest {

    private final String path = "/opt/1cv8/8.3.8.1784/1cv8";
    private final String[] parameters = new String[]{
            "ENTERPRISE", "/S", "virt:1641\\mag", "/N", "server", "/P", "server"};

    private Server server;

    @Before
    public void setUp() throws Exception {
        server = new Server();

        Calendar calendar = new GregorianCalendar();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Command command = new Command(path, parameters);
        Schedule schedule = new Schedule(-1, month, day, -1, -1);
        for (int i = 0; i < 2; i++) {
            Task task = new Task("Task_" + i, command, schedule);
            server.addTask(task);
        }
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
        server = null;
    }

    @Test
    public void start() throws Exception {
        server.start();

        Long stopTime = new Date().getTime() + 10L * 1000;
        Long currentTime;
        do {
            currentTime = new Date().getTime();
        } while (currentTime < stopTime);
    }

    @Test
    public void stop() throws Exception {
        server.stop();
    }
}
