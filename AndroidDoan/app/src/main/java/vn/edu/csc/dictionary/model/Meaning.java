package vn.edu.csc.dictionary.model;

public class Meaning {
    int meaningID;
    int wordID;
    String meaning;
    String phrase;
    String lang;
    String classes;

    public Meaning(int meaningID, int wordID, String meaning, String phrase, String lang, String classes) {
        this.meaningID = meaningID;
        this.wordID = wordID;
        this.meaning = meaning;
        this.phrase = phrase;
        this.lang = lang;
        this.classes = classes;
    }

    public int getMeaningID() {
        return meaningID;
    }

    public void setMeaningID(int meaningID) {
        this.meaningID = meaningID;
    }

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
