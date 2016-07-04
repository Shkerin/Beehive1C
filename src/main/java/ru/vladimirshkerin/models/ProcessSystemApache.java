package ru.vladimirshkerin.models;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.log4j.Logger;
import org.quartz.*;
import ru.vladimirshkerin.enums.ProcessStatus;
import ru.vladimirshkerin.interfaces.ProcessSystem;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The class creates a process in the operating system.
 * Use the library org.apache.commons.exec
 *
 * @author Vladimir Shkerin
 * @since 30.06.2016
 */
public class ProcessSystemApache extends ProcessSystem implements InterruptableJob {

    private static final String DATA_KEY = "task";

    private static Logger log = Logger.getLogger(ProcessSystemApache.class);

    private boolean interrupted = false;

    public ProcessSystemApache() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        status = ProcessStatus.RUNNING;

        jobDetail = context.getJobDetail();

        JobDataMap data = context.getMergedJobDataMap();
        task = (Task) data.get(DATA_KEY);

        //------------------------------
        try {
            log.trace("ProcessSystemApache executed: " + getName());

            final DefaultExecutor executor = makeDefaultExecutor();
            final CommandLine cmdLine = makeCommandLine();
            final DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        executor.execute(cmdLine, resultHandler);

                        resultHandler.waitFor();
                        exitValue = resultHandler.getExitValue();
                    } catch (InterruptedException e) {
                        log.warn("ProcessSystemApache " + getName() + " interrupted_1");
                    } catch (IOException e) {
                        log.error("ProcessSystemApache error: " + getName(), e);
                    } finally {
                        interrupted = true;
                    }
                }
            }

            );
            th.start();

            while (!interrupted) {
                try {
                    Thread.sleep(500L);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }

            if (th.isAlive()) {
                th.interrupt();
            }

            log.trace("ProcessSystemApache " + getName() + " completed");

            //------------------------------
        } finally {
            status = ProcessStatus.STOPPED;
            log.trace("ProcessSystemApache " + getName() + " exit value " + getExitValue());
        }
    }

    private DefaultExecutor makeDefaultExecutor() {
        final DefaultExecutor executor = new DefaultExecutor();
        if (timeOut > 0) {
            ExecuteWatchdog watchdog = new ExecuteWatchdog(timeOut);
            executor.setWatchdog(watchdog);
            log.trace("ProcessSystemApache \'" + getName() + "\' set time out: " + timeOut);
        }
        return executor;
    }

    private CommandLine makeCommandLine() {
        Command cmd = task.getCommand();
        final CommandLine cmdLine = new CommandLine(cmd.getCommandLine());
        for (String arg : cmd.getArguments()) {
            cmdLine.addArgument(arg);
        }

        if (!files.isEmpty()) {
            cmdLine.addArgument("${file}");
            Map map = new HashMap();
            for (String fileStr : files) {
                map.put("file", new File(fileStr));
            }
            cmdLine.setSubstitutionMap(map);
        }
        return cmdLine;
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        log.warn("ProcessSystemApache " + getName() + " interrupted_2");
        interrupted = true;
    }
}
