package model;

public class LineChange {
    private final String Commit_SHA;
    private final int Line_number;

    public LineChange(String commit_SHA, int line_number) {
        this.Commit_SHA = commit_SHA;
        this.Line_number = line_number;
    }

    public String getCommit_SHA() {
        return Commit_SHA;
    }

    public int getLine_number() {
        return Line_number;
    }
}