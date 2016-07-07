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
    private static ResourceBundle resourceStr;

    static {
        String language = System.getProperty("user.language");
        String country = System.getProperty("user.country");

        currentLocale = new Locale(language, country);
        currentPath = System.getProperty("user.dir");
        currentFont = new Font("Arial", Font.PLAIN, 12);//UIManager.getFont("List.font")
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
        if ("Application.version".equals(key)) {
            return ResourceBundle.getBundle("buildNumber").getString(key);
        } else {
            return resourceStr.getString(key);
        }
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
}
