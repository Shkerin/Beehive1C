package ru.vladimirshkerin.model;

import java.util.Calendar;

/**
 * The class contains maintenance schedule of the process.
 *
 * @author Vladimir Shkerin
 * @since 24.06.2016
 */
public class Schedule {

    private short dayOfWeek;    // 0 - 7    (sunday =0 или =7)
    private short month;        // 1 - 12
    private short day;          // 1 - 31
    private short hour;         // 0 - 23
    private short min;          // 0 - 59

    public Schedule(short dayOfWeek, short month, short day, short hour, short min) {
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
    }

    public Schedule(int dayOfWeek, int month, int day, int hour, int min) {
        this((short) dayOfWeek, (short) month, (short) day, (short) hour, (short) min);
    }

    public Schedule() {
        this((short) -1, (short) -1, (short) -1, (short) -1, (short) -1);
    }

    public boolean isExecute(Calendar calendar) {
        if (min == -1 && hour == -1 && day == -1 && month == -1 && dayOfWeek == -1) {
            return false;
        }
        if (min > -1) {
            if (min != calendar.get(Calendar.MINUTE)) {
                return false;
            }
        }
        if (hour > -1) {
            if (hour != calendar.get(Calendar.HOUR_OF_DAY)) {
                return false;
            }
        }
        if (day > -1) {
            if (day != calendar.get(Calendar.DAY_OF_MONTH)) {
                return false;
            }
        }
        if (month > -1) {
            if (month != (calendar.get(Calendar.MONTH) + 1)) {
                return false;
            }
        }
        if (dayOfWeek > -1) {
            if (dayOfWeek != (calendar.get(Calendar.DAY_OF_WEEK) - 1)) {
                return false;
            }
        }
        return true;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = (short) dayOfWeek;
    }

    public short getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = (short) month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = (short) day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = (short) hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = (short) min;
    }

    public void setDayOfWeek(String str) {
        this.dayOfWeek = parseStr(str, 0, 7);
    }

    public void setMonth(String str) {
        this.month = parseStr(str, 1, 12);
    }

    public void setDay(String str) {
        this.day = parseStr(str, 1, 31);
    }

    public void setHour(String str) {
        this.hour = parseStr(str, 0, 23);
    }

    public void setMin(String str) {
        this.min = parseStr(str, 0, 59);
    }

    private short parseStr(String str, int startInterval, int stopInterval) {
        if (str.length() > 0) {
            short dt = Short.valueOf(str.trim());
            if (dt >= startInterval && dt <= stopInterval) {
                return dt;
            }
        }
        return -1;
    }
}











