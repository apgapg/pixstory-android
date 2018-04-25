package com.jullae.ui.profileSelf;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.helpers.AppDataManager;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class CommonTabPresentor extends BasePresentor<CommonTabView> {
    private static final String TAG = CommonTabPresentor.class.getName();

    public CommonTabPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void loadFeeds(int position) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().loadStoryTabFeeds(getmAppDataManager().getmAppPrefsHelper().getKeyPenname(), position).getAsObject(StoryListModel.class, new ParsedRequestListener<StoryListModel>() {
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
