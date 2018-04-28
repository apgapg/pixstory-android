package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllStoriesModel {

    @SerializedName("stories")
    @Expose
    private List<StoryModel> storyModelList;

    public List<StoryModel> getStoryModelList() {
        return storyModelList;
    }
}
