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

    private final String fileName = "crontab_default.txt";
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

    @Ignore
    @Test(timeout = 11L * 1000L)
    public void start() throws Exception {
        server.start();

        try {
            Thread.sleep(10L * 1000L);
        } catch (Exception e) {
            //empty
        }
    }

    @Ignore
    @Test
    public void parseLine() throws Exception {
        String str = " \"C:\\Program Files (x86)\\1cv82\\8.2.19.83\\bin\\1cv8mag.exe\" " +
                " ENTERPRISE " +
                " /S\"sp-sql9/perant_mag\" " +
                " /Nserver " +
                " /Pserver " +
                " /C\"OBMEN#ЦентральныйОбмен#%2\" " +
                " /RunModeOrdinaryApplication " +
                " /DisableStartupMessages ";

        String[] result = str.trim().split("\".*\"");
        System.out.println(result);
    }
}
