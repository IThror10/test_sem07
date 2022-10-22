package TermPedia.dialogs;

import TermPedia.dto.ActionsException;
import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.User;
import TermPedia.handlers.EventHandler;

import javax.swing.*;

public class AuthorizeDialog extends BaseMessages {
    public User execute() {
        JTextField login = new JTextField();
        JPasswordField password = new JPasswordField();

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Enter Login"),
                login,
                new JLabel("Enter Password"),
                password
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Authorization", JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION)
            return null;

        if (login.getText().length() == 0 || password.getPassword().length == 0) {
            showErrorMsg("Fill all fields");
            return null;
        }

        try {
            EventHandler handler = new EventHandler();
            AuthorizeEvent event = new AuthorizeEvent(
                    login.getText(),
                    new String(password.getPassword())
            );

            handler.handle(event);
            User user = event.getResult();
            successMsg("Well come, " + user.login);
            return user;
        } catch (ActionsException e) {
            showErrorMsg(e.getMessage());
            return null;
        }
    }
}
