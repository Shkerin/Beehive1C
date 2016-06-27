package ru.vladimirshkerin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class contains the command parameters to run process.
 *
 * @author Vladimir Shkerin
 * @since 24.06.2016
 */
public class Command {

    private String path;
    private String[] parameters;

    public Command(String path, String[] parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public List<String> toList() {
        List<String> list = new ArrayList<>();
        list.add(path);
        list.addAll(1, Arrays.asList(parameters));
        return list;
    }
}
