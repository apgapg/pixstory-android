package com.jullae.helpers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jullae.model.FreshFeedModel;
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
        mApiHelper = new ApiHelper(mAppPrefsHelper.getKeyToken());
    }

    public AppPrefsHelper getmAppPrefsHelper() {
        return mAppPrefsHelper;
    }

    public ApiHelper getmApiHelper() {
        return mApiHelper;
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



}
