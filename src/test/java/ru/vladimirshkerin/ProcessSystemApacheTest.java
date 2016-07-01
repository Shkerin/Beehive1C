package ru.vladimirshkerin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.vladimirshkerin.model.Command;
import ru.vladimirshkerin.model.ProcessSystemApache;
import ru.vladimirshkerin.model.Schedule;
import ru.vladimirshkerin.model.Task;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * The class for testing class ProcessSystemApache.
 *
 * @author Vladimir Shkerin
 * @since 30.06.2016
 */
public class ProcessSystemApacheTest {

    private final String path1C = "/opt/1cv8/8.3.8.1784/1cv8";
    private final String[] parameters1C = new String[]{
            "ENTERPRISE", "/S", "virt:1641\\mag", "/N", "server", "/P", "server"};
    private ProcessHandler process1C;

    private final String pathLS = "ls";
    private final String[] parametersLS = new String[]{"-l"};
    private ProcessHandler processLS;

    @Before
    public void setUp() throws Exception {
        Calendar calendar = new GregorianCalendar();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Schedule schedule = new Schedule(-1, month, day, -1, -1);

        Command command1C = new Command(path1C, parameters1C);
        Task task1C = new Task("Test task", command1C, schedule);
        process1C = new ProcessSystemApache("Process_1", task1C);

        Command commandLS = new Command(pathLS, parametersLS);
        Task taskLS = new Task("Test task", commandLS, schedule);
        processLS = new ProcessSystemApache("Process_1", taskLS);
    }

    @After
    public void tearDown() throws Exception {
        if (processLS.getStatus() != ProcessStatus.STOPPED) {
            processLS.stop();
        }
    }

    @Test
    public void start() throws Exception {
        processLS.start();

        while (processLS.getStatus() != ProcessStatus.STOPPED) {
            Thread.sleep(100);
        }

        assertEquals(0, processLS.getExitValue());
    }

    @Test
    public void stop() throws Exception {
        processLS.start();
        processLS.stop();
        Thread.sleep(100);
        assertEquals(ProcessStatus.STOPPED, processLS.getStatus());
    }

}