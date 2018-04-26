package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PictureListModel {

    @SerializedName("pictures")
    @Expose
    private List<PictureModel> pictureModelList;

    public List<PictureModel> getPictureModelList() {
        return pictureModelList;
    }
}
