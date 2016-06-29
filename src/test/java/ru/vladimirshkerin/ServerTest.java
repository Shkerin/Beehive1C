package ru.vladimirshkerin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        server = Server.getInstance();

        Command command = new Command(path, parameters);
        Schedule schedule = new Schedule(-1, -1, 29, -1, -1);
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
    }

    @Test
    public void stop() throws Exception {
        server.stop();
    }
}
