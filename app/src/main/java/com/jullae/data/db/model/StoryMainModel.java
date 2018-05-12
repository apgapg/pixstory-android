package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoryMainModel {
    @SerializedName("story")
    @Expose
    private StoryModel storyModel;

    public StoryModel getStoryModel() {
        return storyModel;
    }
}
