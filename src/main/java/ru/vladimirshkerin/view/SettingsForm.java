package ru.vladimirshkerin.view;

import org.apache.log4j.Logger;
import ru.vladimirshkerin.Launcher;
import ru.vladimirshkerin.enums.SettingsEnum;
import ru.vladimirshkerin.exceptions.NotFoundSettingException;
import ru.vladimirshkerin.interfaces.Settings;
import ru.vladimirshkerin.models.Resource;
import ru.vladimirshkerin.models.SettingsFile;
import ru.vladimirshkerin.models.SettingsReg;
import ru.vladimirshkerin.utils.BoxLayoutUtils;
import ru.vladimirshkerin.utils.GUITools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * The class display of the settings window.
 *
 * @author Vladimir Shkerin
 * @since 05.07.2016
 */
public class SettingsForm extends JFrame {

    private static final int DEFAULT_WIDTH_WINDOW = 300;
    private static final int DEFAULT_HEIGHT_WINDOW = 200;

    private static Logger log = Logger.getLogger(Launcher.class);

    private static Settings settingsFile = SettingsFile.getInstance();
    private static Settings settingsReg = SettingsReg.getInstance();

    private JLabel cronLabel;
    private JTextField cronField;
    private JButton cronBtn;
    private JButton saveBtn;
    private JButton exitBtn;

    private boolean restartServer = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SettingsForm form = new SettingsForm();
                form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                form.setTitle("Beehive_Test");
                form.setVisible(true);
            }
        });
    }

    public SettingsForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.error("Error installing user interface.", e);
        }

        setIconImage(new ImageIcon("bee.ico").getImage());
        setLocale(Resource.getCurrentLocale());
        setLocationWindow();
        setSize(DEFAULT_WIDTH_WINDOW, DEFAULT_HEIGHT_WINDOW);
        setResizable(false);

        add(createGUI());
        pack();
    }

    private JPanel createGUI() {
        // Create components
        cronLabel = new JLabel("Crontab file:", SwingConstants.RIGHT);
        cronField = new JTextField(20);
        try {
            cronField.setText(settingsFile.getSetting(SettingsEnum.SCHEDULER_CRONTAB_FILE.getValue()));
        } catch (NotFoundSettingException e) {
            log.error("Error reading setting.", e);
        }
        cronBtn = new JButton("Browse...");
        cronBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int ret = fileChooser.showDialog(null, "Open cron file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    cronField.setText(file.getAbsolutePath());
                    restartServer = true;
                }
            }
        });

        saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        });

        exitBtn = new JButton("Exit");
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsReg.setSetting("left.settings.window", String.valueOf(getX()));
                settingsReg.setSetting("top.settings.window", String.valueOf(getY()));
                dispose();
            }
        });

        // Place components on panel
        int gap = 5;
        JPanel pMain = BoxLayoutUtils.createVerticalPanel();
        pMain.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));

        JPanel pCron = BoxLayoutUtils.createHorizontalPanel();
        pCron.add(cronLabel);
        pCron.add(BoxLayoutUtils.createHorizontalStrut(gap));
        pCron.add(cronField);
        pCron.add(cronBtn);

        JPanel pFlow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JPanel pGrid = new JPanel(new GridLayout(1, 2, gap, 0));

        pGrid.add(saveBtn);
        pGrid.add(exitBtn);
        pFlow.add(pGrid);

        BoxLayoutUtils.setGroupAlignmentX(Component.LEFT_ALIGNMENT, pMain, pCron, pFlow);
        BoxLayoutUtils.setGroupAlignmentY(Component.CENTER_ALIGNMENT, cronLabel, cronField, cronBtn);

//        GUITools.makeSameSize(cronLabel, langLabel);
        GUITools.fixTxtFieldSize(cronField);

        pMain.add(pCron);
        pMain.add(BoxLayoutUtils.createVerticalStrut(gap));
        pMain.add(pFlow);

        return pMain;
    }

    private void setLocationWindow() {
        int left;
        int top;
        try {
            left = Integer.parseInt(settingsReg.getSetting("left.settings.window"));
            top = Integer.parseInt(settingsReg.getSetting("top.settings.window"));
        } catch (NotFoundSettingException e) {
            Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
            left = (int) ((dimScreen.getWidth() - getWidth()) / 2);
            top = (int) ((dimScreen.getHeight() - getHeight()) / 2);
        }

        setLocation(left, top);
    }

    private void saveSettings() {
        boolean isSave = false;
        if (!cronField.getText().isEmpty()) {
            settingsFile.setSetting(SettingsEnum.SCHEDULER_CRONTAB_FILE.getValue(), cronField.getText());
            isSave = true;
        }

        if (isSave) {
            try {
                settingsFile.storeSettings();
            } catch (IOException e) {
                log.error("Error saving settings to file.", e);
            }
            if (restartServer) {
                JOptionPane.showMessageDialog(null,
                        "To apply the changes restart the server.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private JPanel createGUIButtonPane() {
        JPanel panel = new JPanel();

        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : infos) {
            panel.add(makeButton(info.getName(), info.getClassName()));
        }

        return panel;
    }

    private JButton makeButton(String name, final String className) {
        JButton btn = new JButton(name);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    UIManager.setLookAndFeel(className);
                    SwingUtilities.updateComponentTreeUI(SettingsForm.this);
                    pack();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return btn;
    }

}
