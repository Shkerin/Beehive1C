package ru.vladimirshkerin;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

/**
 * The class server startup in console.
 *
 * @author Vladimir Shkerin
 * @since 02.07.2016
 */
public class LauncherServerConsole {

    private static final Logger log = Logger.getLogger(LauncherServerConsole.class);

    public static void main(String[] args) {
        if (args.length == 0 || (args.length > 1 && args.length != 3)) {
            System.out.println("usage: beehive [-f file_name] [start | stop | restart]");
            return;
        }

        Server server = Server.getInstance();

        int index = 0;
        if (args.length == 3) {
            if (args[0].equals("-f")) {
                server.loadCronFile(args[1]);
                index = 2;
            }
        }

        try {
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
        } catch (SchedulerException e) {
            log.error("Scheduler error.", e);
        }
    }

}









