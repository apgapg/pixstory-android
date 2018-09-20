package com.jullae.data.db.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.jullae.BR;

public class ProfileModel extends BaseObservable {

    private String id;
    private String name;
    private String user_avatar;
    private String penname;
    private boolean is_followed;
    private String bio;
    private String account_type;
    private String follower_count;
    private String following_count;
    private String stories_count;
    private String pictures_count;
    private String is_self;
    private boolean unread_notifications;

    public ProfileModel(String id, String name, String penname, String bio, String dpUrl) {

        this.id = id;
        this.name = name;
        this.penname = penname;
        this.bio = bio;
        this.user_avatar = dpUrl;
    }

    public ProfileModel() {

    }

    @Bindable
    public boolean getUnread_notifications() {
        return unread_notifications;
    }

    @Bindable

    public String getId() {
        return id;
    }

    @Bindable

    public String getName() {
        return name;
    }

    @Bindable
    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
        notifyPropertyChanged(BR.user_avatar);
    }

    @Bindable
    public String getPenname() {
        return penname;
    }

    @Bindable
    public boolean getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(boolean is_followed) {
        this.is_followed = is_followed;
        notifyPropertyChanged(BR.is_followed);
    }

    @Bindable

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
        notifyPropertyChanged(BR.bio);
    }

    @Bindable

    public String getAccount_type() {
        return account_type;
    }

    @Bindable

    public String getFollower_count() {
        return follower_count;
    }


    @Bindable
    public String getFollowing_count() {
        return following_count;
    }


    @Bindable

    public String getStories_count() {
        return stories_count;
    }

    @Bindable

    public String getPictures_count() {
        return pictures_count;
    }

    public void setPictures_count(String pictures_count) {
        this.pictures_count = pictures_count;
        notifyPropertyChanged(BR.pictures_count);
    }


    public void incrementFollowingCount() {
        int value = Integer.valueOf(this.following_count);
        value=value+1;
        this.following_count = String.valueOf(value);
        notifyPropertyChanged(BR.following_count);

    }
    public void decrementFollowingCount() {
        int value = Integer.valueOf(this.following_count);
        value=value-1;
        this.following_count = String.valueOf(value);
        notifyPropertyChanged(BR.following_count);

    }
}