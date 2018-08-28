package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuggestionMainModel {
    //{
    //    "tag_suggestions": [
    //        {
    //            "tag_id": 2,
    //            "tag_name": "mountain",
    //            "count": 1
    //        },
    //        {
    //            "tag_id": 3,
    //            "tag_name": "khardungla",
    //            "count": 1
    //        },
    //        {
    //            "tag_id": 4,
    //            "tag_name": "passes",
    //            "count": 1
    //        },
    //        {
    //            "tag_id": 5,
    //            "tag_name": "kahani",
    //            "count": 1
    //        },
    //        {
    //            "tag_id": 7,
    //            "tag_name": "mountains",
    //            "count": 1
    //        }
    //    ]
    //}
    @SerializedName("tag_suggestions")
    @Expose
    private List<SuggestionModel> suggestionModelList;

    public List<SuggestionModel> getSuggestionModelList() {
        return suggestionModelList;
    }

    public static class SuggestionModel {
        private String tag_id;
        private String tag_name;
        private String count;

        public String getCount() {
            return count;
        }

        public String getTag_id() {
            return tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }
    }
}
