package model;

public class Data {
    //4 properties (same as columns in data table)
    private int id;
    private String fileName;
    private String path;
    private String email;

    //constructor
    public Data(int id, String fileName, String path, String email) {
        this.id = id;
        this.fileName = fileName;
        this.path = path;
        this.email = email;
    }
    public Data(int id, String fileName, String path) {
        this.id = id;
        this.fileName = fileName;
        this.path = path;
    }

    //Getter (coz properties pvt hai)
    public int getId() {
        return id;
    }

    //Setter (coz properties pvt hai)
    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Data [id=" + id + ", fileName=" + fileName + ", path=" + path + ", email=" + email + "]";
    }
}