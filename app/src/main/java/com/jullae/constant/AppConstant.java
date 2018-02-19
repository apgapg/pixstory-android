package com.jullae.constant;

/**
 * Developer: Saurabh Verma
 * Dated: 19-02-2017.
 */
public interface AppConstant {

    int THREE = 3;
    int TEN = 10;
    int FOURTEEN = 20;
    int EIGHTY = 80;
    int NINTY = 90;
    float FIVE = 0.5f;

    String EXTRA_TITLE = "title";
    String EXTRA_KEY = "key";
    String EXTRA_ID = "id";
    String EXTRA_ITEM_SELECTED_ID = "item_selected_id";

    String ITEM_EDIT = "item_edit";
    String ITEM_LOC = "item_loc";
    String ITEM_USER_NAME = "item_user_name";
    String ITEM_STORY_PIC = "item_story_pic";
    String ITEM_MORE = "item_more";
    String ITEM_USER_PIC = "item_user_pic";
    String NOTIFICATION_RECEIVED = "notification_received";
    String BUNDLE = "bundle";
    String IS_FIRST_TIME_LAUNCH = "is_first_time_launch";
    String LIST = "item_list";
    String ITEM_LIKE = "item_like";
    String FOLLOW = "follow";
    String SPACE_STRING = " ";
    String EXTRA_ITEM_TYPE = "item_type";
    String TAG_COMMENT = "tag_comment";
    String TAG_LIKE = "tag_like";
    String TAG_STORY = "tag_story";
    int HEX_ID = 0x7;
    String TOKEN = "token";
    String AUTH = "authorization";
    String ACCEPT = "accept";


    interface FragmentTags {
        String HOME_FRAGMENT = "HOME_FRAGMENT";
        String PROFILE_FRAGMENT = "PROFILE_FRAGMENT";
        String SHAPE_FRAGMENT = "SHAPE_FRAGMENT";
    }

    interface RequestCodes {
        int FILTER_REQUEST_CODE = 0x1001;
        int REQUEST_CODE_GOOGLE_PLUS = 0x1202;
        int REQUEST_CODE_GOOGLE_SIGN_IN = 0x1203;
        int REQUEST_CODE_ADD_BENEFICIARY = 0x1006;
        int REQUEST_CODE_ADD_ADDRESS = 0x1007;
        int REQUEST_CODE_CHANGE_PASSWORD = 0x1008;
        int REQUEST_CODE_EDIT_PROFILE = 0x1010;
        int REQUEST_CODE_LOCATION_ACTIVITY = 0x1011;
        int REQUEST_CODE_VERIFY_OTP = 0x1012;
        int REQUEST_CODE_FORGET_PASSWORD = 0x1013;
        int REQUEST_CODE_MY_PROFILE = 0x1021;
        int REQUEST_CODE_CONTACT_DETAILS = 0x1022;
        int REQUEST_CODE_MY_ORDERS = 0x1023;
        int REQUEST_CODE_WELCOME = 0x1024;
        int REQUEST_CODE_SIGN_UP = 0x1025;
        int REQUEST_CODE_TRAVEL_DETAILS = 0x1026;
    }

    interface DateConstants {
        String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
        String LOCAL_FORMAT = "yyyy, dd MMM - hh:mm a";
        String END_USER_FORMAT = "dd MMM yyyy";
        String END_USER_FORMAT_WITH_TIME = "dd MMM yyyy hh:mm a";
        String DELIVERY_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
        String PROFILE_TIME_FORMAT = "MMM, dd, yyyy, hh:mm a";
        String DATE_FORMAT = "MMM dd, yyyy";
        String DATE_FORMAT_FROM = "yyyy-MM-dd";
    }

    interface ApiEndPoints {
        //Url's part.
        String API = "/api";
        String V1 = "/v1";
        String ENDPOINTS = API + V1;
        String HOME = "/home";
        String PICTURE = "/picture";
        String USER = "/user";
        String POST = "/post";
        String STORY = "/story";
        String COMMENT = "/comment";

        //Api Names Starts here.
        String LOGIN = ENDPOINTS + "";
        String API_FEEDS = ENDPOINTS + HOME + "/feed";
        String API_PICTURE_LIKE = ENDPOINTS + PICTURE + "/{id}/like";
        String API_PICTURE_UNLIKE = ENDPOINTS + PICTURE + "/{id}/unlike";
        String API_PICTURE_UPLOAD = ENDPOINTS + PICTURE + "/upload";
        String API_ALL_PICTURE_LIKE = ENDPOINTS + PICTURE + "/{id}/all_likes";

        String API_FOLLOW = ENDPOINTS + USER + "/{id}/follow";
        String API_SAVE = ENDPOINTS + POST + "/{id}/save";
        String API_REPORT = ENDPOINTS + PICTURE + "/{id}/report";
        String API_ALL_COMMENTS = ENDPOINTS + STORY + "/{id}/comments";

        String API_ALL_STORY_LIKE = ENDPOINTS + STORY + "/{id}/likes";
        String API_STORY_LIKE = ENDPOINTS + STORY + "/{id}/like";
        String API_STORY_UNLIKE = ENDPOINTS + STORY + "/{id}/unlike";
        String API_STORY_REPORT = ENDPOINTS + STORY + "/{id}/report";
        String API_STORY_SAVE = ENDPOINTS + STORY + "/{id}/save";

        String API_STORY_UPLOAD = ENDPOINTS + STORY + "/upload";
        String API_STORY_PUBLISH = ENDPOINTS + STORY + "/publish";
        String API_STORY_DRAFT = ENDPOINTS + STORY + "/draft";

        String API_COMMENT_UPLOAD = ENDPOINTS + COMMENT + "/upload";

    }
}
