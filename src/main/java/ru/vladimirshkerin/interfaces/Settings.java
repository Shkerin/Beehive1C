package ru.vladimirshkerin.interfaces;

import ru.vladimirshkerin.exceptions.NotFoundSettingException;

import java.io.IOException;

/**
 * The interface for program options.
 *
 * @author Vladimir Shkerin
 * @since 06.07.2016
 */
public interface Settings {

    String FILE_NAME_SETTINGS = "settings.cfg";

    /**
     * Writes the settings into the output stream.
     *
     * @throws IOException if an error occurs the parameter record to the output stream
     */
    void storeSettings() throws IOException;

    /**
     * Returns the parameter string of the application.
     *
     * @param key key to search for
     * @return string with the found parameter
     * @throws NotFoundSettingException if the parameter is not found for the key
     */
    String getString(String key) throws NotFoundSettingException;

    /**
     * Sets the application setting.
     *
     * @param key   string variable for the key
     * @param value the value of the parameter
     */
    void setSetting(String key, String value);
}
