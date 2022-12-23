package TermPedia.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Vector;

public class JsonBuilder implements DatumBuilder {
    private final StringBuilder builder;
    private boolean is_empty;

    public JsonBuilder() {
        this.builder = new StringBuilder(128);
        this.is_empty = true;
        builder.append("{}");
    }

    public JsonBuilder(int capacity) {
        this.builder = new StringBuilder(capacity);
        this.is_empty = true;
        builder.append("{}");
    }

    public String getData() {
        return builder.toString();
    }


    public DatumBuilder addInt(@NotNull String key, int value) {
        changeBegin();
        appendStringInt(key, value);
        changeCommit();
        return this;
    }

    public DatumBuilder addStr(@NotNull String key, @Nullable String value) throws ActionsException {
        checkStringValue(value);
        changeBegin();
        appendStringString(key, value);
        changeCommit();
        return this;
    }

    public DatumBuilder addBook(@NotNull Book book) throws ActionsException {
        checkStringValue(book.name);
        checkStringValue(book.type);
        checkStringArrValues(book.authors);

        changeBegin();
        appendBook(book);
        changeCommit();
        return this;
    }

    private void appendBook(@NotNull Book book) {
        builder.append("\"Book\" : ");
        builder.append("{");
        appendStringString("Name", book.name);
        builder.append(", ");
        appendStringString("Type", book.type);
        builder.append(", ");
        appendStringInt("Year", book.year);
        builder.append(", ");
        appendStringArrString("Authors", book.authors);
        builder.append("}");
    }


    private void appendStringInt(@NotNull String str, int num) {
        builder.append("\"");
        builder.append(str);
        builder.append("\" : ");
        builder.append(num);
    }

    private void appendStringString(@NotNull String str1, @Nullable String str2) {
        builder.append("\"");
        builder.append(str1);
        if (str2 == null)
            builder.append("\" : null");
        else {
            builder.append("\" : \"");
            builder.append(str2);
            builder.append("\"");
        }
    }

    private void appendStringArrString(@NotNull String str1, Vector<String> arr) {
        builder.append("\"");
        builder.append(str1);
        builder.append("\" : [");

        boolean isFirst = true;
        for (String value: arr) {
            if (isFirst)
                isFirst = false;
            else
                builder.append(", ");

            if (value == null)
                builder.append("null");
            else {
                builder.append("\"");
                builder.append(value);
                builder.append("\"");
            }
        }
        builder.append("]");
    }

    private void changeBegin() {
        builder.setLength(builder.length() - 1);
        if (!is_empty)
            builder.append(", ");
    }

    private void changeCommit() {
        is_empty = false;
        builder.append("}");
    }

    private void checkStringArrValues(Vector<String> arr) throws ActionsException {
        for (String str : arr)
            checkStringValue(str);
    }
    private void checkStringValue(@NotNull String str) throws ActionsException {
        if (str == null)
            return;
        if (str.contains("\\") || str.contains("\""))
            throw new ActionsException("String contains forbidden symbol '\\' or '\"'");
    }
}
