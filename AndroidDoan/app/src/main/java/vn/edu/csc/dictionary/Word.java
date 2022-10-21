package vn.edu.csc.dictionary;

public class Word {
    String wordId;
    String name;
    String meaning;


    public Word(String wordId, String name, String meaning) {
        this.wordId = wordId;
        this.name = name;
        this.meaning = meaning;
    }

    public String getWordId() {
        return wordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
