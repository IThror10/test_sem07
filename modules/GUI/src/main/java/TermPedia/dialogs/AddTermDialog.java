package TermPedia.dialogs;

import TermPedia.dto.ActionsException;
import TermPedia.events.data.AddTermEvent;
import TermPedia.events.user.User;
import TermPedia.handlers.EventHandler;

import javax.swing.*;

public class AddTermDialog extends BaseMessages {
    public void execute(User user) {
        JTextField termName = new JTextField(50);
        JTextArea description = new JTextArea(4, 50);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Term Name:"),
                termName,
                new JLabel("Description:"),
                description
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "New Term Name", JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION)
            return;

        if (termName.getText().length() == 0 || description.getText().length() == 0) {
            showErrorMsg("Fill all fields");
            return;
        }

        try {
            EventHandler handler = new EventHandler();
            AddTermEvent event = new AddTermEvent(
                    termName.getText(),
                    description.getText(),
                    user == null ? null : user.UID
            );

            handler.handle(event);
            if (event.getResult().getStatus())
                successMsg("Term will be added soon");
        } catch (ActionsException e) {
            showErrorMsg(e.getMessage());
        }
    }
}
