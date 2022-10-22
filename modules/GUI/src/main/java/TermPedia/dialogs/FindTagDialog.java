package TermPedia.dialogs;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Tag;
import TermPedia.handlers.QueryHandler;
import TermPedia.queries.instances.tags.FindTagByNameQuery;

import javax.swing.*;
import java.util.Objects;
import java.util.Vector;

public class FindTagDialog extends BaseMessages {
    public Tag execute(Tag tag, boolean existOnly) {
        JComponent[] inputs;
        int result;

        FindTagByNameQuery query = new FindTagByNameQuery(tag.name, 14, 0);
        try {
            QueryHandler handler = new QueryHandler();
            handler.handle(query);
        } catch (ActionsException e) {
            showErrorMsg(e.getMessage());
            return null;
        }

        Vector<Tag> tags = query.getResult().getTags();
        if (!existOnly) {
            boolean found = false;
            for (Tag cur : tags)
                found |= Objects.equals(cur.name, tag.name);
            if (!found)
                tags.insertElementAt(tag, 0);
        }

        if (tags.size() == 0) {
            BaseMessages.showErrorMsg("No tags found");
            return null;
        }

        JComboBox<Tag> comboBox = new JComboBox<>(tags);
        comboBox.setMaximumRowCount(15);
        comboBox.setSelectedItem(tags.get(0));
        inputs = new JComponent[]{
                new JLabel("Search results:"),
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
            return tags.get(comboBox.getSelectedIndex());
        return null;
    }
}
