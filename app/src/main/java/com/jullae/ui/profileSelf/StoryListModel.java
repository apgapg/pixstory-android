package com.jullae.ui.profileSelf;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jullae.model.FeedModel;

import java.util.List;

public class StoryListModel {

    @SerializedName("stories")
    @Expose
    private List<FeedModel> storyMainModelList;

    public List<FeedModel> getStoryMainModelList() {
        return storyMainModelList;
    }

}
