package ru.vladimirshkerin;

/**
 * The class contains the data for running process.
 *
 * @author Vladimir Shkerin
 * @since 24.06.2016
 */
public class Task {

    private String name;
    private Command command;
    private Schedule schedule;

    public Task(String name, Command command, Schedule schedule) {
        this.name = name;
        this.command = command;
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
