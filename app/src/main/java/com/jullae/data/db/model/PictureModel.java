package com.jullae.data.db.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

import java.util.Date;

public class PictureModel extends BaseObservable {

    private String picture_id;
    private String picture_title;
    private String picture_url;
    private String picture_url_small;
    private String picture_medium;
    private String photographer_name;
    private String photographer_penname;
    private String photographer_avatar;
    private int like_count;
    private String story_count;
    private Date created_at;
    private boolean is_liked;
    private String is_followed;
    private String is_self;


    public String getPicture_url() {
        return picture_url;
    }

    public String getPhotographer_avatar() {
        return photographer_avatar;
    }

    public String getPhotographer_name() {
        return photographer_name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    private String getIs_followed() {
        return is_followed;
    }

    @Bindable
    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
        notifyPropertyChanged(BR.like_count);
    }

    public String getPhotographer_penname() {
        return photographer_penname;
    }

    public String getPicture_id() {
        return picture_id;
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

    @Bindable
    public boolean getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
        notifyPropertyChanged(BR.is_liked);
    }

    public void setDecrementLikeCount() {
        this.like_count--;
        notifyPropertyChanged(BR.like_count);

    }

    public void setIncrementLikeCount() {
        this.like_count++;
        notifyPropertyChanged(BR.like_count);
    }
}