package ru.vladimirshkerin.models;

import org.apache.log4j.Logger;
import ru.vladimirshkerin.exceptions.NotFoundSettingException;
import ru.vladimirshkerin.interfaces.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The class to save and restore program settings from a file.
 *
 * @author Vladimir Shkerin
 * @since 06.07.2016
 */
public class SettingsFile implements Settings {

    private static Logger log = Logger.getLogger(SettingsFile.class);

    private static SettingsFile Instance;

    private static Properties properties;
    private static File propertiesFile;

    /**
     * Returns an instance of the class single.
     *
     * @return an instance of the class.
     */
    public static SettingsFile getInstance() {
        if (Instance == null) {
            Instance = new SettingsFile();
        }
        return Instance;
    }

    /**
     * Constructor of the pattern alone.
     */
    private SettingsFile() {
        String userDir = System.getProperty("user.dir");
        propertiesFile = new File(userDir, FILE_NAME_SETTINGS);
        if (!propertiesFile.exists()) {
            properties = new Properties(getDefaultSettings());
        } else {
            try (FileInputStream in = new FileInputStream(propertiesFile)) {
                properties.load(in);
            } catch (IOException e) {
                log.error("Error loading settings file", e);
            }
        }
    }

    /**
     * Creates and returns the settings of the default application.
     *
     * @return a variable of type SettingsFile with the default settings
     */
    private static Properties getDefaultSettings() {
        Properties properties = new Properties();

        properties.put("crontab.file", Resource.getCurrentPath() + "/crontab.txt");
        properties.put("language", Resource.getCurrentLocale());

        return properties;
    }

    /**
     * Writes the settings into the output stream.
     *
     * @throws IOException if an error occurs the parameter record to the output stream
     */
    @Override
    public void storeSettings() throws IOException {
        FileOutputStream out = new FileOutputStream(propertiesFile);
        properties.store(out, "Program settings");
    }

    /**
     * Returns the parameter string of the application.
     *
     * @param key the key to search for
     * @return a string with the found parameter
     * @throws NotFoundSettingException if the parameter is not found for the key
     */
    @Override
    public String getString(String key) throws NotFoundSettingException {
        String property = properties.getProperty(key);
        if (property == null) {
            throw new NotFoundSettingException("settings \"" + key + "\" not found");
        }
        return property;
    }

    /**
     * Sets the application setting.
     *
     * @param key   string variable for the key
     * @param value the value of the parameter
     */
    @Override
    public void setSetting(String key, String value) {
        properties.setProperty(key, value);
    }

}
