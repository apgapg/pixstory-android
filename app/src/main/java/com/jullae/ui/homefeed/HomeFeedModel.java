package com.jullae.ui.homefeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jullae.model.StoryModel;

import java.util.List;

/**
 * Created by Rahul Abrol on 12/21/17.
 * <p>
 * Feed Model.
 */
public class HomeFeedModel {

    @SerializedName("feed")
    @Expose
    public List<Feed> feedlist;

    public List<Feed> getFeedList() {
        return feedlist;
    }

    public class Feed {


        String id;

        String picture_id;

        String photographer_name;
        String photographer_penname;

        String photographer_avatar;

        String picture_title;

        String created_at;

        String picture_url;

        String like_count;

        String story_count;

        String nav_story_id;

        String is_liked;

        String is_followed;

        String is_self;


        List<StoryModel> stories;

        public String getPhotographer_penname() {
            return photographer_penname;
        }

        public String getIs_followed() {
            return is_followed;
        }

        public String getIs_liked() {
            return is_liked;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getPicture_title() {
            return picture_title;
        }

        public String getId() {
            return id;
        }

        public String getPhotographer_name() {
            return photographer_name;
        }

        public String getLike_count() {
            return like_count;
        }

        public String getNav_story_id() {
            return nav_story_id;
        }

        public String getPicture_id() {
            return picture_id;
        }

        public String getStory_count() {
            return story_count;
        }

        public void setIs_liked(String is_liked) {
            this.is_liked = is_liked;
        }

        public String getPicture_url() {
            return picture_url;
        }

        public Object getPhotographer_avatar() {
            return photographer_avatar;
        }

        public List<StoryModel> getStories() {
            return stories;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public String getIs_self() {
            return is_self;
        }
    }


}


