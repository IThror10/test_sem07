package TermPedia.dialogs;

import TermPedia.dto.ActionsException;
import TermPedia.events.user.RegisterEvent;
import TermPedia.handlers.EventHandler;

import javax.swing.*;

public class RegisterDialog {
    public void execute() {
        JTextField login = new JTextField();
        JTextField email = new JTextField();
        JPasswordField password1 = new JPasswordField();
        JPasswordField password2 = new JPasswordField();

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Enter Login (At least 5 chars)"),
                login,
                new JLabel("Enter Email"),
                email,
                new JLabel("Enter password (At least 5 chars)"),
                password1,
                new JLabel("Confirm password"),
                password2
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Registration", JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION)
            return;

        if (login.getText().length() == 0 || email.getText().length() == 0 ||
            password1.getPassword().length == 0 || password2.getPassword().length == 0) {
            JOptionPane.showInternalMessageDialog(
                    null,
                    new JComponent[]{new JLabel("Fill all fields")},
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (password1.getPassword() == password2.getPassword()) {
            JOptionPane.showInternalMessageDialog(
                    null,
                    new JComponent[]{new JLabel("Passwords don't match")},
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }


        try {
            EventHandler handler = new EventHandler();
            RegisterEvent event = new RegisterEvent(
                    login.getText(),
                    new String(password1.getPassword()),
                    email.getText()
            );

            handler.handle(event);
            if (event.getResult().getStatus())
                JOptionPane.showInternalMessageDialog(
                        null,
                        new JComponent[]{new JLabel("Successfully authorized")},
                        "",
                        JOptionPane.INFORMATION_MESSAGE
                );
        } catch (ActionsException e) {
            JOptionPane.showInternalMessageDialog(
                    null,
                    new JComponent[]{new JLabel(e.getMessage())},
                    "",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    }
}
