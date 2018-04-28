package com.jullae.data.db.model;

public class MessageModel {
    private String message_id;
    private String message;
    private String sent_by_name;
    private String sent_by_penname;
    private String sent_by_avatar;
    private String sent_by_id;
    private String is_self;
    private String sent_at;


    public String getSent_by_name() {
        return sent_by_name;
    }

    public String getSent_by_penname() {
        return sent_by_penname;
    }

    public String getMessage_id() {
        return message_id;
    }

    public String getMessage() {
        return message;
    }

    public String getSent_by_avatar() {
        return sent_by_avatar;
    }

    public String getSent_by_id() {
        return sent_by_id;
    }

    public String getIs_self() {
        return is_self;
    }

    public String getSent_at() {
        return sent_at;
    }
}