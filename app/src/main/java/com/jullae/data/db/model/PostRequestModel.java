package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * Model.
 */

public class PostRequestModel {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("picture_id")
    @Expose
    private int pictureId;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
