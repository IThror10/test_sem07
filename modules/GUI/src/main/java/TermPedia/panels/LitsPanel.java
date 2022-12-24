package TermPedia.panels;

import TermPedia.dialogs.BaseMessages;
import TermPedia.dialogs.FindLitTypeDialog;
import TermPedia.dialogs.FindTagDialog;
import TermPedia.dialogs.GetAuthorsDialog;
import TermPedia.dto.*;
import TermPedia.events.data.AddLitToTermEvent;
import TermPedia.events.data.AddTagToTermEvent;
import TermPedia.events.data.ChangeTermLitRatingEvent;
import TermPedia.events.data.ChangeTermTagRatingEvent;
import TermPedia.events.user.User;
import TermPedia.factory.query.QueryFactory;
import TermPedia.handlers.EventHandler;
import TermPedia.handlers.QueryHandler;
import TermPedia.queries.books.SearchBookByLikeNameQuery;
import TermPedia.queries.books.SearchBookByTermQuery;

import javax.swing.*;
import java.awt.*;
import java.beans.beancontext.BeanContextServiceAvailableEvent;
import java.util.Vector;

public class LitsPanel extends javax.swing.JPanel {
    private final int width;
    private final int height;

    DefaultListModel model;
    Integer uid;
    Vector<RatedBook> books;
    JButton addTag;
    Term term;

    public LitsPanel(int _width, int _height) {
        this.setLayout(null);
        this.setVisible(false);
        this.width = _width;
        this.height = _height;

        TagBook temp;
        try {
            temp = new TagBook("Add New", "<<Type>>", 0, new Vector<>(), 0);
        } catch (ActionsException e) {
            temp = null;
        }
        TagBook addNew = temp;

        JList<RatedBook> jList;
        JSlider slider;
        JButton rateTag;
        JTextField inBook;

        inBook = new JTextField();
        addTag = new JButton("Add literature ->");
        addTag.addActionListener(e -> {
            String bookName = inBook.getText();
            if (bookName.length() < 2) {
                BaseMessages.showErrorMsg("Enter lit name");
                return;
            }

            SearchBookByLikeNameQuery query = new SearchBookByLikeNameQuery(
                    bookName, true, 0, 20, 0);

            try {
                QueryHandler handler = new QueryHandler();
                handler.handle(query);
            } catch (ActionsException err) {
                BaseMessages.showErrorMsg(err.getMessage());
                return;
            }

            JComboBox<TagBook> comboBox = new JComboBox<>(query.getResult().getBooks());
            comboBox.insertItemAt(addNew, 0);

            JComponent[] inputs = new JComponent[] {
                    new JLabel("Search results:"),
                    comboBox
            };
            int result = JOptionPane.showConfirmDialog(null,
                    inputs,
                    "Add literature",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION || comboBox.getSelectedItem() == null)
                return;
            else if (comboBox.getSelectedIndex() != 0){
                EventHandler handler = new EventHandler();
                try {
                    AddLitToTermEvent event = new AddLitToTermEvent(term.name, (TagBook) comboBox.getSelectedItem(), uid);
                    handler.handle(event);
                    if (event.getResult().getStatus())
                        BaseMessages.successMsg("Literature will be added soon");
                    return;
                } catch (ActionsException err) {
                    BaseMessages.showErrorMsg(err.getMessage());
                    return;
                }
            }

            //Добавление своего
            result = JOptionPane.showConfirmDialog(null,
                    new JLabel("Add literature " + bookName),
                    "New lit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            //Тип
            FindLitTypeDialog dialog = new FindLitTypeDialog();
            String litType = dialog.execute();

            //Год
            JTextField inYear = new JTextField();
            inputs = new JComponent[] {
                    new JLabel("Enter year"),
                    inYear
            };

            int year;
            result = JOptionPane.showConfirmDialog(null,
                    inputs,
                    "Year",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (result != JOptionPane.OK_OPTION)
                return;
            try {
                year = Integer.parseInt(inYear.getText());
            }
            catch (NumberFormatException err) {
                BaseMessages.showErrorMsg("Wrong year");
                return;
            }

            //Authors
            GetAuthorsDialog dialog1 = new GetAuthorsDialog();
            Vector<String> authors = dialog1.execute();

            try {
                Book book = new Book(bookName, litType, year, authors);
                AddLitToTermEvent event = new AddLitToTermEvent(term.name, book, uid);
                EventHandler handler = new EventHandler();
                handler.handle(event);
                if (event.getResult().getStatus())
                    BaseMessages.successMsg("Literature will be added soon");
            } catch (ActionsException err) {
                BaseMessages.showErrorMsg(err.getMessage());
            }

        });

        slider = new JSlider(1, 5, 3);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        jList = new JList();
        model = new DefaultListModel();
        jList.setModel(model);
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(10);
        JScrollPane listScroller = new JScrollPane(jList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        //Rate Button
        rateTag = new JButton("Rate literature");
        rateTag.addActionListener(e -> {
            if (jList.getSelectedValue() == null) {
                BaseMessages.showErrorMsg("Choose source from list");
                return;
            }

            int index = jList.getSelectedIndex();
            int rating = slider.getValue();
            RatedBook book = books.get(index);

            try {
                ChangeTermLitRatingEvent event = new ChangeTermLitRatingEvent(
                        term.name, book, rating, uid
                );
                EventHandler handler = new EventHandler();
                handler.handle(event);

                if (book.userRating == 0) {
                    book.rating = (book.rating * book.ratesAmount + rating) / (double)(book.ratesAmount + 1);
                    book.ratesAmount++;
                } else {
                    int diff = rating - book.userRating;
                    book.rating = (book.rating * book.ratesAmount + diff) / (double) book.ratesAmount;
                }
                book.userRating = rating;
                model.setElementAt(book, index);
            } catch (ActionsException err) {
                BaseMessages.showErrorMsg(err.getMessage());
            }
        });

        int stdWidth = width / 20 * 18;

        addTag.setBounds(width / 20, height / 20, stdWidth / 2, height / 10);
        this.add(addTag);

        inBook.setBounds(width / 20 + stdWidth / 2, height / 20, stdWidth / 2, height / 10);
        this.add(inBook);

        jList.setBounds(width / 20, height / 5, stdWidth, height / 5 * 3);
        this.add(jList);

        slider.setBounds(width / 20, height / 20 * 17, stdWidth / 20 * 9, height / 20 * 3);
        this.add(slider);

        rateTag.setBounds(width / 20 + stdWidth / 2, height / 20 * 17, stdWidth / 2, height / 10);
        this.add(rateTag);
    }

    public void refresh(Term term) {
        this.term = term;
        addTag.setText("Add source to term " + term.name + " -> ");
        SearchBookByTermQuery query = new SearchBookByTermQuery(
                term.name,
                true,
                false,
                0,
                uid,
                40,
                0
        );
        QueryHandler handler = new QueryHandler();
        try {
            handler.handle(query);
            model.removeAllElements();
            books = query.getResult().getBooks();
            model.addAll(books);
        } catch (ActionsException exception) {
            BaseMessages.showErrorMsg(exception.getMessage());
        }
    }

    public void setUser(User user) {
        this.uid = user == null ? null : user.UID;
    }
}
