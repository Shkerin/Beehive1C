package ru.vladimirshkerin.models;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class for resources program.
 *
 * @author Vladimir Shkerin
 * @since 04.07.2016
 */
public class Resource {

    private static Locale currentLocale;
    private static String currentPath;
    private static Font currentFont;
    private static String currentPackage;
    private static String defaultCrontabFileName;
    private static ResourceBundle resourceStr;

    static {
        String language = System.getProperty("user.language");
        String country = System.getProperty("user.country");

        currentLocale = new Locale(language, country);
        currentPath = System.getProperty("user.dir");
        currentFont = new Font("Arial", Font.PLAIN, 13);//UIManager.getFont("List.font")
        currentPackage = "ru/vladimirshkerin/beehive";
        defaultCrontabFileName = "crontab.txt";
        resourceStr = ResourceBundle.getBundle("strings", currentLocale);
    }

    /**
     * Empty constructor.
     */
    private Resource() {
        //TODO empty
    }

    /**
     * Returns the resource string.
     *
     * @param key the key for resource lookup
     * @return a resource string found for key
     */
    public static String getString(String key) {
        return resourceStr.getString(key);
    }

    /**
     * Returns the current area.
     *
     * @return a variable of type Local with the current terrain
     */
    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * Returns the current program path.
     *
     * @return a string with the current path of the program.
     */
    public static String getCurrentPath() {
        return currentPath;
    }

    /**
     * Returns the current font of the program.
     *
     * @return a variable of type Font with the current font.
     */
    public static Font getCurrentFont() {
        return currentFont;
    }

    /**
     * Returns the current package of the program.
     *
     * @return a variable of type string with the current package.
     */
    public static String getCurrentPackage() {
        return currentPackage;
    }

    /**
     * Returns the current default crontab file name.
     *
     * @return a variable of type string with the current default crontab file name.
     */
    public static String getDefaultCrontabFileName() {
        return defaultCrontabFileName;
    }

    /**
     * Returns the current build number of the program.
     *
     * @return a variable of type string with the current build number.
     */
    public static String getBuildNumber() {
        return ResourceBundle.getBundle("buildNumber").getString("Application.version");
    }
}
