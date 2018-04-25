package com.jullae.ui.profileSelf;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jullae.model.PictureModel;
import com.jullae.model.StoryModel;

import java.util.List;

public class StoryListModel {

    @SerializedName("stories")
    @Expose
    private List<StoryMainModel> storyMainModelList;

    public List<StoryMainModel> getStoryMainModelList() {
        return storyMainModelList;
    }

    public class StoryMainModel {
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

}
