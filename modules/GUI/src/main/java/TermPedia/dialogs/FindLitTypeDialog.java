package TermPedia.dialogs;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Tag;
import TermPedia.handlers.QueryHandler;
import TermPedia.queries.instances.tags.FindTagByNameQuery;
import TermPedia.queries.instances.types.FindLitTypesByNameQuery;

import javax.swing.*;
import java.util.Objects;
import java.util.Vector;

import static TermPedia.dialogs.BaseMessages.showErrorMsg;

public class FindLitTypeDialog {
    public String execute() {
        JComponent[] inputs;
        JTextField typeName;
        int result;

        typeName = new JTextField(30);
        inputs = new JComponent[]{
                new JLabel("Enter source type:"),
                typeName
        };

        result = JOptionPane.showConfirmDialog(
                null,
                inputs,
                "Source type",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result != JOptionPane.OK_OPTION)
            return null;

        String litType = typeName.getText();
        FindLitTypesByNameQuery query = new FindLitTypesByNameQuery(litType, 14, 0);
        try {
            QueryHandler handler = new QueryHandler();
            handler.handle(query);
        } catch (ActionsException e) {
            showErrorMsg(e.getMessage());
            return null;
        }

        Vector<String> types = query.getResult().getLitTypes();

        boolean found = false;
        for (String type : types)
            found |= Objects.equals(type, litType);
        if (!found)
            types.insertElementAt(litType, 0);

        JComboBox<String> comboBox = new JComboBox<>(types);
        comboBox.setMaximumRowCount(15);
        comboBox.setSelectedItem(types.get(0));
        inputs = new JComponent[]{
                new JLabel("Search Results:"),
                comboBox
        };

        result = JOptionPane.showConfirmDialog(
                null,
                inputs,
                "Choose Tag",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result != JOptionPane.OK_OPTION)
            return null;

        if (comboBox.getSelectedItem() != null)
            return types.get(comboBox.getSelectedIndex());
        return null;
    }
}
