package com.jullae.helpers;

import com.androidnetworking.AndroidNetworking;
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

    public void loadFreshFeeds(int position, final ReqListener reqListener) {
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
        AndroidNetworking.get(BASE_URL + end_point)
                .addHeaders(headers)
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

               /* .getAsObjectList(FreshFeedModel.class, new ParsedRequestListener<List<FreshFeedModel>>() {


                    @Override
                    public void onResponse(List<FreshFeedModel> response) {
                        NetworkUtils.parseResponse(TAG, response);
                        reqListener.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        NetworkUtils.parseError(TAG, anError);
                        reqListener.onFail();
                    }
                })*/
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

    public void loadHomeFeeds(final ReqListener reqListener) {
        AndroidNetworking.get(BASE_URL + Constants.HOME_FEEDS)
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

    public interface ReqListener {


        void onSuccess(JSONObject response);

        void onFail();
    }
}
