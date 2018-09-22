package com.jullae.data.network;

final class ApiEndPoint {

    static final String ENDPOINT_HOME_FEEDS = "/home/feed";
    static final String ENDPOINT_FRESH_FEEDS = "/discover/new";
    static final String ENDPOINT_POPULAR_FEEDS = "/discover/popular";
    static final String ENDPOINT_ARCHIVED_FEEDS = "/discover/archived";
    static final String ENDPOINT_SEARCH_TAG = "/search/tagged_stories";
    static final String ENDPOINT_LIKE_STORY_URL = "/story/{id}/like";
    static final String ENDPOINT_UNLIKE_STORY_URL = "/story/{id}/unlike";
    static final String ENDPOINT_LIKE_PICTURE_URL = "/picture/{id}/like";
    static final String ENDPOINT_UNLIKE_PICTURE_URL = "/picture/{id}/unlike";
    static final String ENDPOINT_PICTURE_LIKES_LIST = "/picture/{id}/likes";
    static final String ENDPOINT_STORY_LIKES_LIST = "/story/{id}/likes";
    static final String ENDPOINT_FOLLOW = "/user/{id}/follow";
    static final String ENDPOINT_UNFOLLOW = "/user/{id}/unfollow";
    static final String ENDPOINT_STORY_DETAILS = "/story/{id}/view";
    static final String ENDPOINT_COMMENT = "/story/{id}/comments";
    static final String ENDPOINT_PASSWORD_CHANGE = "/user/{id}/update_password";
    static final String ENDPOINT_UPDATE_EDIT_STORY = "/story/{id}/update";
    static final String ENDPOINT_ADD_STORY = "/discover/nonstory_pictures";
    static final String ENDPOINT_FOLLOWERS_LIST = "/user/{id}/followers_list";
    static final String ENDPOINT_FOLLOWINGS_LIST = "/user/{id}/followings_list";
    static final String ENDPOINT_STORY_DELETE = "/story/{id}/destroy";
    static final String ENDPOINT_PICTURE_DELETE = "/picture/{id}/destroy";

    static final String ENDPOINT_EMAIL_SIGNUP = "/custom_registration";
    static final String ENDPOINT_EMAIL_LOGIN = "/custom_authentication";
    static final String ENDPOINT_GOOGLE_LOGIN = "/google_authentication";
    static final String ENDPOINT_FACEBOOK_LOGIN = "/facebook_authentication";
    static final String ENDPOINT_FORGOT_PASSWORD = "/reset_password_request";
    static final String ENDPOINT_PICTURE_DETAIL = "/auth_misc/pic_story";

    static final String ENDPOINT_PROFILE_TAB_PICTURES = "/profile/{penname}/pictures";
    static final String ENDPOINT_PROFILE_TAB_STORIES = "/profile/{penname}/stories";
    static final String ENDPOINT_PROFILE_TAB_BOOKMARKS = "/profile/{penname}/bookmarks";
    static final String ENDPOINT_PROFILE_PIC_UPDATE = "/user/{id}/upload_dp";
    static final String ENDPOINT_WRITE_COMMENTS = "/comment/publish";
    static final String ENDPOINT_REPORT_STORY = "/report";
    static final String ENDPOINT_DRAFTS = "/profile/{penname}/drafts";
    static final String ENDPOINT_PROFILE_VISITOR_INFO = "/profile/{penname}/info";

    static final String ENDPOINT_CONVERSATION_LIST = "/user/conversations";
    static final String ENDPOINT_MESSAGE_LIST = "/user/{id}/messages";
    static final String ENDPOINT_ADD_MESSAGE = "/user/{id}/message";
    static final String ENDPOINT_TAG_SUGGESTIONS = "/suggestion/tag_suggestions";
    static final String ENDPOINT_PEOPLE_SUGGESTIONS = "/suggestion/name_suggestions";
    static final String ENDPOINT_PUBLISH_STORY = "/story/publish";
    static final String ENDPOINT_PUBLISH_DRAFT = "/story/draft";
    static final String ENDPOINT_DELETE_STORY = "/story/{id}/destroy";
    static final String ENDPOINT_DELETE_BOOKMARK = "/story/{id}/remove_bookmark";
    static final String ENDPOINT_DELETE_COMMENT = "/comment/{id}/delete";

    static final String ENDPOINT_UPDATE_PROFILE = "/user/{id}/update";
    static final String ENDPOINT_SAVE_STORY = "/story/{id}/bookmark";

    static final String ENDPOINT_VIEW_ALL_STORIES = "/discover/other_stories";
    static final String ENDPOINT_UPLOAD_PICTURE = "/picture/upload";

    static final String ENDPOINT_NOTIFICATION_LIST = "/user/{id}/notifications";
    static final String ENDPOINT_NOTIFICATION_READ_STATUS = "/user/{id}/read_notifications";
    static final String ENDPOINT_ADD_PROFILE_DETAILS = "/auth_misc/complete_account_setup";

    static final String ENDPOINT_WRITESTORY_CATEGORY= "/story/categories";


    private ApiEndPoint() {

    }
}
