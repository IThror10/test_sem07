package TermPedia.dialogs;

import javax.swing.*;

public class LogoutDialog {
    public boolean execute(String userName) {
        if (userName == null) {
            JOptionPane.showInternalMessageDialog(
                    null,
                    new JComponent[]{new JLabel("You are not authorized!")},
                    "",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return false;
        }

        Object[] options = {"Yes", "No"};
        int choice = JOptionPane.showOptionDialog(null,
                "Log out?",
                "Confirm",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (choice == 1)
            return false;
        else {
            JOptionPane.showInternalMessageDialog(
                    null,
                    new JComponent[]{new JLabel(userName + ", come back :)")},
                    "",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        }
    }
}
