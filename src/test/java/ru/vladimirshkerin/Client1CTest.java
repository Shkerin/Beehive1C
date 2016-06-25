package ru.vladimirshkerin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.java2d.cmm.kcms.CMM;

import static org.junit.Assert.*;

/**
 * The class for testing class Client1C.
 *
 * @author Vladimir Shkerin
 * @since 25.06.2016
 */
public class Client1CTest {

    private final String path = "/tmp";
    private final String[] parameters = new String[] {"ENTERPRISE", "/N", "admin"};

    private final String name = "Client1C";
    private final Command command = new Command(path, parameters);
    private final Schedule schedule = new Schedule(1, 6, 25, 9, 0);

    private Client1C client1C;

    @Before
    public void setUp() throws Exception {
        client1C = new Client1C(name, command, schedule);
    }

    @After
    public void tearDown() throws Exception {
        client1C = null;
    }

    @Test
    public void getName() throws Exception {
        final String expected = name;
        final String actual = client1C.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void setName() throws Exception {
        final String expected = name;
        client1C.setName(expected);
        final String actual = client1C.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void getCommand() throws Exception {
        final Command expected = command;
        final Command actual = client1C.getCommand();
        assertEquals(expected, actual);
    }

    @Test
    public void setCommand() throws Exception {
        final Command expected = command;
        client1C.setCommand(expected);
        final Command actual = client1C.getCommand();
        assertEquals(expected, actual);
    }

    @Test
    public void getSchedule() throws Exception {
        final Schedule expected = schedule;
        final Schedule actual = client1C.getSchedule();
        assertEquals(expected, actual);
    }

    @Test
    public void setSchedule() throws Exception {
        final Schedule expected = schedule;
        client1C.setSchedule(schedule);
        final Schedule actual = client1C.getSchedule();
        assertEquals(expected, actual);
    }

}