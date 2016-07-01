package ru.vladimirshkerin;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.vladimirshkerin.model.Command;
import ru.vladimirshkerin.model.ProcessSystemDefault;
import ru.vladimirshkerin.model.Schedule;
import ru.vladimirshkerin.model.Task;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The class for testing class ProcessSystemDefault.
 *
 * @author Vladimir Shkerin
 * @since 30.06.2016
 */
public class ProcessSystemDefaultTest {

    private final String path = "/opt/1cv8/8.3.8.1784/1cv8";
    private final String[] parameters = new String[]{
            "ENTERPRISE", "/S", "virt:1641\\mag", "/N", "server", "/P", "server"};

    private ProcessHandler process;

    @Before
    public void setUp() throws Exception {
        Calendar calendar = new GregorianCalendar();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Command command = new Command(path, parameters);
        Schedule schedule = new Schedule(-1, month, day, -1, -1);
        Task task = new Task("TaskTest", command, schedule);

        process = new ProcessSystemDefault("ProcessTest", task);
    }

    @After
    public void tearDown() throws Exception {
        if (process.getStatus() != ProcessStatus.STOPPED) {
            process.stop();
        }
    }

    @Ignore
    @Test
    public void start() throws Exception {

        process.start();
        while (process.getStatus() != ProcessStatus.STOPPED) {
            Thread.sleep(100);
        }
    }

    @Ignore
    @Test
    public void stop() throws Exception {
        process.stop();
    }

}