package model;

public class Repository_Object {
    private final String repositoryName;
    private final String File_name;
    private final String Path;
    private final int Size;

    public Repository_Object(String repositoryName, String file_name, String path, int size) {
        this.repositoryName = repositoryName;
        this.File_name = file_name;
        this.Path = path;
        this.Size = size;
    }

    public String getFile_name() {
        return File_name;
    }

    public String getPath() {
        return Path;
    }

    public int getSize() {
        return Size;
    }
}