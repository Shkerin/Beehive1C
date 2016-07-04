package ru.vladimirshkerin.exceptions;

/**
 * The exception class to parse a string.
 *
 * @author Vladimir Shkerin
 * @since 02.07.2016
 */
public class ParsingFileException extends Exception {

    public ParsingFileException() {
        super();
    }

    public ParsingFileException(String message) {
        super(message);
    }

    public ParsingFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingFileException(Throwable cause) {
        super(cause);
    }
}
