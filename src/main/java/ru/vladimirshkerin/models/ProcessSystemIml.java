package ru.vladimirshkerin.models;

import org.apache.log4j.Logger;
import org.quartz.*;
import ru.vladimirshkerin.enums.ProcessStatus;
import ru.vladimirshkerin.interfaces.ProcessSystem;

import java.io.IOException;

/**
 * The class creates a process in the operating system.
 * Uses the standard Java class ProcessBuilder.
 *
 * @author Vladimir Shkerin
 * @since 26.06.2016
 */
public class ProcessSystemIml extends ProcessSystem implements InterruptableJob {

    private static final String DATA_KEY = "task";

    private static Logger log = Logger.getLogger(ProcessSystemIml.class);

    private Process process = null;

    public ProcessSystemIml() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        status = ProcessStatus.RUNNING;

        jobDetail = context.getJobDetail();

        JobDataMap data = context.getMergedJobDataMap();
        task = (Task) data.get(DATA_KEY);

        //------------------------------
        Command cmd = task.getCommand();
        ProcessBuilder pb = new ProcessBuilder(cmd.toList());
        try {
            log.trace("ProcessSystemIml start executed: " + getName());

            try {
                process = pb.start();
                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    log.warn("ProcessSystemApache " + getName() + " interrupted");
                }

                exitValue = process.exitValue();
            } catch (IOException e) {
                log.error("ProcessSystemIml error: " + getName(), e);
            }

            log.trace("ProcessSystemIml stop executed: " + getName());
            //------------------------------
        } finally {
            status = ProcessStatus.STOPPED;
        }

        log.trace("ProcessSystemIml \'" + getName() + "\' exit value: " + getExitValue());
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        log.warn("ProcessSystemApache " + getName() + " interrupted");
        if (process != null) {
            log.trace("ProcessSystemIml destroy: " + getName());
            process.destroy();
        }
    }
}
