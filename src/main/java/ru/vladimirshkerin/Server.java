package ru.vladimirshkerin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The class contains the main logic of the server.
 *
 * @author Vladimir Shkerin
 * @since 24.06.2016
 */
public class Server {

    private List<Task> taskList;        // list all task
    private List<Thread> processList;   // list all process
    private boolean execute;            // flag execute server

    private Server() {
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

    private void start() {
        setExecute(true);
        execute();
    }

    private void stop() {
        setExecute(false);
        stopAllProcess();
    }

    private void execute() {
        while (isExecute()) {
            if (getTaskList().size() > 0) {
                Calendar cal = Calendar.getInstance();
                for (Task task : getTaskList()) {
                    if (checkRunningProcess(task, cal)) {
                        runProcess(task);
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                //TODO to replace the log
                ex.printStackTrace();
            }
        }
    }

    /**
     * Checked date in all Task for run process.
     *
     * @param task
     * @return
     */
    private boolean checkRunningProcess(final Task task, final Calendar calendar) {
        Schedule schedule = task.getSchedule();
        return schedule.isExecute(calendar);
    }

    /**
     * Run process.
     *
     * @param task
     */
    private void runProcess(final Task task) {
        Process proc = new Process("Thread_" + (processList.size() + 1), task);
        Thread thread = new Thread(proc);
        thread.start();

        processList.add(thread);

        //TODO to replace the log
        System.out.println("Thread \"" + proc.getName() + "\" start.");
    }

    /**
     * Stop all alive process.
     */
    private void stopAllProcess() {
        for (Thread thread : getThreadList()) {
            if (thread.isAlive()) {
                thread.interrupt();
                //TODO to replace the log
                System.out.println("Thread \"" + thread.getName() + "\" interrupt.");
            }
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public List<Thread> getThreadList() {
        return processList;
    }

    public synchronized boolean isExecute() {
        return execute;
    }

    public synchronized void setExecute(boolean execute) {
        this.execute = execute;
    }
}

















