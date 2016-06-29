package ru.vladimirshkerin;

import java.io.IOException;

/**
 * The class creates a process in the operating system.
 *
 * @author Vladimir Shkerin
 * @since 26.06.2016
 */
public class ProcessSystem {

    private String name;
    private Task task;
    private Process process;
    private boolean execute;

    public ProcessSystem(String name, Task task) {
        this.name = name;
        this.task = task;
        this.process = null;
        this.execute = false;
    }

    public ProcessSystem(Task task) {
        this("", task);
    }

    public void start() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                ProcessBuilder pb = new ProcessBuilder(task.getCommand().toList());
                try {
                    process = pb.start();
                    execute = true;
                    try {
                        process.waitFor();
                    } catch (InterruptedException e) {
                        //TODO to replace the log
                        System.out.println("Process interrupted..");
                    }
                } catch (ArrayIndexOutOfBoundsException | SecurityException | IOException e) {
                    //TODO to replace the log
                    System.out.println("Error to run process system.");
                } finally {
                    process = null;
                    execute = false;
                }
            }
        });
        th.start();
    }

    public void stop() {
        if (process != null) {
            process.destroy();
            process = null;
        }
    }

    public String getName() {
        return name;
    }

    public Task getTask() {
        return task;
    }

    public boolean isExecute() {
        return execute;
    }
}
