package com.jullae.ui.profileSelf.draftTab;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.model.DraftListModel;
import com.jullae.helpers.AppDataManager;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class DraftTabPresentor extends BasePresentor<DraftTabView> {
    private static final String TAG = DraftTabPresentor.class.getName();

    public DraftTabPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void loadDrafts() {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().getDraftList(getmAppDataManager().getmAppPrefsHelper().getKeyPenname()).getAsObject(DraftListModel.class, new ParsedRequestListener<DraftListModel>() {
            @Override
            public void onResponse(DraftListModel draftListModel) {
                NetworkUtils.parseResponse(TAG, draftListModel);
                if (isViewAttached()) {
                    // getMvpView().onDraftsFetchSuccess(draftListModel.getList());
                }
            }

            @Override
            public void onError(ANError anError) {
                if (isViewAttached()) {
                    getMvpView().onDraftsFetchFail();
                }
            }
        });
    }
}
