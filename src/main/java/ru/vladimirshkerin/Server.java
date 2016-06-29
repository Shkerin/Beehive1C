package ru.vladimirshkerin;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class of the controller server.
 *
 * @author Vladimir Shkerin
 * @since 24.06.2016
 */
public class Server {

    private Scheduler scheduler;
    private List<Task> taskList;
    private List<ProcessSystem> processList;
    private boolean execute;

    public Server() {
        this.scheduler = new Scheduler(this);
        this.taskList = new ArrayList<>();
        this.processList = new ArrayList<>();
        this.execute = false;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("usage: beehive [start | stop | restart | status] ");
        }

        Server server = new Server();
        if (args[0].equals("start")) {
            server.start();
            System.out.println("Beehive started.");
        } else if (args[1].equals("stop")) {
            server.stop();
            System.out.println("Beehive stopped.");
        } else if (args[0].equals("restart")) {
            server.stop();
            System.out.println("Beehive stopped.");
            server.start();
            System.out.println("Beehive started.");
        } else if (args[0].equals("status")) {
            if (server.isExecute()) {
                System.out.println("Beehive running:");
                System.out.printf("\t(the number of running processes - %d)", server.processList.size());
            } else {
                System.out.println("Beehive stopped.");
            }
        }
    }

    public void start() {
        setExecute(true);
        scheduler.setExecute(true);
        scheduler.execute();
    }

    public void stop() {
        setExecute(false);
        scheduler.setExecute(false);
        stopAllProcess();
    }

    public void excecute() {
        while (isExecute()) {
            //empty
        }
    }

    /**
     * Stop all alive process.
     */
    private void stopAllProcess() {
        for (ProcessSystem proc : getProcessList()) {
            if (proc.isExecute()) {
                proc.stop();
                //TODO to replace the log
                System.out.println("Process \"" + proc.getName() + "\" stopped.");
            }
        }
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void addProcess(ProcessSystem proc) {
        processList.add(proc);
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public List<ProcessSystem> getProcessList() {
        return processList;
    }

    public synchronized boolean isExecute() {
        return execute;
    }

    private synchronized void setExecute(boolean execute) {
        this.execute = execute;
    }
}

















