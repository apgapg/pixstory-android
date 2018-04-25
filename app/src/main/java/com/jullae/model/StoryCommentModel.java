package com.jullae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * model For comments.
 */

public class StoryCommentModel {
    @SerializedName("story")
    @Expose
    private StoryDetailModel storyDetailModel;

    public StoryDetailModel getStoryDetailModel() {
        return storyDetailModel;
    }

    public class StoryDetailModel {

        private String id;
        private String picture_id;
        private String is_liked;
        private String is_self;
        private String is_followed;
        @SerializedName("comments")
        @Expose
        private List<Comment> comments;

        public String getId() {
            return id;
        }

        public String getPicture_id() {
            return picture_id;
        }

        public String getIs_liked() {
            return is_liked;
        }

        public void setIs_liked(String is_liked) {
            this.is_liked = is_liked;
        }

        public String getIs_self() {
            return is_self;
        }

        public String getIs_followed() {
            return is_followed;
        }

        public void setIs_followed(String is_followed) {
            this.is_followed = is_followed;
        }

        public List<Comment> getComments() {
            return comments;
        }
    }


    public class Comment {


        private int id;

        private int story_id;

        private String comment;

        private String user_name;
        private String user_penname;

        private String user_dp_url;

        private String created_at;

        public int getId() {
            return id;
        }

        public int getStory_id() {
            return story_id;
        }

        public String getComment() {
            return comment;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_penname() {
            return user_penname;
        }

        public String getUser_dp_url() {
            return user_dp_url;
        }

        public String getCreated_at() {
            return created_at;
        }
    }


}
