package vn.edu.csc.dictionary.model;

import java.io.Serializable;

public class DetailRow implements Serializable {
    public static final int WORD_TYPE = 0;
    public static final int LANG_TYPE = 1;
    public static final int CLASS_TYPE = 2;
    public static final int PHRASE_TYPE = 3;
    public static final int MEANING_TYPE = 4;
    public static final int EXAMPLE_TYPE = 5;
    public static final int EXPLAIN_TYPE = 6;

    String content;
    int type;

    public DetailRow(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
