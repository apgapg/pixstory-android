package com.jullae.data.db.model;

public class PictureModel {

    private String picture_id;
    private String picture_title;
    private String picture_url_small;
    private String picture_medium;
    private String photographer_name;
    private String photographer_penname;
    private String photographer_avatar;
    private String like_count;
    private String story_count;
    private String created_at;
    private String is_liked;
    private String is_followed;
    private String is_self;


    public String getPhotographer_avatar() {
        return photographer_avatar;
    }

    public String getPhotographer_name() {
        return photographer_name;
    }

    private String getCreated_at() {
        return created_at;
    }

    private String getIs_followed() {
        return is_followed;
    }

    public String getLike_count() {
        return like_count;
    }

    public String getPhotographer_penname() {
        return photographer_penname;
    }

    public String getPicture_id() {
        return picture_id;
    }

    private String getIs_liked() {
        return is_liked;
    }

    private String getIs_self() {
        return is_self;
    }

    private String getPicture_medium() {
        return picture_medium;
    }

    public String getPicture_title() {
        return picture_title;
    }

    public String getPicture_url_small() {
        return picture_url_small;
    }

    public String getStory_count() {
        return story_count;
    }
}