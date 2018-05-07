package com.jullae.ui.home.profile.bookmarkTab;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.StoryListModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.ui.home.profile.draftTab.DraftTabAdapter;
import com.jullae.utils.NetworkUtils;

public class BookmarkTabPresentor extends BasePresentor<BookmarkTabView> {
    private static final String TAG = BookmarkTabPresentor.class.getName();

    public BookmarkTabPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void loadFeeds() {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().loadStoryTabFeeds(getmAppDataManager().getmAppPrefsHelper().getKeyPenname()).getAsObject(StoryListModel.class, new ParsedRequestListener<StoryListModel>() {
            @Override
            public void onResponse(StoryListModel storyListModel) {
                NetworkUtils.parseResponse(TAG, storyListModel);
                if (isViewAttached()) {
                    getMvpView().onBookmarkFetchSuccess(storyListModel.getStoryMainModelList());
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onBookmarkFetchFail();
                }
            }
        });
    }

    public void sendDeleteBookmarkReq(String story_id, final DraftTabAdapter.DeleteListener deleteListener) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().makeBookmarkDeleteReq(story_id).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {


            @Override
            public void onResponse(BaseResponseModel response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached()) {

                    deleteListener.onSuccess();

                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    deleteListener.onFail();
                }
            }
        });
    }

}
