package com.jullae.ui.home.profile.profileVisitor;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.ProfileMainModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class ProfileVisitorPresentor extends BasePresentor<ProfileVisitorView> {

    private static final String TAG = ProfileVisitorPresentor.class.getName();

    public ProfileVisitorPresentor() {
    }


    public void loadProfile(String penname) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().fetchVisitorProfileData(penname).getAsObject(ProfileMainModel.class, new ParsedRequestListener<ProfileMainModel>() {
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
