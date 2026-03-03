package org.example.rest;

public class StaticFileConfi {
    private String path;

    public StaticFileConfi() {
        this.path = "";
    }

    public void path(String path) {
        this.path = limpiarRuta(path);
    }
    private String limpiarRuta(String path) {
        return path.startsWith("/") ? path.substring(1) : path;
    }

    public String getLocation() { return path; }
}
