package ru.vladimirshkerin.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class contains the command arguments to run process.
 *
 * @author Vladimir Shkerin
 * @since 24.06.2016
 */
public class Command {

    private String commandLine;
    private List<String> arguments;

    public Command(String commandLine, List<String> arguments) {
        this.commandLine = commandLine;
        this.arguments = arguments;
    }

    public Command(String commandLine) {
        this(commandLine, new ArrayList<String>());
    }

    public Command() {
        this("", new ArrayList<String>());
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = Arrays.asList(arguments);
    }

    public List<String> toList() {
        List<String> list = new ArrayList<>();
        list.add(commandLine);
        list.addAll(1, arguments);
        return list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(commandLine);
        for (String s : arguments) {
            sb.append(" ").append(s);
        }
        return sb.toString();
    }

}
