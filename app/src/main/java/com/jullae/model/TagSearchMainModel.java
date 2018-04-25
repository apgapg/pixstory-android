package com.jullae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TagSearchMainModel {
    @SerializedName("tagged_stories")
    @Expose
    private List<FeedModel> feedModelList;

    public List<FeedModel> getFeedModelList() {
        return feedModelList;
    }

}
