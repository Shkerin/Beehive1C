package ru.vladimirshkerin;

import org.apache.log4j.Logger;
import ru.vladimirshkerin.model.ProcessSystemApache;
import ru.vladimirshkerin.model.Schedule;
import ru.vladimirshkerin.model.Task;

import java.util.Calendar;

/**
 * The class contains the model of the scheduler.
 *
 * @author Vladimir Shkerin
 * @since 28.06.2016
 */
public class SchedulerDefault implements Runnable {

    private static final Logger log = Logger.getLogger(SchedulerDefault.class);

    private Server server;
    private boolean execute;

    public SchedulerDefault(Server server) {
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
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isExecute()) {
                    if (server.getTaskList().size() > 0) {
                        Calendar cal = Calendar.getInstance();
                        for (Task task : server.getTaskList()) {
                            ProcessHandler proc = createProcess(task);
                            if (checkStatusProcess(cal, proc)) {
                                runProcess(proc);
                            }
                        }
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        log.warn("SchedulerDefault interrupted.");
                    }
                }
            }
        });
        th.start();
    }

    private ProcessHandler createProcess(Task task) {
        for (ProcessHandler p : server.getProcessList()) {
            if (p.getTask().equals(task)) {
                return p;
            }
        }
        return new ProcessSystemApache(task);
    }

    private boolean checkStatusProcess(Calendar cal, ProcessHandler proc) {
        if (proc.getStatus() != ProcessStatus.RUN) {
            return false;
        }

        Schedule schedule = proc.getTask().getSchedule();
        return schedule.isExecute(cal);
    }

    private void runProcess(final ProcessHandler proc) {
        proc.start();
        server.addProcess(proc);

        log.debug("SchedulerDefault: process \"" + proc.getName() + "\" start.");
    }
}










