package model;

public class Commit {
    private final String Commit_SHA;
    private final int Commit_number;
    private final Repository repository;
    private final String Repository_name;
    private final File file;
    private final String File_changed;
    private final String Author;
    private final Date date;

    public Commit(String commit_SHA, int commit_number, Repository repository, File file, String author, Date date) {
        this.Commit_SHA = commit_SHA;
        this.Commit_number = commit_number;
        this.repository = repository;
        this.Repository_name = repository.getRepository_name();
        this.file = file;
        this.File_changed = file.getFile_name();
        this.Author = author;
        this.date = date;
    }

    public String getCommit_SHA() {
        return Commit_SHA;
    }

    public int getCommit_number() {
        return Commit_number;
    }

    public String getRepository_name() {
        return Repository_name;
    }

    public String getFile_changed() {
        return File_changed;
    }

    public String getAuthor() {
        return Author;
    }

    public Date getDate() {
        return date;
    }
}