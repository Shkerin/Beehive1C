package ru.vladimirshkerin.exceptions;

/**
 * The exception class to settings program.
 *
 * @author Vladimir Shkerin
 * @since 07.07.2016
 */
public class NotFoundSettingException extends Exception {

    public NotFoundSettingException(String message) {
        super(message);
    }

    public NotFoundSettingException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundSettingException(Throwable cause) {
        super(cause);
    }
}
