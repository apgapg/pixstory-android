package com.jullae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedModel {
    @SerializedName("picture")
    @Expose
    private PictureModel pictureModel;

    @SerializedName("story")
    @Expose
    private StoryModel storyModel;

    public PictureModel getPictureModel() {
        return pictureModel;
    }

    public StoryModel getStoryModel() {
        return storyModel;
    }
}
