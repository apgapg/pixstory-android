package com.jullae.ui.home.profile.draftTab;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.DraftModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.NetworkUtils;

public class DraftTabPresentor extends BasePresentor<DraftTabView> {
    private static final String TAG = DraftTabPresentor.class.getName();

    public DraftTabPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void loadDrafts() {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().getDraftList(getmAppDataManager().getmAppPrefsHelper().getKeyPenname()).getAsObject(DraftModel.class, new ParsedRequestListener<DraftModel>() {
            @Override
            public void onResponse(DraftModel draftModel) {
                NetworkUtils.parseResponse(TAG, draftModel);
                if (isViewAttached()) {
                    getMvpView().onDraftsFetchSuccess(draftModel.getList());
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onDraftsFetchFail();
                }
            }
        });
    }

    public void sendDeleteDraftReq(String story_id, final DraftTabAdapter.DeleteListener deleteListener) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().makeDraftDeleteReq(story_id).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {


            @Override
            public void onResponse(BaseResponseModel response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached()) {
                    if (response.isReqSuccess())
                        deleteListener.onSuccess();
                    else deleteListener.onFail();

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
