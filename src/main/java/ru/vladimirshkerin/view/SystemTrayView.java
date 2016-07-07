package ru.vladimirshkerin.view;

import org.apache.log4j.Logger;
import ru.vladimirshkerin.Launcher;
import ru.vladimirshkerin.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

/**
 * The class show icons in the system tray of the operating system.
 *
 * @author Vladimir Shkerin
 * @since 05.07.2016
 */
public class SystemTrayView extends JFrame {

    private static Logger log = Logger.getLogger(Launcher.class);

    private static Server server = Server.getInstance();

    public SystemTrayView() {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        // Check the SystemTrayView support
        if (!java.awt.SystemTray.isSupported()) {
            log.error("SystemTray is not supported");
            return;
        }
        Image image = createImage("/images/bee.png", "tray icon");
        final TrayIcon trayIcon = new TrayIcon(image);
        final java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
        final PopupMenu popup = new PopupMenu();

        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Beehive server");

        // Create a popup menu components
        MenuItem configItem = new MenuItem("Configuration...");
        final CheckboxMenuItem pauseItem = new CheckboxMenuItem("Pause");
        MenuItem restartItem = new MenuItem("Restart");
        MenuItem aboutItem = new MenuItem("About...");
        MenuItem shutdownItem = new MenuItem("Shutdown server");

        // Add components to popup menu
        popup.add(configItem);
        popup.addSeparator();
        popup.add(pauseItem);
        popup.add(restartItem);
        popup.addSeparator();
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(shutdownItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            log.error("TrayIcon could not be added.", e);
            return;
        }

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from System Tray");
            }
        });

        configItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SettingsForm form = new SettingsForm();
                        form.setTitle("Beehive");
                        form.setVisible(true);
                    }
                });
            }
        });

        pauseItem.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int id = e.getStateChange();
                if (id == ItemEvent.SELECTED) {
                    server.pauseScheduler();
                } else {
                    server.resumeScheduler();
                }
            }
        });

        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.restart();
                pauseItem.setState(false);
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from the About menu item");
            }
        });


        shutdownItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                server.stop();
                System.exit(0);
            }
        });
    }

    // Obtain the image URL
    private static Image createImage(String path, String description) {
        URL imageURL = Launcher.class.getResource(path);

        if (imageURL == null) {
            log.error("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}
