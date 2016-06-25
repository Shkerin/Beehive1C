package ru.vladimirshkerin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * The class for testing class Command.
 *
 * @author Vladimir Shkerin
 * @since 25.06.2016
 */
public class CommandTest {

    private final String path = "/tmp";
    private final String[] parameters = new String[] {"ENTERPRISE", "/N", "admin"};

    private Command command;

    @Before
    public void setUp() throws Exception {
        command = new Command(path, parameters);
    }

    @After
    public void tearDown() throws Exception {
        command = null;
    }

    @Test
    public void getPath() throws Exception {
        final String expected = path;
        final String actual = command.getPath();
        assertEquals(expected, actual);
    }

    @Test
    public void setPath() throws Exception {
        final String expected = path;
        command.setPath(expected);
        final String actual = command.getPath();
        assertEquals(expected, actual);
    }

    @Test
    public void getParameters() throws Exception {
        final String[] expected = parameters;
        final String[] actual = command.getParameters();
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void setParameters() throws Exception {
        final String[] expected = parameters;
        command.setParameters(expected);
        final String[] actual = command.getParameters();
        assertTrue(Arrays.equals(expected, actual));
    }
}