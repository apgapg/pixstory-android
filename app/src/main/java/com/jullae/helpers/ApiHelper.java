package com.jullae.helpers;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jullae.utils.Constants;
import com.jullae.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.HashMap;

import static com.jullae.utils.Constants.BASE_URL;

/**
 * Created by master on 1/4/18.
 */

public class ApiHelper {

    private static final String TAG = ApiHelper.class.getName();
    private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MjY4NDEzMDIsImlhdCI6MTUyNDI0OTMwMiwiaXNzIjoianVsbGFlLmNvbSIsInNjb3BlcyI6W10sInVzZXIiOnsidXNlcl9pZCI6OH19.eYrtdL9x5rOGzMNLWtVNVE9BsII6XqoAL8cR9FIym1k";
    private final HashMap<String, String> headers;

    public ApiHelper() {
        headers = new HashMap<>();
        headers.put("Authorization", TOKEN);
    }

    public ANRequest loadFreshFeeds(int position) {
        String end_point;
        switch (position) {
            case 0:
                end_point = Constants.FRESH_FEEDS;
                break;
            case 1:
                end_point = Constants.POPULAR_FEEDS;
                break;
            case 2:
                end_point = Constants.ARCHIVED_FEEDS;
                break;
            case 3:
                end_point = Constants.FRESH_FEEDS;
                break;
            default:
                end_point = Constants.FRESH_FEEDS;
        }
        return AndroidNetworking.get(BASE_URL + end_point)
                .addHeaders(headers)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")*/
                .setPriority(Priority.HIGH)
                .build();
    }

    public void loadsearchfeeds(final ReqListener reqListener) {
        AndroidNetworking.get(BASE_URL + Constants.ARCHIVED_FEEDS)
                .addHeaders(headers)
                .addQueryParameter("term", "karakoram")
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")*/
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        NetworkUtils.parseResponse(TAG, response);
                        reqListener.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        NetworkUtils.parseError(TAG, anError);
                        reqListener.onFail();
                    }
                });
    }

    public ANRequest loadHomeFeeds() {
        return AndroidNetworking.get(BASE_URL + Constants.HOME_FEEDS)
                .addHeaders(headers)
                .addQueryParameter("term", "karakoram")
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")*/
                .setPriority(Priority.HIGH)
                .build();
    }

    public ANRequest setlikeReq(String id, String isLiked, int likeType) {
        String url;
        if (isLiked.equals("false")) {
            if (likeType == Constants.LIKE_TYPE_PICTURE)
                url = Constants.LIKE_PICTURE_URL;
            else url = Constants.LIKE_STORY_URL;
        } else {
            if (likeType == Constants.LIKE_TYPE_PICTURE)
                url = Constants.UNLIKE_PICTURE_URL;
            else url = Constants.UNLIKE_STORY_URL;
        }

        return AndroidNetworking.post(BASE_URL + url)
                .addHeaders(headers)
                .addPathParameter("id", id)
                .setPriority(Priority.HIGH)
                .build();
    }

    public ANRequest getLikesList(String id, int likesListType) {

        String endpoint = null;
        if (likesListType == Constants.LIKE_TYPE_STORY)
            endpoint = Constants.STORY_LIKES_LIST;
        else if (likesListType == Constants.LIKE_TYPE_PICTURE)
            endpoint = Constants.PICTURE_LIKES_LIST;


        return AndroidNetworking.get(BASE_URL + endpoint)
                .addHeaders(headers)
                .addPathParameter("id", id)
                .setPriority(Priority.HIGH)
                .build();


    }

    public ANRequest makeFollowReq(String user_id) {
        return AndroidNetworking.post(BASE_URL + Constants.FOLLOW)
                .addHeaders(headers)
                .addPathParameter("id", user_id)
                .setPriority(Priority.HIGH)
                .build();


    }

    public ANRequest loadComments(String story_id) {
        return AndroidNetworking.get(BASE_URL + Constants.COMMENTS)
                .addHeaders(headers)
                .addPathParameter("id", story_id)
                .setPriority(Priority.HIGH)
                .build();
    }

    public ANRequest sendCommentReq(String comment, String story_id) {
        return AndroidNetworking.post(BASE_URL + Constants.WRITE_COMMENTS)
                .addHeaders(headers)
                .addBodyParameter("comment", comment)
                .addBodyParameter("story_id", story_id)
                .setPriority(Priority.HIGH)
                .build();
    }

    public ANRequest reportStory(String report, String id, int reportTypeStory) {
        String url = null;
        String reportType = null;
        if (reportTypeStory == Constants.REPORT_TYPE_STORY) {
            url = Constants.REPORT_STORY;
            reportType = "Story";

        }

        return AndroidNetworking.post(BASE_URL + url)
                .addHeaders(headers)
                .addBodyParameter("reportable_type", reportType)
                .addBodyParameter("reportable_id", id)
                .addBodyParameter("reason", report)
                .setPriority(Priority.HIGH)
                .build();


    }

    public interface ReqListener {


        void onSuccess(JSONObject response);

        void onFail();

        interface StringReqListener {

            void onSuccess(String string);

            void onFail();
        }
    }


}
