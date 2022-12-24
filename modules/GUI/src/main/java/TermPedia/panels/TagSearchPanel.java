package TermPedia.panels;

import TermPedia.dialogs.BaseMessages;
import TermPedia.dialogs.FindTagDialog;
import TermPedia.dto.ActionsException;
import TermPedia.dto.RatedBook;
import TermPedia.dto.Tag;
import TermPedia.dto.TagBook;
import TermPedia.handlers.QueryHandler;
import TermPedia.queries.books.SearchBookByTagQuery;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class TagSearchPanel extends javax.swing.JPanel {
    public TagSearchPanel(int width, int height) {
        this.setLayout(null);
        this.setVisible(false);

        JList<RatedBook> bookList;
        JList<Tag> tagList;
        JSlider slider;
        JSpinner jSpinner;
        JButton addTag, deleteTag, searchBook;
        JCheckBox isOrderByRating;
        JLabel amount, labelOrder, minRating;

        DefaultListModel tagModel, bookModel;

        amount = new JLabel("Amount: ", SwingConstants.RIGHT);
        labelOrder = new JLabel("The best", SwingConstants.LEFT);
        minRating = new JLabel("Minimal rating: ");

        isOrderByRating = new JCheckBox();

        jSpinner = new JSpinner(new SpinnerNumberModel(10, 10, 200, 5));

        bookList = new JList();
        bookModel = new DefaultListModel();
        bookList.setModel(bookModel);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        bookList.setLayoutOrientation(JList.VERTICAL);
        bookList.setVisibleRowCount(15);
        bookList.setFont(new Font("Arial",Font.BOLD,14));
        JScrollPane listScroller = new JScrollPane(bookList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        tagList = new JList();
        tagModel = new DefaultListModel();
        tagList.setModel(tagModel);
        tagList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tagList.setLayoutOrientation(JList.VERTICAL);
        tagList.setVisibleRowCount(15);
        tagList.setFont(new Font("Arial",Font.BOLD,14));
        JScrollPane tagListScroller = new JScrollPane(tagList);
        tagListScroller.setPreferredSize(new Dimension(250, 80));

        slider = new JSlider(0, 5, 0);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        addTag = new JButton("Add tag");
        addTag.addActionListener(e -> {
            JTextField tagName = new JTextField();
            final JComponent[] inputs = new JComponent[] {
                    new JLabel("Enter tag name:"),
                    tagName
            };

            int result = JOptionPane.showConfirmDialog(null, inputs, "Add tag", JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION)
                return;

            FindTagDialog dialog = new FindTagDialog();
            Tag tag = dialog.execute(new Tag(tagName.getText()), true);

            if (tag != null) {
                tagModel.addElement(tag);
            }
        });

        deleteTag = new JButton("Remove tag");
        deleteTag.addActionListener(e -> {
            Tag tag = tagList.getSelectedValue();
            if (tag != null) {
                int index = tagList.getSelectedIndex();
                tagModel.remove(index);
            }
        });

        searchBook = new JButton("Search");
        searchBook.addActionListener(e -> {
            SearchBookByTagQuery query = new SearchBookByTagQuery(
                    isOrderByRating.isSelected(),
                    slider.getValue(),
                    (int) jSpinner.getValue(),
                    0
            );

            int size = tagList.getModel().getSize();
            Vector<String> tags = new Vector<>(size);
            for (int i = 0; i < size; i++)
                tags.add(tagList.getModel().getElementAt(i).name);

            query.setTags(tags);
            QueryHandler handler = new QueryHandler();
            try {
                handler.handle(query);
                bookModel.removeAllElements();
                Vector<TagBook> books = query.getResult().getBooks();
                if (books.size() == 0)
                    BaseMessages.nothingFound();
                else
                    bookModel.addAll(query.getResult().getBooks());
            } catch (ActionsException err) {
                BaseMessages.showErrorMsg(err.getMessage());
            }
        });


        int stdWidth = width / 10 * 9;

        addTag.setBounds(width / 20, height / 20, stdWidth / 6, height / 10);
        this.add(addTag);

        deleteTag.setBounds(width / 20 + stdWidth / 6, height / 20, stdWidth / 6, height / 10);
        this.add(deleteTag);

        tagList.setBounds(width / 20, height / 5, stdWidth / 3, height / 5 * 3);
        this.add(tagList);

        amount.setBounds(width / 20 + stdWidth / 3 * 2, height / 20, stdWidth / 12, height / 10);
        this.add(amount);

        jSpinner.setBounds(width / 20 + stdWidth / 4 * 3, height / 20, stdWidth / 12, height / 10);
        this.add(jSpinner);

        searchBook.setBounds(width / 20 + stdWidth / 6 * 5, height / 20, stdWidth / 6, height / 10);
        this.add(searchBook);


        labelOrder.setBounds(width / 2, height / 20, stdWidth / 12, height / 10);
        this.add(labelOrder);

        isOrderByRating.setBounds(width / 2 + stdWidth / 12, height / 20, stdWidth / 24, height / 10);
        this.add(isOrderByRating);

        bookList.setBounds(width / 10 * 4 + 20, height / 5, width / 20 * 11 - 20, height / 5 * 3);
        this.add(bookList);

        slider.setBounds(width / 2 + stdWidth / 6, height / 20 * 17, stdWidth / 3, height / 10);
        this.add(slider);

        minRating.setBounds(width / 2, height / 20 * 17, stdWidth / 6, height / 9);
        this.add(minRating);
    }
}
