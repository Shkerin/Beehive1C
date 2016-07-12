package ru.vladimirshkerin;

import org.apache.log4j.Logger;
import ru.vladimirshkerin.view.SystemTrayView;

import javax.swing.*;

/**
 * The class starting the server and creating the tray icon.
 *
 * @author Vladimir Shkerin
 * @since 04.07.2016
 */
public class Launcher {

    private static Logger log = Logger.getLogger(Server.class);

    private static Server server = Server.getInstance();

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.showIconTray();
        if (args.length > 0) {
            launcher.parseArgs(args);
        } else {
            server.start();
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

}
