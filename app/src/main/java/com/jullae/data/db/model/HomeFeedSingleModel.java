package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jullae.ui.home.homeFeed.HomeFeedModel;

/**
 * Created by Rahul Abrol on 12/21/17.
 * <p>
 * Feed Model.
 */
public class HomeFeedSingleModel {

    @SerializedName("feed")
    @Expose
    public HomeFeedModel.Feed feedModel;

    public HomeFeedModel.Feed getFeedModel() {
        return feedModel;
    }



}


