package model;

public class Directory extends Repository_Object {
    private final String Parent_directory_path;

    public Directory(String name, String path, int size, String parent_directory_path) {
        super(name, path, size);
        this.Parent_directory_path = parent_directory_path;
    }

    public String getParent_directory_path() {
        return Parent_directory_path;
    }
}
