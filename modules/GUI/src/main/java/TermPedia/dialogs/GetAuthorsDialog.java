package TermPedia.dialogs;

import TermPedia.dto.ActionsException;
import TermPedia.dto.RatedBook;
import TermPedia.dto.Tag;
import TermPedia.handlers.QueryHandler;
import TermPedia.queries.books.SearchBookByTagQuery;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Vector;

public class GetAuthorsDialog {
    public Vector<String> execute() {
        Vector<String> authors = new Vector<>();

        JList<String> authorsList;
        DefaultListModel<String> authorsModel;
        JButton addAuthor, deleteAuthor;
        JTextField authorName;

        authorName = new JTextField();
        authorsModel = new DefaultListModel();
        authorsModel.addElement("");
        authorsModel.addElement(" ");
        authorsModel.addElement("  ");
        authorsModel.addElement("   ");
        authorsModel.addElement("    ");
        authorsModel.addElement("     ");
        authorsList = new JList<>();

        addAuthor = new JButton("Add Author");
        addAuthor.addActionListener(e -> {
            String name = authorName.getText();

            if (name.length() > 3) {
                boolean found = false;
                for (int i = 0; i < authorsModel.size() && !found; i++)
                    found = Objects.equals(name, authorsModel.get(i));
                if (!found) {
                    authorsModel.removeElementAt(5);
                    authorsModel.add(0, name);
                }
            }
        });

        authorsList = new JList();
        authorsList.setModel(authorsModel);
        authorsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        authorsList.setLayoutOrientation(JList.VERTICAL);
        authorsList.setVisibleRowCount(4);
        authorsList.setFont(new Font("Arial",Font.BOLD,14));
        JScrollPane listScroller = new JScrollPane(authorsList);
        listScroller.setPreferredSize(new Dimension(250, 80));


        final JList<String> tmp = authorsList;
        deleteAuthor = new JButton("Remove Author");
        deleteAuthor.addActionListener(e -> {
            String author = tmp.getSelectedValue();
            if (author != null) {
                authorsModel.remove(tmp.getSelectedIndex());
            }
        });

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Author Name:"),
                authorName,
                addAuthor,
                deleteAuthor,
                authorsList
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Authors", JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION)
            return null;

        for (int i = 0 ; i < authorsList.getModel().getSize(); i++) {
            String cur = authorsList.getModel().getElementAt(i).strip();
            if (cur.length() > 1)
                authors.add(authorsList.getModel().getElementAt(i));
        }
        return authors;
    }
}
