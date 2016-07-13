package ru.vladimirshkerin.enums;

/**
 * Enumeration settings fields.
 *
 * @author Vladimir Shkerin
 * @since 12.07.2016
 */
public enum SettingsEnum {
    SCHEDULER_CRONTAB_FILE("scheduler.crontabFile"),
    SCHEDULER_THREAD_COUNT("scheduler.threadCount"),
    LANGUAGE("system.language");

    private String value;

    SettingsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
