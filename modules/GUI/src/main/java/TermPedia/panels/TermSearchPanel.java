package TermPedia.panels;

import TermPedia.dialogs.BaseMessages;
import TermPedia.dto.ActionsException;
import TermPedia.dto.RatedBook;
import TermPedia.handlers.QueryHandler;
import TermPedia.queries.books.SearchBookByLikeTermQuery;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class TermSearchPanel extends javax.swing.JPanel {
    public TermSearchPanel(int width, int height) {
        this.setLayout(null);
        this.setVisible(false);

        JList<RatedBook> jList;
        JLabel jLabel, amount, labelOrder, labelRecently, minRating;
        JCheckBox isOderByRating, isAddedRecently;
        JButton search;
        JSlider slider;
        JSpinner jSpinner;
        JTextField termName;
        DefaultListModel model;

        jLabel = new JLabel("Term name    ", SwingConstants.CENTER);
        amount = new JLabel("Amount: ", SwingConstants.RIGHT);
        minRating = new JLabel("Minimal rating: ", SwingConstants.RIGHT);
        labelOrder = new JLabel("The best", SwingConstants.LEFT);
        labelRecently = new JLabel("New first", SwingConstants.LEFT);

        isOderByRating = new JCheckBox();
        isAddedRecently = new JCheckBox();

        termName = new JTextField();

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

        slider = new JSlider(0, 5, 0);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        search = new JButton("Search");
        search.addActionListener(e -> {
            SearchBookByLikeTermQuery query = new SearchBookByLikeTermQuery(
                    termName.getText(),
                    isOderByRating.isSelected(),
                    isAddedRecently.isSelected(),
                    slider.getValue(),
                    null,
                    (int) jSpinner.getValue(),
                    0
            );

            try {
                QueryHandler handler = new QueryHandler();
                handler.handle(query);
                model.removeAllElements();

                Vector<RatedBook> result = query.getResult().getBooks();
                if (result == null || result.size() == 0)
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

        termName.setBounds(width / 20 + stdWidth / 3, height / 20, stdWidth / 3, height / 10);
        this.add(termName);

        amount.setBounds(width / 20 + stdWidth / 3 * 2, height / 20, stdWidth / 12, height / 10);
        this.add(amount);

        jSpinner.setBounds(width / 20 + stdWidth / 4 * 3, height / 20, stdWidth / 12, height / 10);
        this.add(jSpinner);

        search.setBounds(width / 20 + stdWidth / 6 * 5, height / 20, stdWidth / 6, height / 10);
        this.add(search);


        labelOrder.setBounds(width / 20, height / 20, stdWidth / 8, height / 20);
        this.add(labelOrder);

        labelRecently.setBounds(width / 20, height / 10, stdWidth / 8, height / 20);
        this.add(labelRecently);

        isOderByRating.setBounds(width / 20 + stdWidth / 8, height / 20, stdWidth / 24, height / 20);
        this.add(isOderByRating);

        isAddedRecently.setBounds(width / 20 + stdWidth / 8, height / 10, stdWidth / 24, height / 20);
        this.add(isAddedRecently);

        jList.setBounds(width / 20, height / 5, stdWidth, height / 5 * 3);
        this.add(jList);

        slider.setBounds(width / 20 + stdWidth / 3, height / 20 * 17, stdWidth / 3, height / 10);
        this.add(slider);

        minRating.setBounds(width / 20, height / 20 * 17, stdWidth / 3, height / 9);
        this.add(minRating);
    }
}
