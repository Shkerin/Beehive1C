package ru.vladimirshkerin.models;

import ru.vladimirshkerin.exceptions.NotFoundSettingException;
import ru.vladimirshkerin.interfaces.Settings;

import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * The class to save and restore the auxiliary program settings from the registry.
 *
 * @author Vladimir Shkerin
 * @since 06.07.2016
 */
public class SettingsReg implements Settings {

    private static SettingsReg Instance;

    private static Preferences node;

    static {
        Instance = new SettingsReg();
    }

    /**
     * Returns an instance of the class single.
     *
     * @return an instance of the class.
     */
    public static SettingsReg getInstance() {
        return Instance;
    }

    /**
     * Constructor of the pattern alone.
     */
    private SettingsReg() {
        Preferences root = Preferences.userRoot();
        node = root.node(Resource.getCurrentPackage());
        if ("".equals(node.get("width.position.settings.window", ""))) {
            setDefaultSettings();
        }
    }

    /**
     * Sets the program settings by default.
     */
    private void setDefaultSettings() {
        node.put("width.position.settings.window", "30");
        node.put("height.position.settings.window", "30");
    }

    @Override
    public void storeSettings() throws IOException {
        //empty
    }

    /**
     * Returns the parameter string of the program.
     *
     * @param key the key to search for
     * @return a string with the found parameter
     * @throws NotFoundSettingException if the parameter is not found for the key
     */
    @Override
    public String getSetting(String key) throws NotFoundSettingException {
        String value = node.get(key, "");
        if ("".equals(value))
            throw new NotFoundSettingException("settings \"" + key + "\" not found");
        return value;
    }

    /**
     * Sets the parameter of the program.
     *
     * @param key   string variable for the key
     * @param value the value of the parameter
     */
    @Override
    public void setSetting(String key, String value) {
        node.put(key, value);
    }

}
