package model;

public class LineChange {

    private final Commit commit;
    private final String Commit_SHA;
    private final int Line_number;

    public LineChange(Commit commit, int line_number) {
        this.commit = commit;
        this.Commit_SHA = commit.getCommit_SHA();
        this.Line_number = line_number;
    }

    public String getCommit_SHA() {
        return Commit_SHA;
    }

    public int getLine_number() {
        return Line_number;
    }
}