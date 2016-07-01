package ru.vladimirshkerin.model;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.log4j.Logger;
import ru.vladimirshkerin.ProcessHandler;
import ru.vladimirshkerin.ProcessStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class creates a process in the operating system.
 * Use the library org.apache.commons.exec
 *
 * @author Vladimir Shkerin
 * @since 30.06.2016
 */
public class ProcessSystemApache implements ProcessHandler {

    private static final Logger log = Logger.getLogger(ProcessSystemApache.class);

    private String name;
    private Task task;
    private List<String> files;
    private ProcessStatus status;
    private Thread thread;
    private int exitValue;

    private DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
    private long timeOut = 0;

    public ProcessSystemApache(String name, Task task) {
        this.name = name;
        this.task = task;
        this.files = new ArrayList<>();
        this.thread = new Thread();
        this.status = ProcessStatus.RUN;
    }

    public ProcessSystemApache(Task task) {
        this(task.getName(), task);
    }

    @Override
    public void start() {
        log.trace("ProcessSystemApache started: " + name);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                status = ProcessStatus.RUNNING;

                CommandLine cmdLine = new CommandLine(task.getCommand().getCommandLine());
                for (String arg : task.getCommand().getArguments()) {
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

                DefaultExecutor executor = new DefaultExecutor();
                if (timeOut > 0) {
                    ExecuteWatchdog watchdog = new ExecuteWatchdog(timeOut);
                    executor.setWatchdog(watchdog);
                    log.trace("Process " + getName() + " set time out: " + timeOut);
                }

                try {
                    executor.execute(cmdLine, resultHandler);
                    resultHandler.waitFor();
                    exitValue = resultHandler.getExitValue();
                } catch (InterruptedException e) {
                    log.warn("Process interrupted: " + name);
                } catch (IOException e) {
                    log.error("Process error: " + name, e);
                } finally {
                    status = ProcessStatus.STOPPED;
                }
                log.trace("Process " + getName() + " exit value: " + getExitValue());
            }
        });
        thread.start();
    }

    @Override
    public void stop() {
        if (status != ProcessStatus.STOPPED) {
            status = ProcessStatus.STOPPED;
            thread.interrupt();
        }
        log.trace("ProcessSystemApache stopped: " + name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Task getTask() {
        return task;
    }

    @Override
    public ProcessStatus getStatus() {
        return status;
    }

    @Override
    public int getExitValue() {
        return exitValue;
    }
}
