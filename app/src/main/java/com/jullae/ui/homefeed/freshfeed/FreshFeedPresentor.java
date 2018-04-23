package com.jullae.ui.homefeed.freshfeed;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.helpers.AppDataManager;
import com.jullae.model.FreshFeedModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

import java.util.List;

public class FreshFeedPresentor extends BasePresentor<FreshFeedContract.View> {


    private static final String TAG = FreshFeedPresentor.class.getName();

    public FreshFeedPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void loadFeeds(int position) {
        checkViewAttached();
        getMvpView().showProgressBar();
        getmAppDataManager().getmApiHelper().loadFreshFeeds(position).getAsObject(FreshFeedModel.class, new ParsedRequestListener<FreshFeedModel>() {
            @Override
            public void onResponse(FreshFeedModel freshFeedModel) {
                NetworkUtils.parseResponse(TAG, freshFeedModel);
                if (isViewAttached()) {
                    getMvpView().hideProgressBar();
                    getMvpView().onFetchFeeds(freshFeedModel.getList());
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().hideProgressBar();
                    getMvpView().onFetchFeedsFail();
                }
            }
        });
    }


}
