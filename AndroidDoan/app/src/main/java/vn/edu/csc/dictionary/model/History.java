package vn.edu.csc.dictionary.model;

public class History {
    int id;
    int wordID;

    public History(int id, int wordID) {
        this.id = id;
        this.wordID = wordID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }
}
