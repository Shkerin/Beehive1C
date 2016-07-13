package ru.vladimirshkerin;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.vladimirshkerin.enums.CronFiled;
import ru.vladimirshkerin.enums.SettingsEnum;
import ru.vladimirshkerin.exceptions.NotFoundSettingException;
import ru.vladimirshkerin.exceptions.ParsingFileException;
import ru.vladimirshkerin.interfaces.Settings;
import ru.vladimirshkerin.models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * The main class of the controller server.
 *
 * @author Vladimir Shkerin
 * @since 24.06.2016
 */
public class Server {

    private static Logger log = Logger.getLogger(Server.class);

    private static Settings settings = SettingsFile.getInstance();

    public static Server Instance;

    private Scheduler scheduler;
    private boolean execute;
    private List<Task> taskList;
    private List<JobDetail> jobList;

    public static Server getInstance() {
        if (Instance == null) {
            Instance = new Server();
        }
        return Instance;
    }

    private Server() {
        try {
            this.scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException sex) {
            log.error("Error initialization scheduler!", sex);
            System.exit(1);
        }
        this.execute = false;
        this.taskList = new ArrayList<>();
        this.jobList = new ArrayList<>();
    }

    public void start() {
        log.info("---------- Start server Beehive ----------");

        String str;
        try {
            str = settings.getSetting(SettingsEnum.SCHEDULER_CRONTAB_FILE.getValue());
        } catch (NotFoundSettingException e) {
            str = Resource.getCurrentPath() +
                    System.getProperty("file.separator") +
                    Resource.getDefaultCrontabFileName();
        }

        Path path = Paths.get(str);
        if (!Files.exists(path)) {
            createCrontabFile(path);
        }

        loadCronFile(str);

        try {
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            log.error("Error scheduler.", e);
            return;
        }

        setExecute(true);
        execute();
    }

    public void stop() {
        setExecute(false);

        try {
            for (JobDetail job : jobList) {
                scheduler.interrupt(job.getKey());
            }

            scheduler.clear();

            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            log.error("Error scheduler.", e);
            return;
        }

        log.info("---------- Stop server Beehive ----------");
    }

    public void restart() {
        stop();

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException sex) {
            log.error("Error initialization scheduler!", sex);
            System.exit(1);
        }

        start();
    }

    public void execute() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isExecute()) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException ex) {
                        log.warn("Server interrupted.");
                    }
                }
            }
        });
        th.start();
    }

    public void pauseScheduler() {
        try {
            if (scheduler.isStarted()) {
                scheduler.pauseAll();
            }
        } catch (SchedulerException e) {
            log.error("Error scheduler.", e);
            return;
        }
    }

    public void resumeScheduler() {
        try {
            if (scheduler.isStarted()) {
                scheduler.resumeAll();
            }
        } catch (SchedulerException e) {
            log.error("Error scheduler.", e);
            return;
        }
    }

    private void createCrontabFile(Path path) {
        try (InputStream is = getClass().getResourceAsStream("/crontab_default.txt")) {

            Files.copy(is, path); //StandardCopyOption.REPLACE_EXISTING

            log.info("Create crontab file \"crontab.txt\" in the folder \"" + path.toAbsolutePath() + "\"");
        } catch (IOException e) {
            log.error("Error create crontab file", e);
        }
    }

    public void loadCronFile(String fineName) {
        taskList.clear();

        int numLine = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(fineName))) {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    parseLine(line);
                    numLine++;
                }
            } catch (ParsingFileException e) {
                String msg = String.format("Error parsing file \"%s\" in line %d: \"%s\"",
                        fineName, numLine, e.getMessage());
                log.error(msg);
            }

            addTaskToScheduler(taskList);

            log.trace("Load crontab file \"crontab.txt\" in the folder \"" + fineName + "\"");
        } catch (IOException e) {
            log.error("Error load file \"" + fineName + "\"", e);
        }
    }

    private void parseLine(String line) throws ParsingFileException {
        String[] result = line.trim().split("\\s+");

        if (result.length == 0 || result[0].isEmpty() || result[0].equals("#")) {
            return;
        } else if (result.length < 7) {
            throw new ParsingFileException(line);
        }

        // build cron expression
        Map<CronFiled, String> mapExp = new LinkedHashMap<>();
        mapExp.put(CronFiled.SECONDS, result[0]);
        mapExp.put(CronFiled.MINUTES, result[1]);
        mapExp.put(CronFiled.HOURS, result[2]);
        mapExp.put(CronFiled.DAY_OF_MONTH, result[3]);
        mapExp.put(CronFiled.MONTH, result[4]);
        mapExp.put(CronFiled.DAY_OF_WEEK, result[5]);

        // build an executable command
        String cmd = result[6];

        // build command arguments
        List<String> argList = new ArrayList<>();
        for (int i = 7; i < result.length; i++) {
            argList.add(result[i]);
        }

        Command command = new Command(cmd, argList);

        Task task = new Task("Task_" + (taskList.size() + 1), "Group", mapExp, command);
        taskList.add(task);

        log.trace("Task add: " + task.toString());
    }

    public void addTaskToScheduler(List<Task> taskList) {
        int num = 1;
        for (Task task : taskList) {
            JobDetail job = makeJob(task);
            Trigger trigger = makeTrigger(task, num);
            try {
                scheduler.scheduleJob(job, trigger);
            } catch (SchedulerException e) {
                log.error("Error add job to scheduler.");
            }
            jobList.add(job);
            num++;
        }
    }

    private JobDetail makeJob(Task task) {
        Map<String, Task> map = new HashMap<>();
        map.put("task", task);
        JobDataMap jobDataMap = new JobDataMap(map);

        return newJob(ProcessSystemIml.class)
                .withIdentity(task.getName(), task.getGroup())
                .setJobData(jobDataMap)
                .build();
    }

    private Trigger makeTrigger(Task task, int num) {
        return newTrigger()
                .withIdentity("Trigger_" + num, task.getGroup())
                .withSchedule(cronSchedule(task.getExpressionString()))
                .build();
    }

    private boolean isExecute() {
        return execute;
    }

    private void setExecute(boolean execute) {
        this.execute = execute;
    }

}
