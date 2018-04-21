package com.jullae.helpers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jullae.model.FreshFeedModel;
import com.jullae.ui.homefeed.HomeFeedModel;
import com.jullae.ui.homefeed.HomeFeedPresentor;
import com.jullae.ui.homefeed.freshfeed.FreshFeedPresentor;
import com.jullae.ui.search.SearchFeedPresentor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by master on 7/4/18.
 */

public class AppDataManager {

    private final AppPrefsHelper mAppPrefsHelper;
    private final ApiHelper mApiHelper;

    public AppDataManager(AppPrefsHelper appPrefsHelper) {
        mAppPrefsHelper = appPrefsHelper;
        mApiHelper = new ApiHelper();

    }

    public AppPrefsHelper getmAppPrefsHelper() {
        return mAppPrefsHelper;
    }

    public ApiHelper getmApiHelper() {
        return mApiHelper;
    }


    public void saveUserDetails(String uid, String authtoken, String number) {
        mAppPrefsHelper.saveUserDetails(uid, authtoken, number);

    }

    public void clear() {
        mAppPrefsHelper.clear();
    }


    public boolean getLoggedInMode() {
        return mAppPrefsHelper.getLoggedInMode();
    }

    public String getUid() {
        return mAppPrefsHelper.getUid();
    }

    public String getAuthToken() {
        return mAppPrefsHelper.getAuthToken();
    }

    public String getNumber() {
        return mAppPrefsHelper.getNumber();
    }

    public void putinsharedprefBoolean(String key, boolean value) {

        mAppPrefsHelper.putinsharedprefBoolean(key, value);
    }

    public Boolean getvaluefromsharedprefBoolean(String key) {
        return mAppPrefsHelper.getvaluefromsharedprefBoolean(key);
    }


    public void loadFreshFeeds(int position, final FreshFeedPresentor.FeedListener feedListener) {
        getmApiHelper().loadFreshFeeds(position, new ApiHelper.ReqListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.serializeNulls();
                    Gson gson = gsonBuilder.create();
                    JSONArray jsonArray = response.getJSONArray("posts");
                    List<FreshFeedModel> freshFeedModels = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        FreshFeedModel freshFeedModel = gson.fromJson(jsonObject.toString(), FreshFeedModel.class);
                        freshFeedModels.add(freshFeedModel);
                    }
                    feedListener.onSuccess(freshFeedModels);
                } catch (JSONException e) {
                    e.printStackTrace();
                    feedListener.onFail();
                }


            }

            @Override
            public void onFail() {
                feedListener.onFail();

            }
        });
    }

    public void loadSearchFeeds(final SearchFeedPresentor.FeedListener feedListener) {
        getmApiHelper().loadsearchfeeds(new ApiHelper.ReqListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.serializeNulls();
                    Gson gson = gsonBuilder.create();
                    JSONArray jsonArray = response.getJSONArray("tagged_stories");
                    List<FreshFeedModel> freshFeedModels = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        FreshFeedModel freshFeedModel = gson.fromJson(jsonObject.toString(), FreshFeedModel.class);
                        freshFeedModels.add(freshFeedModel);
                    }
                    feedListener.onSuccess(freshFeedModels);
                } catch (JSONException e) {
                    e.printStackTrace();
                    feedListener.onFail();
                }
            }

            @Override
            public void onFail() {
                feedListener.onFail();
            }
        });
    }

    public void loadHomeFeeds(final HomeFeedPresentor.FeedFetchListener feedFetchListener) {
        getmApiHelper().loadHomeFeeds(new ApiHelper.ReqListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.serializeNulls();
                    Gson gson = gsonBuilder.create();
                    HomeFeedModel homeFeedModel = gson.fromJson(response.toString(), HomeFeedModel.class);

                    feedFetchListener.onSuccess(homeFeedModel);
                } catch (Exception e) {
                    e.printStackTrace();
                    feedFetchListener.onFail();
                }
            }

            @Override
            public void onFail() {
                feedFetchListener.onFail();
            }
        });
    }
}
