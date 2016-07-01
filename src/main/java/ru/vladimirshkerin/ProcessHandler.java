package ru.vladimirshkerin;

import ru.vladimirshkerin.model.Task;

/**
 * The interface to describe the process in the operating system
 *
 * @author Vladimir Shkerin
 * @since 30.06.2016
 */
public interface ProcessHandler {

    void start();

    void stop();

    String getName();

    Task getTask();

    ProcessStatus getStatus();

    int getExitValue();
}
