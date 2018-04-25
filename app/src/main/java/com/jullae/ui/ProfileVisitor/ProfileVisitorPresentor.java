package com.jullae.ui.ProfileVisitor;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.helpers.AppDataManager;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.profileSelf.ProfileMainModel;
import com.jullae.utils.NetworkUtils;

public class ProfileVisitorPresentor extends BasePresentor<ProfileVisitorView> {

    private static final String TAG = ProfileVisitorPresentor.class.getName();

    public ProfileVisitorPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }


    public void loadProfile(String penname) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().fetchVisitorProfileData(penname).getAsObject(ProfileMainModel.class, new ParsedRequestListener<ProfileMainModel>() {
            @Override
            public void onResponse(ProfileMainModel profileMainModel) {
                NetworkUtils.parseResponse(TAG, profileMainModel);
                if (isViewAttached()) {
                    getMvpView().onProfileFetchSuccess(profileMainModel.getProfileModel());
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onProfileFetchFail();
                }
            }
        });
    }


}
