package TermPedia.dialogs;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Term;
import TermPedia.handlers.QueryHandler;
import TermPedia.queries.instances.terms.FindTermByNameQuery;

import javax.swing.*;
import java.util.Vector;

public class FindTermDialog extends BaseMessages {
    public Term execute() {
        JComponent[] inputs;
        JTextField termName;
        int result;

        termName = new JTextField(30);
        inputs = new JComponent[]{
                new JLabel("Enter Term Name:"),
                termName
        };

        result = JOptionPane.showConfirmDialog(
                null,
                inputs,
                "New Term Name",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result != JOptionPane.OK_OPTION)
            return null;


        FindTermByNameQuery query = new FindTermByNameQuery(termName.getText(), 30, 0);
        try {
            QueryHandler handler = new QueryHandler();
            handler.handle(query);
        } catch (ActionsException e) {
            showErrorMsg(e.getMessage());
            return null;
        }

        Vector<Term> terms = query.getResult().getTerms();
        JComboBox<Term> comboBox = new JComboBox<>(terms);
        comboBox.setMaximumRowCount(15);
        comboBox.setSelectedItem(terms.get(0));
        inputs = new JComponent[]{
                new JLabel("Search Result:"),
                comboBox
        };

        result = JOptionPane.showConfirmDialog(
                null,
                inputs,
                "Choose Term",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result != JOptionPane.OK_OPTION)
            return null;

        if (comboBox.getSelectedItem() != null)
            return terms.get(comboBox.getSelectedIndex());
        return null;
    }
}