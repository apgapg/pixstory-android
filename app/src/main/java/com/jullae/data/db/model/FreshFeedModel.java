package com.jullae.data.db.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul Abrol on 12/21/17.
 * <p>
 * Feed Model.
 */
public class FreshFeedModel {


    @SerializedName("posts")
    @Expose
    private List<FreshFeed> list;

    public List<FreshFeed> getList() {
        return list;
    }


    public class FreshFeed extends BaseObservable {
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


