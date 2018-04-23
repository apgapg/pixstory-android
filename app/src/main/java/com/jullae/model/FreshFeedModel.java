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




    public class PictureModel {

        private String picture_id;
        private String picture_title;
        private String picture_url_small;
        private String picture_medium;
        private String photographer_name;
        private String photographer_penname;
        private String photographer_avatar;
        private String like_count;
        private String story_count;
        private String created_at;
        private String is_liked;
        private String is_followed;
        private String is_self;


        public String getPhotographer_avatar() {
            return photographer_avatar;
        }

        private String getPhotographer_name() {
            return photographer_name;
        }

        private String getCreated_at() {
            return created_at;
        }

        private String getIs_followed() {
            return is_followed;
        }

        public String getLike_count() {
            return like_count;
        }

        public String getPhotographer_penname() {
            return photographer_penname;
        }

        private String getPicture_id() {
            return picture_id;
        }

        private String getIs_liked() {
            return is_liked;
        }

        private String getIs_self() {
            return is_self;
        }

        private String getPicture_medium() {
            return picture_medium;
        }

        public String getPicture_title() {
            return picture_title;
        }

        public String getPicture_url_small() {
            return picture_url_small;
        }

        public String getStory_count() {
            return story_count;
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


