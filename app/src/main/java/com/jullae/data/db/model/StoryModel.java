package com.jullae.data.db.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jullae.BR;

import java.util.Date;
import java.util.List;

public class StoryModel extends BaseObservable {


    private String story_id;
    private String story_title;
    private String writer_id;
    private String writer_name;
    private String writer_penname;
    private String writer_avatar;
    private String story_text;
    private int like_count;
    private String comment_count;
    private boolean is_liked;
    private boolean is_self;
    private String is_followed;
    private Date created_at;
    private boolean has_more_comments;

    @SerializedName("comments")
    @Expose
    private List<CommentModel> commentModelList;

    public boolean isHas_more_comments() {
        return has_more_comments;
    }

    public List<CommentModel> getCommentModelList() {
        return commentModelList;
    }

    public String getWriter_id() {
        return writer_id;
    }

    @Bindable
    public String getWriter_name() {
        return writer_name;
    }

    public void setWriter_name(String writer_name) {
        this.writer_name = writer_name;
        notifyPropertyChanged(BR.writer_name);

    }

    @Bindable
    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
        notifyPropertyChanged(BR.story_title);
    }

    @Bindable
    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
        notifyPropertyChanged(BR.like_count);

    }

    @Bindable
    public String getComment_count() {
        return comment_count;
    }

    @Bindable
    public String getStory_text() {
        return story_text;
    }

    public void setStory_text(String story_text) {
        this.story_text = story_text;
        notifyPropertyChanged(BR.story_text);

    }

    public Date getCreated_at() {
        return created_at;
    }


    @Bindable
    public String getWriter_avatar() {
        return writer_avatar;
    }

    public String getStory_id() {
        return story_id;
    }

    public void setStory_id(String story_id) {
        this.story_id = story_id;
    }

    @Bindable
    public String getWriter_penname() {
        return writer_penname;
    }

    public void setWriter_penname(String writer_penname) {
        this.writer_penname = writer_penname;
        notifyPropertyChanged(BR.writer_penname);
    }

    @Bindable
    public boolean getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
        notifyPropertyChanged(BR.is_liked);
    }

    public boolean getIs_self() {
        return is_self;
    }

    public String getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(String is_followed) {
        this.is_followed = is_followed;
    }

}
