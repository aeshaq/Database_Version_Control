package model;

public class Repository_Object {
    private final String repositoryName;
    private final String Path;
    private final int Size;

    public Repository_Object(String repositoryName, String path, int size) {
        this.repositoryName = repositoryName;
        this.Path = path;
        this.Size = size;
    }

    public String getPath() {
        return Path;
    }

    public int getSize() {
        return Size;
    }
}