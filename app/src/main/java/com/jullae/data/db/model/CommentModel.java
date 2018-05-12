package com.jullae.data.db.model;

public class CommentModel {

    private int id;
    private String comment;
    private String user_name;
    private String user_penname;
    private String user_avatar;
    private String created_at;

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_penname() {
        return user_penname;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public String getCreated_at() {
        return created_at;
    }
}
