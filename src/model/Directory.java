package model;

public class Directory extends Repository_Object {
    private final Repository_Object parent_dir;
    private final String Parent_directory_path;

    public Directory(String name, String path, int size, Repository_Object parent) {
        super(name, path, size);
        parent_dir = parent;
        this.Parent_directory_path = parent_dir.getPath();
    }

    public String getParent_directory_path() {
        return Parent_directory_path;
    }
}
