package ru.vladimirshkerin;

/**
 * The class contains the command parameters to run.
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
}
