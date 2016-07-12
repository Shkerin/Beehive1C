package ru.vladimirshkerin.utils;

import javax.swing.*;
import java.awt.*;

/**
 * A set of tools for the final sanding and polishing interface
 * <p/>
 * Code taken from the book:
 * Title: Swing эффектные пользовательские интерфейсы (Издание 2-е)
 * Author: Иван Портянкин
 * Publisher: Лори
 */
public class GUITools {

    // Giving to a group of components with the same sizes
    // (minimum, preferred and maximum).
    public static void makeSameSize(JComponent... cs) {
        Dimension maxSize = cs[0].getPreferredSize();
        for (JComponent c : cs) {
            if (c.getPreferredSize().width > maxSize.width) {
                maxSize = c.getPreferredSize();
            }
        }

        for (JComponent c : cs) {
            c.setPreferredSize(maxSize);
            c.setMinimumSize(maxSize);
            c.setMaximumSize(maxSize);
        }
    }

    // Allows you to correct the mistake in size of a text field JTextField
    public static void fixTxtFieldSize(JTextField field) {
        Dimension size = field.getPreferredSize();
        /* so that the text field still could
           to increase your size in length */
        size.width = field.getMaximumSize().width;
        /* now text ate will not be higher
           his optimal height */
        field.setMaximumSize(size);
    }
}
