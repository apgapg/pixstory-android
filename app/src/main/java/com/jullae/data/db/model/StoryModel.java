package com.jullae.data.db.model;

public class StoryModel {


    private String story_id;
    private String story_title;
    private String writer_id;
    private String writer_name;
    private String writer_penname;
    private String writer_avatar;
    private String story_text;
    private String like_count;
    private String comment_count;
    private String created_at;


    public String getWriter_id() {
        return writer_id;
    }



    public String getWriter_name() {
        return writer_name;
    }

    public String getStory_title() {
        return story_title;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public String getStory_text() {
        return story_text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getWriter_avatar() {
        return writer_avatar;
    }

    public String getStory_id() {
        return story_id;
    }

    public String getWriter_penname() {
        return writer_penname;
    }


}
