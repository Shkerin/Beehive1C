package ru.vladimirshkerin;

import java.util.Calendar;

/**
 * The class contains the model of the scheduler.
 *
 * @author Vladimir Shkerin
 * @since 28.06.2016
 */
public class Scheduler implements Runnable {

    private Server server;
    private boolean execute;

    public Scheduler(Server server) {
        this.server = server;
        this.execute = false;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    @Override
    public void run() {
        execute();
    }

    public void execute() {
        while (isExecute()) {
            if (server.getTaskList().size() > 0) {
                Calendar cal = Calendar.getInstance();
                for (Task task : server.getTaskList()) {
                    ProcessSystem proc = createProcess(task);
                    if (checkExecuteProcess(cal, proc)) {
                        runProcess(proc);
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

    private ProcessSystem createProcess(Task task) {
        for (ProcessSystem p : server.getProcessList()) {
            if (p.getTask().equals(task)) {
                return p;
            }
        }
        return new ProcessSystem(task);
    }

    private boolean checkExecuteProcess(Calendar cal, ProcessSystem proc) {
        if (proc.isExecute()) {
            return false;
        }

        Schedule schedule = proc.getTask().getSchedule();
        return schedule.isExecute(cal);
    }

    private void runProcess(final ProcessSystem proc) {
        proc.start();
        server.addProcess(proc);

        //TODO to replace the log
        System.out.println("Thread \"" + proc.getName() + "\" start.");
    }
}










