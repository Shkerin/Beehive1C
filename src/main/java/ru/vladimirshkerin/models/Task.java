package ru.vladimirshkerin.models;

import ru.vladimirshkerin.enums.CronFiled;

import java.util.HashMap;
import java.util.Map;

/**
 * The class contains data to run the cron job.
 *
 * @author Vladimir Shkerin
 * @since 02.07.2016
 */
public class Task {

    private String name;
    private String group;
    private Map<CronFiled, String> expressionMap;
    private Command command;

    public Task(String name, String group, Map<CronFiled, String> expressionMap, Command command) {
        this.name = name;
        this.group = group;
        this.expressionMap = expressionMap;
        this.command = command;
    }

    public Task() {
        this("", "", new HashMap<CronFiled, String>(), new Command());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<CronFiled, String> getExpressionMap() {
        return expressionMap;
    }

    public String getExpressionString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<CronFiled, String> map : expressionMap.entrySet()) {
            sb.append(map.getValue()).append(" ");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public void setExpressionMap(Map<CronFiled, String> expressionMap) {
        this.expressionMap = expressionMap;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name).append(" ");
        sb.append(group).append(" ");
        sb.append(getExpressionString()).append(" ");
        sb.append(command.getCommandLine());

        for (String s : command.getArguments()) {
            sb.append(" ").append(s);
        }
        return sb.toString();
    }

}




















