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

    static {
        Instance = new SettingsFile();
    }

    /**
     * Returns an instance of the class single.
     *
     * @return an instance of the class.
     */
    public static SettingsFile getInstance() {
        return Instance;
    }

    /**
     * Constructor of the pattern alone.
     */
    private SettingsFile() {
        String userDir = System.getProperty("user.dir");
        propertiesFile = new File(userDir, FILE_NAME_SETTINGS);
        properties = new Properties(getDefaultSettings());
        if (propertiesFile.exists()) {
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
    private Properties getDefaultSettings() {
        Properties props = new Properties();

        props.put("crontab.file", Resource.getCurrentPath() + System.getProperty("file.separator") + "crontab.txt");
        props.put("language", Resource.getCurrentLocale());

        return props;
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
    public String getSetting(String key) throws NotFoundSettingException {
        String str = properties.getProperty(key);
        if (str == null) {
            throw new NotFoundSettingException("settings \"" + key + "\" not found");
        }
        return str;
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
