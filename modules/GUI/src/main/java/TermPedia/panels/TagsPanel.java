package TermPedia.panels;

import TermPedia.dialogs.BaseMessages;
import TermPedia.dialogs.FindTagDialog;
import TermPedia.dto.ActionsException;
import TermPedia.dto.RatedTag;
import TermPedia.dto.Tag;
import TermPedia.dto.Term;
import TermPedia.events.data.AddTagToTermEvent;
import TermPedia.events.data.ChangeTermTagRatingEvent;
import TermPedia.events.user.User;
import TermPedia.handlers.EventHandler;
import TermPedia.handlers.QueryHandler;
import TermPedia.queries.instances.tags.FindTagByTermNameQuery;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class TagsPanel extends javax.swing.JPanel {
    private final int width;
    private final int height;

    DefaultListModel model;
    Integer uid;
    Vector<RatedTag> tags;
    JButton addTag;
    Term term;

    public TagsPanel(int _width, int _height) {
        this.setLayout(null);
        this.setVisible(false);
        this.width = _width;
        this.height = _height;


        JList<RatedTag> jList;
        JSlider slider;
        JButton rateTag;
        JTextField inTag;

        inTag = new JTextField();
        addTag = new JButton("Add tag ->");
        addTag.addActionListener(e -> {
            FindTagDialog dialog = new FindTagDialog();
            Tag tag = dialog.execute(new Tag(inTag.getText()), false);
            if (tag == null)
                return;

            AddTagToTermEvent event = new AddTagToTermEvent(term.name, tag.name, uid);
            try {
                EventHandler handler = new EventHandler();
                handler.handle(event);
                if (event.getResult().getStatus())
                    BaseMessages.successMsg("Tag will be added soon");
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
        rateTag = new JButton("Rate tag");
        rateTag.addActionListener(e -> {
            if (jList.getSelectedValue() == null) {
                BaseMessages.showErrorMsg("Choose tag from list");
                return;
            }

            int index = jList.getSelectedIndex();
            int rating = slider.getValue();
            RatedTag tag = tags.get(index);

            try {
                ChangeTermTagRatingEvent event = new ChangeTermTagRatingEvent(
                        term.name, tag.name, rating, uid
                );
                EventHandler handler = new EventHandler();
                handler.handle(event);

                if (tag.userRating == 0) {
                    tag.rating = (tag.rating * tag.ratesAmount + rating) / (double)(tag.ratesAmount + 1);
                    tag.ratesAmount++;
                } else {
                    int diff = rating - tag.userRating;
                    tag.rating = (tag.rating * tag.ratesAmount + diff) / (double) tag.ratesAmount;
                }
                tag.userRating = rating;
                model.setElementAt(tag, index);
            } catch (ActionsException err) {
                BaseMessages.showErrorMsg(err.getMessage());
            }
        });

        int stdWidth = width / 20 * 18;

        addTag.setBounds(width / 20, height / 20, stdWidth / 2, height / 10);
        this.add(addTag);

        inTag.setBounds(width / 20 + stdWidth / 2, height / 20, stdWidth / 2, height / 10);
        this.add(inTag);

        jList.setBounds(width / 20, height / 5, stdWidth, height / 5 * 3);
        this.add(jList);

        slider.setBounds(width / 20, height / 20 * 17, stdWidth / 20 * 9, height / 20 * 3);
        this.add(slider);

        rateTag.setBounds(width / 20 + stdWidth / 2, height / 20 * 17, stdWidth / 2, height / 10);
        this.add(rateTag);
    }

    public void refresh(Term term) {
        this.term = term;
        addTag.setText("Add tag to term " + term.name + " -> ");
        FindTagByTermNameQuery query = new FindTagByTermNameQuery(term.name, 50, 0, uid, false);
        QueryHandler handler = new QueryHandler();
        try {
            handler.handle(query);
            model.removeAllElements();
            tags = query.getResult().getTags();
            model.addAll(tags);
        } catch (ActionsException exception) {
            BaseMessages.showErrorMsg(exception.getMessage());
        }
    }

    public void setUser(User user) {
        this.uid = user == null ? null : user.UID;
    }
}
