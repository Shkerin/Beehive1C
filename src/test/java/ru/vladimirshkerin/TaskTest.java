package ru.vladimirshkerin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The class for testing class Task.
 *
 * @author Vladimir Shkerin
 * @since 25.06.2016
 */
public class TaskTest {

    private final String path = "/tmp";
    private final String[] parameters = new String[]{"ENTERPRISE", "/N", "admin"};

    private final String name = "Task";
    private final Command command = new Command(path, parameters);
    private final Schedule schedule = new Schedule(1, 6, 25, 9, 0);

    private Task task;

    @Before
    public void setUp() throws Exception {
        task = new Task(name, command, schedule);
    }

    @After
    public void tearDown() throws Exception {
        task = null;
    }

    @Test
    public void getName() throws Exception {
        final String expected = name;
        final String actual = task.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void setName() throws Exception {
        final String expected = name;
        task.setName(expected);
        final String actual = task.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void getCommand() throws Exception {
        final Command expected = command;
        final Command actual = task.getCommand();
        assertEquals(expected, actual);
    }

    @Test
    public void setCommand() throws Exception {
        final Command expected = command;
        task.setCommand(expected);
        final Command actual = task.getCommand();
        assertEquals(expected, actual);
    }

    @Test
    public void getSchedule() throws Exception {
        final Schedule expected = schedule;
        final Schedule actual = task.getSchedule();
        assertEquals(expected, actual);
    }

    @Test
    public void setSchedule() throws Exception {
        final Schedule expected = schedule;
        task.setSchedule(schedule);
        final Schedule actual = task.getSchedule();
        assertEquals(expected, actual);
    }

}