package com.jullae.data.db.model;

public class WriteStoryCategoryItem {
    private String id;
    private String name;

    WriteStoryCategoryItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
