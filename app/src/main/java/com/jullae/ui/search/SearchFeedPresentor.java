package com.jullae.ui.search;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.FreshFeedModel;
import com.jullae.data.db.model.TagSearchMainModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

import java.util.List;

public class SearchFeedPresentor extends BasePresentor<SearchFeedContract.View> {


    private static final String TAG = SearchFeedPresentor.class.getName();
    private SearchFeedContract.View view;

    public SearchFeedPresentor() {
    }

    public void loadFeeds(String searchTag) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().loadsearchfeeds(searchTag).getAsObject(TagSearchMainModel.class, new ParsedRequestListener<TagSearchMainModel>() {


            @Override
            public void onResponse(TagSearchMainModel tagSearchMainModel) {
                NetworkUtils.parseResponse(TAG, tagSearchMainModel);
                if (isViewAttached()) {
                    getMvpView().onFetchFeedsSuccess(tagSearchMainModel.getFeedModelList());

                }

            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseResponse(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onFetchFeedsFail();
                }
            }
        });
    }

    public void setView(SearchFeedContract.View view) {
        this.view = view;
    }

    public void removeView() {
        view = null;
    }

    public interface FeedListener {
        void onSuccess(List<FreshFeedModel> list);

        void onFail();
    }
}
