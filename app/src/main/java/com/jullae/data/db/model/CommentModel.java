package com.jullae.data.db.model;

import java.util.Date;

public class CommentModel {

    private int id;
    private String comment;
    private String user_name;
    private String user_penname;
    private String user_avatar;
    private Date created_at;
    private boolean is_self;

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

    public Date getCreated_at() {
        return created_at;
    }

    public boolean isIs_self() {
        return is_self;
    }
}
