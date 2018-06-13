package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul Abrol on 12/25/17.
 * <p>
 * Model class for all likes.
 */
public class FollowersModel {
    @SerializedName("followers")
    @Expose
    private List<LikesModel.Like> followers;

    public List<LikesModel.Like> getLikes() {
        return followers;
    }


}
