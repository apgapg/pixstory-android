package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WriteStoryCategoryModel {

    @SerializedName("categories")
    @Expose
    private List<WriteStoryCategoryItem> categoryItemList;

    public List<WriteStoryCategoryItem> getCategoryItemList() {
        return categoryItemList;
    }
}
