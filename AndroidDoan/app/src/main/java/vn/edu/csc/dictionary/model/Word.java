package vn.edu.csc.dictionary.model;

import java.io.Serializable;

public class Word implements Serializable {
    int wordId;
    String name;
    String lang;


    public Word(int wordId, String name, String lang) {
        this.wordId = wordId;
        this.name = name;
        this.lang = lang;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
