package tool;

public class Database {
    String name;
    String path;
    public Database(String name, String path) {
        this.setName(name);
        this.setPath(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName(){
        return name;
    }

    public void setName(String databaseName) {
        this.name = databaseName;
    }
}