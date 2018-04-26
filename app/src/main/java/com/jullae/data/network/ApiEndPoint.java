package com.jullae.data.network;

public class ApiEndPoint {

    public static final String ENDPOINT_HOME_FEEDS = "/home/feed";
    public static final String ENDPOINT_FRESH_FEEDS = "/discover/new";
    public static final String ENDPOINT_POPULAR_FEEDS = "/discover/popular";
    public static final String ENDPOINT_ARCHIVED_FEEDS = "/discover/archived";
    public static final String ENDPOINT_SEARCH_TAG = "/search/tagged_stories";
    public static final String ENDPOINT_LIKE_STORY_URL = "/story/{id}/like";
    public static final String ENDPOINT_UNLIKE_STORY_URL = "/story/{id}/unlike";
    public static final String ENDPOINT_LIKE_PICTURE_URL = "/picture/{id}/like";
    public static final String ENDPOINT_UNLIKE_PICTURE_URL = "/picture/{id}/unlike";
    public static final String ENDPOINT_PICTURE_LIKES_LIST = "/picture/{id}/likes";
    public static final String ENDPOINT_STORY_LIKES_LIST = "/story/{id}/likes";
    public static final String ENDPOINT_FOLLOW = "/picture/{id}/follow";
    public static final String ENDPOINT_STORY_DETAILS = "/story/{id}/view";
    public static final String ENDPOINT_EMAIL_SIGNUP = "/custom_registration";
    public static final String ENDPOINT_EMAIL_LOGIN = "/custom_authentication";
    public static final String ENDPOINT_PROFILE_TAB_PICTURES = "/profile/{penname}/pictures";
    public static final String ENDPOINT_PROFILE_TAB_STORIES = "/profile/{penname}/stories";
    public static final String ENDPOINT_PROFILE_TAB_BOOKMARKS = "/profile/{penname}/bookmarks";
    public static final String ENDPOINT_PROFILE_PIC_UPDATE = "/user/{id}/upload_dp";
    public static final String ENDPOINT_WRITE_COMMENTS = "/comment/publish";
    public static final String ENDPOINT_REPORT_STORY = "/report";
    public static final String ENDPOINT_DRAFTS = "/profile/{penname}/drafts";
    public static final String ENDPOINT_PROFILE_VISITOR_INFO = "/profile/{penname}/info";

    public static final String ENDPOINT_CONVERSATION_LIST = "/user/conversations";
    public static final String ENDPOINT_MESSAGE_LIST = "/user/{id}/messages";
    public static final String ENDPOINT_ADD_MESSAGE = "/user/{id}/message";
    public static final String ENDPOINT_TAG_SUGGESTIONS = "/suggestion/tag_suggestions";
    public static final String ENDPOINT_PUBLISH_STORY = "/story/publish";
    public static final String ENDPOINT_PUBLISH_DRAFT = "/story/draft";
    public static final String ENDPOINT_DELETE_STORY = "/story/{id}/destroy";
    public static final String ENDPOINT_DELETE_BOOKMARK = "/story/{id}/remove_bookmark";

    public static final String ENDPOINT_UPDATE_PROFILE = "/user/{id}/update";
    public static final String ENDPOINT_SAVE_STORY = "/story/{id}/bookmark";

    private ApiEndPoint() {

    }
}
