package ru.vladimirshkerin;

import org.junit.Before;
import org.junit.Test;
import ru.vladimirshkerin.models.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The class for testing class Command.
 *
 * @author Vladimir Shkerin
 * @since 25.06.2016
 */
public class CommandTest {

    private final String path = "/opt/1cv8/8.3.8.1784/1cv8";
    private final String[] arguments = new String[]{
            "ENTERPRISE", "/S", "virt:1641\\mag", "/N", "server", "/P", "server"};

    private Command command;

    @Before
    public void setUp() throws Exception {
        command = new Command(path, Arrays.asList(arguments));
    }

    @Test
    public void constructorsTest() throws Exception {
        Command cmd = new Command();
        cmd = new Command(path);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(path, command.getCommandLine());
    }

    @Test
    public void setPath() throws Exception {
        command.setCommandLine(path);
        assertEquals(path, command.getCommandLine());
    }

    @Test
    public void getArguments() throws Exception {
        List<String> expected = Arrays.asList(arguments);
        List<String> actual = command.getArguments();
        assertEquals(expected, actual);
    }

    @Test
    public void setArgumentsAsList() throws Exception {
        List<String> expected = Arrays.asList(arguments);
        command.setArguments(expected);
        List<String> actual = command.getArguments();
        assertEquals(expected, actual);
    }

    @Test
    public void setArgumentsAsArrays() throws Exception {
        String[] expected = arguments;
        command.setArguments(expected);
        String[] actual = (String[]) command.getArguments().toArray();
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void toList() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(path);
        list.addAll(1, Arrays.asList(arguments));
        assertEquals(list, command.toList());
    }

    @Test
    public void toStringTest() throws Exception {
        StringBuilder sb = new StringBuilder(path);
        for (String s : arguments) {
            sb.append(" ").append(s);
        }
        String expected = sb.toString();
        String actual = command.toString();
        assertEquals(expected, actual);
    }
}
