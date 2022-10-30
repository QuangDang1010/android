package vn.edu.csc.dictionary.model;

public class Example {
    int exampleID;
    int meaningID;
    String example;
    String explain;

    public Example(int exampleID, int meaningID, String example, String explain) {
        this.exampleID = exampleID;
        this.meaningID = meaningID;
        this.example = example;
        this.explain = explain;
    }

    public Example(String example, String explain) {
        this.example = example;
        this.explain = explain;
    }

    public int getExampleID() {
        return exampleID;
    }

    public void setExampleID(int exampleID) {
        this.exampleID = exampleID;
    }

    public int getMeaningID() {
        return meaningID;
    }

    public void setMeaningID(int meaningID) {
        this.meaningID = meaningID;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
