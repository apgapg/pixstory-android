package com.jullae.utils;

public class Constants {
    public final static String BASE_URL = "http://54.255.220.204:80/api/v1/";

    public static final String HOME_FEEDS = "/home/feed";

    public static final String FRESH_FEEDS = "/discover/new";
    public static final String POPULAR_FEEDS = "/discover/popular";
    public static final String ARCHIVED_FEEDS = "/discover/archived";

    public static final String SEARCH_TAG = "/search/tagged_stories";

    public static final String LIKE_STORY_URL = "/story/{id}/like";
    public static final String UNLIKE_STORY_URL = "/story/{id}/unlike";

    public static final String LIKE_PICTURE_URL = "/picture/{id}/like";
    public static final String UNLIKE_PICTURE_URL = "/picture/{id}/unlike";

    public static final String PICTURE_LIKES_LIST = "/picture/{id}/likes";
    public static final String STORY_LIKES_LIST = "/story/{id}/likes";

    public static final String FOLLOW = "/picture/{id}/follow";
    public static final String COMMENTS = "/story/{id}/comments";


    public static final String WRITE_COMMENTS = "/comment/publish";

    public static final String REPORT_STORY = "/report";

    public static final int LIKE_TYPE_STORY = 0;
    public static final int LIKE_TYPE_PICTURE = 1;
    public static final int REPORT_TYPE_STORY = 2;
}
