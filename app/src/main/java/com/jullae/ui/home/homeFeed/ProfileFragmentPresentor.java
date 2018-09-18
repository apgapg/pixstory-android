package com.jullae.ui.home.homeFeed;

import android.util.Log;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.AvatarResponseModel;
import com.jullae.data.db.model.ConversationModel;
import com.jullae.data.db.model.ProfileMainModel;
import com.jullae.data.db.model.ProfileModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.ui.fragments.ProfileFragment;
import com.jullae.ui.home.homeFeed.freshfeed.ProfileFragmentView;
import com.jullae.utils.Constants;
import com.jullae.utils.NetworkUtils;

import java.io.File;

public class ProfileFragmentPresentor extends BasePresentor<ProfileFragmentView> {

    private static final String TAG = ProfileFragmentPresentor.class.getName();

    public ProfileFragmentPresentor() {
    }

    public ProfileModel getStaticUserData() {
        return AppDataManager.getInstance().getmSharedPrefsHelper().getPrefsUserData();
    }

    public void loadProfile(String penname) {
        checkViewAttached();

        AppDataManager.getInstance().fetchSelfProfile(penname);
       /* AppDataManager.getInstance().getmApiHelper().fetchVisitorProfileData(penname).getAsObject(ProfileMainModel.class, new ParsedRequestListener<ProfileMainModel>() {
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
        });*/
    }

    public void makeDpReq(File file) {
        checkViewAttached();
        getMvpView().showPicUploadProgress();
        AppDataManager.getInstance().getmApiHelper().makeDpChangeReq(AppDataManager.getInstance().getmSharedPrefsHelper().getKeyUserId(), file)
                .getAsObject(AvatarResponseModel.class, new ParsedRequestListener<AvatarResponseModel>() {


                    @Override
                    public void onResponse(AvatarResponseModel avatarResponseModel) {
                        NetworkUtils.parseResponse(TAG, avatarResponseModel);
                        AppDataManager.getInstance().getmSharedPrefsHelper().setKeyDpUrl(avatarResponseModel.getProfile_dp_url());
                        if (isViewAttached()) {
                            getMvpView().hidePicUploadProgress();
                            getMvpView().onProfilePicUpdateSuccess(avatarResponseModel.getProfile_dp_url());
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        NetworkUtils.parseError(TAG, anError);
                        if (isViewAttached()) {
                            getMvpView().hidePicUploadProgress();
                            getMvpView().onProfilePicUpdateFail();
                        }
                    }
                });
    }


    public boolean isEmailModeLogin() {
        Log.d(TAG, "isEmailModeLogin: " + AppDataManager.getInstance().getmSharedPrefsHelper().getKeyProvider());
        return AppDataManager.getInstance().getmSharedPrefsHelper().getKeyProvider().equals(Constants.PROVIDER_EMAIL);
    }

    public void makePasswordChange(String oldpassword, String newpassword, final ProfileFragment.PasswordChangeListener passwordChangeListener) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().makePasswordChangeReq(oldpassword, newpassword, AppDataManager.getInstance().getmSharedPrefsHelper().getKeyUserId()).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {

            @Override
            public void onResponse(BaseResponseModel response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached())
                    passwordChangeListener.onPasswordChangeSuccess();
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    passwordChangeListener.onPasswordChangeFail();
            }
        });
    }
}
