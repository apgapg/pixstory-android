package com.jullae.model;

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


    public class FreshFeed {
        @SerializedName("picture")
        @Expose
        private PictureModel pictureModel;
        @SerializedName("story")
        @Expose
        private Story story;

        public PictureModel getPictureModel() {
            return pictureModel;
        }

        public Story getStory() {
            return story;
        }
    }






    public class Story {
        private String story_id;
        private String story_title;
        private String writer_id;
        private String writer_name;
        private String writer_penname;
        private String writer_avatar;
        private String story_text;
        private String like_count;
        private String comment_count;
        private String created_at;


        public String getLike_count() {
            return like_count;
        }

        private String getCreated_at() {
            return created_at;
        }

        public String getComment_count() {
            return comment_count;
        }

        private String getStory_id() {
            return story_id;
        }

        public String getStory_title() {
            return story_title;
        }

        public String getStory_text() {
            return story_text;
        }

        public String getWriter_avatar() {
            return writer_avatar;
        }

        private String getWriter_id() {
            return writer_id;
        }

        private String getWriter_name() {
            return writer_name;
        }

        public String getWriter_penname() {
            return writer_penname;
        }
    }
}


