package ru.vladimirshkerin;

import org.apache.log4j.Logger;
import ru.vladimirshkerin.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class of the controller server.
 *
 * @author Vladimir Shkerin
 * @since 24.06.2016
 */
public class Server {

    private static final Logger log = Logger.getLogger(Server.class);

    private Scheduler scheduler;
    private List<Task> taskList;
    private List<ProcessHandler> processList;
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
            return;
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
        log.info("---------- Start server Beehive ----------");
        setExecute(true);
        scheduler.setExecute(true);
        scheduler.execute();
        execute();
    }

    public void stop() {
        setExecute(false);
        scheduler.setExecute(false);
        stopAllProcess();
        log.info("---------- Stop server Beehive ----------");
    }

    public void execute() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    log.warn("Server interrupted.");
                }
            }
        });
        th.start();
    }

    private void stopAllProcess() {
        for (ProcessHandler proc : getProcessList()) {
            if (proc.getStatus() != ProcessStatus.STOPPED) {
                proc.stop();
            }
        }
        log.debug("Server: stopped all processes.");
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void addProcess(ProcessHandler proc) {
        processList.add(proc);
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public List<ProcessHandler> getProcessList() {
        return processList;
    }

    public synchronized boolean isExecute() {
        return execute;
    }

    private synchronized void setExecute(boolean execute) {
        this.execute = execute;
    }
}

















