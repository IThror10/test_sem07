package TermPedia.panels;

import TermPedia.dialogs.BaseMessages;
import TermPedia.dto.*;
import TermPedia.handlers.QueryHandler;
import TermPedia.queries.books.SearchBookByAuthorQuery;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class AuthorSearchPanel extends javax.swing.JPanel {

    public AuthorSearchPanel(int width, int height) {
        this.setLayout(null);
        this.setVisible(false);


        JList<Book> jList;
        JLabel jLabel, amount;
        JButton search;
        JSpinner jSpinner;
        JTextField authorName;
        DefaultListModel model;

        jLabel = new JLabel("Author Name    ", SwingConstants.CENTER);
        amount = new JLabel("Amount: ", SwingConstants.RIGHT);

        authorName = new JTextField();

        jSpinner = new JSpinner(new SpinnerNumberModel(10, 10, 200, 5));

        jList = new JList();
        model = new DefaultListModel();
        jList.setModel(model);
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(15);
        jList.setFont(new Font("Arial",Font.BOLD,14));
        JScrollPane listScroller = new JScrollPane(jList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        search = new JButton("Search");
        search.addActionListener(e -> {
            SearchBookByAuthorQuery query = new SearchBookByAuthorQuery(
                    authorName.getText(),
                    (int) jSpinner.getValue(),
                    0
            );
            try {
                QueryHandler handler = new QueryHandler();
                handler.handle(query);
                model.removeAllElements();

                Vector<Book> books = query.getResult().getBooks();
                if (books.size() == 0)
                    BaseMessages.nothingFound();
                else
                    model.addAll(query.getResult().getBooks());
            } catch (ActionsException err) {
                BaseMessages.showErrorMsg(err.getMessage());
            }
        });



        int stdWidth = width / 10 * 9;

        jLabel.setBounds(width / 20 + stdWidth / 60 * 13, height / 20, stdWidth / 6, height / 10);
        this.add(jLabel);

        authorName.setBounds(width / 20 + stdWidth / 3, height / 20, stdWidth / 3, height / 10);
        this.add(authorName);

        amount.setBounds(width / 20 + stdWidth / 3 * 2, height / 20, stdWidth / 12, height / 10);
        this.add(amount);

        jSpinner.setBounds(width / 20 + stdWidth / 4 * 3, height / 20, stdWidth / 12, height / 10);
        this.add(jSpinner);

        search.setBounds(width / 20 + stdWidth / 6 * 5, height / 20, stdWidth / 6, height / 10);
        this.add(search);

        jList.setBounds(width / 20, height / 5, stdWidth, height / 5 * 3);
        this.add(jList);
    }
}
