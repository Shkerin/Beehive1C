package ru.vladimirshkerin.model;

import org.apache.log4j.Logger;
import ru.vladimirshkerin.ProcessHandler;
import ru.vladimirshkerin.ProcessStatus;

import java.io.IOException;

/**
 * The class creates a process in the operating system.
 * Uses the standard Java class ProcessBuilder.
 *
 * @author Vladimir Shkerin
 * @since 26.06.2016
 */
public class ProcessSystemDefault implements ProcessHandler {

    private static final Logger log = Logger.getLogger(ProcessSystemDefault.class);

    private String name;
    private Task task;
    private int exitValue;
    private ProcessStatus status;
    private Thread thread;

    public ProcessSystemDefault(String name, Task task) {
        this.name = name;
        this.task = task;
        this.exitValue = 0;
        this.status = ProcessStatus.RUN;
        this.thread = new Thread();
    }

    public ProcessSystemDefault(Task task) {
        this("", task);
    }

    @Override
    public void start() {
        log.debug("ProcessSystemDefault started: " + name);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ProcessBuilder pb = new ProcessBuilder(task.getCommand().toList());
                try {
                    status = ProcessStatus.RUNNING;
                    Process process = pb.start();
                    process.waitFor();
                } catch (InterruptedException e) {
                    log.warn("Process interrupted.");
                    exitValue = 1;
                } catch (ArrayIndexOutOfBoundsException | SecurityException | IOException e) {
                    log.error("Process error: " + name, e);
                    exitValue = 1;
                } finally {
                    status = ProcessStatus.STOPPED;
                }
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
        log.debug("ProcessSystemDefault stopped: " + name);
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
    public synchronized ProcessStatus getStatus() {
        return status;
    }

    @Override
    public int getExitValue() {
        return exitValue;
    }
}
