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
        builder.append("}");
        return this;
    }

    public DatumBuilder addStr(@NotNull String key, @Nullable String value) {
        changeBegin();
        appendStringString(key, value);
        builder.append("}");
        return this;
    }

    public DatumBuilder addBook(@NotNull Book book) {
        changeBegin();
        builder.append("\"Book\" : ");
        appendBook(book);
        builder.append("}");
        return this;
    }

    private void appendBook(@NotNull Book book) {
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
            str2 = checkStringValue(str2);
            builder.append("\" : \"");
            builder.append(str2);
            builder.append("\"");
        }
    }


    private void appendStringArrString(@NotNull String str1, Vector<String> arr) {
        builder.append("\"");
        builder.append(str1);
        builder.append("\" : [");
        for (int i = 0; i < arr.size(); ++i) {
            if (i > 0)
                builder.append(", ");

            if (arr.get(i) == null)
                builder.append("null");
            else {
                String add = checkStringValue(arr.get(i));
                builder.append("\"");
                builder.append(add);
                builder.append("\"");
            }
        }
        builder.append("]");
    }

    private void changeBegin() {
        builder.setLength(builder.length() - 1);
        if (is_empty)
            is_empty = false;
        else
            builder.append(", ");
    }

    private String checkStringValue(@NotNull String str) {
        str = str.replace('\"', '\'');
        str = str.replace('\\', '|');
        return str;
    }
}
