package com.jullae.ui.homefeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

        String photographer_dp_url;

        String picture_title;

        String created_at;

        String picture_url;

        String like_count;

        String story_count;

        String nav_story_id;

        List<Story> stories;

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

        public Object getPhotographer_dp_url() {
            return photographer_dp_url;
        }

        public String getPicture_url() {
            return picture_url;
        }


        public List<Story> getStories() {
            return stories;
        }
    }

    public class Story {


        String id;

        String writer_name;

        String story_created_at;

        String content;

        String story_like_count;
        String story_comment_count;

        String story_count;

        String story_title;

        String writer_dp_Url;

        public String getId() {
            return id;
        }

        public String getWriter_name() {
            return writer_name;
        }

        public String getStory_title() {
            return story_title;
        }

        public String getStory_count() {
            return story_count;
        }

        public String getStory_like_count() {
            return story_like_count;
        }

        public String getStory_comment_count() {
            return story_comment_count;
        }

        public String getContent() {
            return content;
        }

        public String getStory_created_at() {
            return story_created_at;
        }

        public String getWriter_dp_Url() {
            return writer_dp_Url;
        }
    }
}


