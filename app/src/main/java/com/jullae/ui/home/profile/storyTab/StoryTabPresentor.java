package com.jullae.ui.home.profile.storyTab;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.StoryListModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class StoryTabPresentor extends BasePresentor<StoryTabView> {
    private static final String TAG = StoryTabPresentor.class.getName();

    public StoryTabPresentor() {
    }

    public void loadFeeds() {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().loadStoryTabFeeds(AppDataManager.getInstance().getmSharedPrefsHelper().getKeyPenname()).getAsObject(StoryListModel.class, new ParsedRequestListener<StoryListModel>() {
            @Override
            public void onResponse(StoryListModel storyListModel) {
                NetworkUtils.parseResponse(TAG, storyListModel);
                if (isViewAttached()) {
                    getMvpView().onStoriesFetchSuccess(storyListModel.getStoryMainModelList());
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onStoriesFetchFail();
                }
            }
        });
    }
}
