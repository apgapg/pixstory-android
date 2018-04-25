package com.jullae.ui.profileSelf;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jullae.model.PictureModel;

import java.util.List;

public class PictureListModel {

    @SerializedName("pictures")
    @Expose
    private List<PictureModel> pictureModelList;

    public List<PictureModel> getPictureModelList() {
        return pictureModelList;
    }
}
