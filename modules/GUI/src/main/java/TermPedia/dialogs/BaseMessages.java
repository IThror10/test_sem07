package TermPedia.dialogs;

import javax.swing.*;

public class BaseMessages {
    public static void showErrorMsg(String msg) {
        JOptionPane.showInternalMessageDialog(
                null,
                new JComponent[]{new JLabel(msg)},
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void successMsg(String msg) {
        JOptionPane.showInternalMessageDialog(
                null,
                new JComponent[]{new JLabel(msg)},
                "Success!",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void nothingFound() {
        JOptionPane.showInternalMessageDialog(
                null,
                new JComponent[]{new JLabel("Nothing Found")},
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
