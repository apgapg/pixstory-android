package com.jullae.ui.home.homeFeed;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jullae.BR;
import com.jullae.data.db.model.StoryModel;

import java.util.Date;
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

    public class Feed extends BaseObservable {


        List<StoryModel> stories;
        private String id;
        private String picture_id;
        private String photographer_name;
        private String photographer_penname;
        private String photographer_avatar;
        private String picture_title;
        private Date created_at;
        private String picture_url;
        private int like_count;
        private int story_count;
        private String nav_story_id;
        private boolean is_liked;
        private String is_followed;
        private boolean is_self;
        private int highlightStoryIndex;

        public int getHighlightStoryIndex() {
            return highlightStoryIndex;
        }

        public void setHighlightStoryIndex(int highlightStoryIndex) {
            this.highlightStoryIndex = highlightStoryIndex;
        }

        public String getPhotographer_penname() {
            return photographer_penname;
        }

        public String getIs_followed() {
            return is_followed;
        }

        @Bindable
        public boolean getIs_liked() {
            return is_liked;
        }

        public void setIs_liked(boolean is_liked) {
            this.is_liked = is_liked;
            notifyPropertyChanged(BR.is_liked);

        }

        public Date getCreated_at() {
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

        @Bindable
        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public String getNav_story_id() {
            return nav_story_id;
        }

        public String getPicture_id() {
            return picture_id;
        }

        public int getStory_count() {
            return story_count;
        }

        public String getPicture_url() {
            return picture_url;
        }

        public String getPhotographer_avatar() {
            return photographer_avatar;
        }

        public List<StoryModel> getStories() {
            return stories;
        }

        public boolean getIs_self() {
            return is_self;
        }

        public void setDecrementLikeCount() {
            this.like_count--;
            notifyPropertyChanged(BR.like_count);
        }

        public void setIncrementLikeCount() {
            this.like_count++;
            notifyPropertyChanged(BR.like_count);
        }
    }


}


