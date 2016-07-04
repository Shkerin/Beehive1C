package ru.vladimirshkerin.interfaces;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobDetailImpl;
import ru.vladimirshkerin.enums.ProcessStatus;
import ru.vladimirshkerin.models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * The class will be the parent of all system processes
 *
 * @author Vladimir Shkerin
 * @since 03.07.2016
 */
public abstract class ProcessSystem implements Job {

    protected Task task;
    protected List<String> files;
    protected ProcessStatus status;
    protected int exitValue;
    protected long timeOut;
    protected JobDetail jobDetail;

    public ProcessSystem() {
        this.task = new Task();
        this.files = new ArrayList<>();
        this.status = ProcessStatus.RUN;
        this.exitValue = 0;
        this.timeOut = 0;
        this.jobDetail = new JobDetailImpl();
    }

    @Override
    public abstract void execute(JobExecutionContext context) throws JobExecutionException;

    public String getName() {
        if (jobDetail.getKey() != null && jobDetail.getKey().getName() != null) {
            return jobDetail.getKey().getName();
        }
        return "_unnamed_";
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public int getExitValue() {
        return exitValue;
    }

    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
}
