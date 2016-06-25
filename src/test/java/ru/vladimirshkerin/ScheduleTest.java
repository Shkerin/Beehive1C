package ru.vladimirshkerin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * The class for testing class ScheduleTest.
 *
 * @author Vladimir Shkerin
 * @since 25.06.2016
 */
public class ScheduleTest {

    private final short dayOfWeek = 6;  // 0 - 7    (sunday =0 или =7)
    private final short month = 6;      // 1 - 12
    private final short day = 25;       // 1 - 31
    private final short hour = 8;       // 0 - 23
    private final short min = 25;       // 0 - 59

    private final Calendar calendar = new GregorianCalendar(2016, month - 1, day, hour, min, 0);

    private Schedule schedule;

    @Before
    public void setUp() throws Exception {
        schedule = new Schedule(dayOfWeek, month, day, hour, min);
    }

    @After
    public void tearDown() throws Exception {
        schedule = null;
    }

    @Test
    public void isExecute() throws Exception {
        schedule = new Schedule();
        assertFalse(schedule.isExecute(calendar));

        schedule = new Schedule((short) -1, (short) -1, (short) -1, (short) -1, (short) -1);
        assertFalse(schedule.isExecute(calendar));

        short[] ts = new short[]{dayOfWeek, month, day, hour, min};
        for (int i = 0; i < 5; i++) {
            ts[i] = (short) (ts[i] + 1);
            schedule = new Schedule(ts[0], ts[1], ts[2], ts[3], ts[4]);
            assertFalse(schedule.isExecute(calendar));
        }

        schedule = new Schedule(dayOfWeek, month, day, hour, min);
        assertTrue(schedule.isExecute(calendar));

        schedule = new Schedule((int) dayOfWeek, (int) month, (int) day, (int) hour, (int) min);
        assertTrue(schedule.isExecute(calendar));
    }

    @Test
    public void getDayOfWeek() throws Exception {
        final int expected = dayOfWeek;
        final int actual = schedule.getDayOfWeek();
        assertEquals(expected, actual);
    }

    @Test
    public void setDayOfWeek() throws Exception {
        final int expected = dayOfWeek;
        schedule.setDayOfWeek(expected);
        final int actual = schedule.getDayOfWeek();
        assertEquals(expected, actual);
    }

    @Test
    public void getMonth() throws Exception {
        final int expected = month;
        final int actual = schedule.getMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void setMonth() throws Exception {
        final int expected = month;
        schedule.setMonth(expected);
        final int actual = schedule.getMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void getDay() throws Exception {
        final int expected = day;
        final int actual = schedule.getDay();
        assertEquals(expected, actual);
    }

    @Test
    public void setDay() throws Exception {
        final int expected = day;
        schedule.setDay(expected);
        final int actual = schedule.getDay();
        assertEquals(expected, actual);
    }

    @Test
    public void getHour() throws Exception {
        final int expected = hour;
        final int actual = schedule.getHour();
        assertEquals(expected, actual);
    }

    @Test
    public void setHour() throws Exception {
        final int expected = hour;
        schedule.setHour(expected);
        final int actual = schedule.getHour();
        assertEquals(expected, actual);
    }

    @Test
    public void getMin() throws Exception {
        final int expected = hour;
        final int actual = schedule.getHour();
        assertEquals(expected, actual);
    }

    @Test
    public void setMin() throws Exception {
        final int expected = min;
        schedule.setMin(expected);
        final int actual = schedule.getMin();
        assertEquals(expected, actual);
    }

    @Test
    public void setDayOfWeekStr() throws Exception {
        final int expected = dayOfWeek;
        schedule.setDayOfWeek(String.valueOf(expected));
        final int actual = schedule.getDayOfWeek();
        assertEquals(expected, actual);
    }

    @Test
    public void setMonthStr() throws Exception {
        final int expected = month;
        schedule.setMonth(String.valueOf(expected));
        final int actual = schedule.getMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void setDayStr() throws Exception {
        final int expected = day;
        schedule.setDay(String.valueOf(expected));
        final int actual = schedule.getDay();
        assertEquals(expected, actual);
    }

    @Test
    public void setHourStr() throws Exception {
        final int expected = hour;
        schedule.setHour(String.valueOf(expected));
        final int actual = schedule.getHour();
        assertEquals(expected, actual);
    }

    @Test
    public void setMinStr() throws Exception {
        final int expected = min;
        schedule.setMin(String.valueOf(expected));
        final int actual = schedule.getMin();
        assertEquals(expected, actual);
    }

    @Test
    public void parseStrTest() throws Exception {
        final int expected = -1;
        schedule.setMin("");
        final int actual = schedule.getMin();
        assertEquals(expected, actual);

    }

    private void calendarExample() {
        Calendar calendar = Calendar.getInstance();

        System.out.println("DATE: " + calendar.getTime());
        System.out.println("ERA: " + calendar.get(Calendar.ERA));
        System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
        System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
        System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
        System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
        System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
        System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
        System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
        System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("DAY_OF_WEEK_IN_MONTH: " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
        System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
        System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
    }
}