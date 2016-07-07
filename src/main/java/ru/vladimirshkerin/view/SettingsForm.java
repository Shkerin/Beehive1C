package ru.vladimirshkerin.view;

import org.apache.log4j.Logger;
import ru.vladimirshkerin.Launcher;

import javax.swing.*;

/**
 * TODO: comment
 *
 * @author vlad
 * @since 05.07.2016
 */
public class SettingsForm extends JFrame {

    private static Logger log = Logger.getLogger(Launcher.class);

    public SettingsForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("ERROR");
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        add(createGUI());
    }

    private JPanel createGUI() {
        JPanel panel = new JPanel();
        JButton button = new JButton("click me");
        panel.add(button);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SettingsForm form = new SettingsForm();
                form.setTitle("Beehive");
                form.setVisible(true);
            }
        });
    }
}
