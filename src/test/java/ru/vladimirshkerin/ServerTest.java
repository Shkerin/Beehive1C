package ru.vladimirshkerin;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The class for testing class Server.
 *
 * @author Vladimir Shkerin
 * @since 27.06.2016
 */
public class ServerTest {

    private final String fileName = "crontab.txt";
    private final String path = "/opt/1cv8/8.3.8.1784/1cv8";
    private final String[] parameters = new String[]{
            "ENTERPRISE", "/S", "virt:1641\\mag", "/N", "server", "/P", "server"};

    private Server server;

    @Before
    public void setUp() throws Exception {
        server = Server.getInstance();
        server.loadCronFile(fileName);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test(timeout = 11L * 1000L)
    public void start() throws Exception {
        server.start();

        try {
            Thread.sleep(10L * 1000L);
        } catch (Exception e) {
            //empty
        }
    }

}

