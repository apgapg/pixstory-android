package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by master on 8/6/18.
 */
public class CommentMainModel {

    @SerializedName("comments")
    @Expose
    private List<CommentModel> commentModelList;

    public List<CommentModel> getCommentModelList() {
        return commentModelList;
    }
}
