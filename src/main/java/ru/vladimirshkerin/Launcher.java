package ru.vladimirshkerin;

import org.apache.log4j.Logger;
import ru.vladimirshkerin.exceptions.NotFoundSettingException;
import ru.vladimirshkerin.interfaces.Settings;
import ru.vladimirshkerin.models.Resource;
import ru.vladimirshkerin.models.SettingsFile;
import ru.vladimirshkerin.view.SystemTrayView;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * The class starting the server and creating the tray icon.
 *
 * @author Vladimir Shkerin
 * @since 04.07.2016
 */
public class Launcher {

    private static Logger log = Logger.getLogger(Server.class);

    private static Settings settings = SettingsFile.getInstance();
    private static Server server = Server.getInstance();

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.showIconTray();
        if (args.length > 0) {
            launcher.parseArgs(args);
        } else {
            launcher.startServer();
        }
    }

    private void showIconTray() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SystemTrayView();
            }
        });
    }

    private void parseArgs(String[] args) {
        if (args.length > 1 && args.length != 3) {
            System.out.println("usage: beehive [-f file_name] [start | stop | restart]");
            return;
        }

        int index = 0;
        if (args.length == 3) {
            if (args[0].equals("-f")) {
                server.loadCronFile(args[1]);
                index = 2;
            }
        }

        if (args[index].equals("start")) {
            server.start();
            System.out.println("Beehive started.");
        } else if (args[index].equals("stop")) {
            server.stop();
            System.out.println("Beehive stopped.");
        } else if (args[index].equals("restart")) {
            server.restart();
            System.out.println("Beehive restart.");
        } else {
            server.stop();
        }
    }

    private void startServer() {
        String str;
        try {
            str = settings.getString("crontab.file");
        } catch (NotFoundSettingException e) {
            str = Resource.getCurrentPath() + System.getProperty("line.separate") + "crontab.txt";
        }
        Path path = Paths.get(str);
        if (!Files.exists(path)) {
            createCrontabFile(path);
        }

        server.loadCronFile(str);
        server.start();
    }

    private void createCrontabFile(Path path) {
        try (InputStream is = getClass().getResourceAsStream("/crontab_default.txt")) {
            Files.createFile(path);
            Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Error create crontab file", e);
        }
    }

}
