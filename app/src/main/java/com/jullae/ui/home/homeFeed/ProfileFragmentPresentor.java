package com.jullae.ui.home.homeFeed;

import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.AvatarResponseModel;
import com.jullae.data.db.model.ConversationModel;
import com.jullae.data.db.model.ProfileMainModel;
import com.jullae.data.db.model.ProfileModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.ui.home.homeFeed.freshfeed.ProfileFragmentView;
import com.jullae.utils.NetworkUtils;
import com.jullae.utils.ReqListener;

import java.io.File;

public class ProfileFragmentPresentor extends BasePresentor<ProfileFragmentView> {

    private static final String TAG = ProfileFragmentPresentor.class.getName();

    public ProfileFragmentPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public ProfileModel getStaticUserData() {
        return getmAppDataManager().getmAppPrefsHelper().getPrefsUserData();
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

    public void makeDpReq(File file) {
        checkViewAttached();
        getMvpView().showPicUploadProgress();
        getmAppDataManager().getmApiHelper().makeDpChangeReq(getmAppDataManager().getmAppPrefsHelper().getKeyUserId(), file)
                /*.getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                }); */.getAsObject(AvatarResponseModel.class, new ParsedRequestListener<AvatarResponseModel>() {


            @Override
            public void onResponse(AvatarResponseModel avatarResponseModel) {
                NetworkUtils.parseResponse(TAG, avatarResponseModel);
                if (avatarResponseModel.isReqSuccess()) {
                    getmAppDataManager().getmAppPrefsHelper().setKeyDpUrl(avatarResponseModel.getProfile_dp_url());
                    if (isViewAttached()) {
                        getMvpView().hidePicUploadProgress();
                        getMvpView().onProfilePicUpdateSuccess(avatarResponseModel.getProfile_dp_url());
                    }
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

    public void getConversationList() {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().getConversationList().getAsObject(ConversationModel.class, new ParsedRequestListener<ConversationModel>() {

            @Override
            public void onResponse(ConversationModel conversationModel) {
                if (isViewAttached()) {
                    getMvpView().onConversationListFetchSuccess(conversationModel.getConversationList());
                }
            }

            @Override
            public void onError(ANError anError) {
                if (isViewAttached()) {
                    getMvpView().onConversationListFetchFail();

                }
            }
        });
    }

    public void updateProfile(final String name, final String bio, final ReqListener reqListener) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(bio)) {
            checkViewAttached();
            getMvpView().showProgressBar();
            getmAppDataManager().getmApiHelper().updateProfileReq(name, bio, getmAppDataManager().getmAppPrefsHelper().getKeyUserId())
                    .getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {
                        @Override
                        public void onResponse(BaseResponseModel response) {
                            NetworkUtils.parseResponse(TAG, response);
                            if (response.isReqSuccess()) {
                                getmAppDataManager().getmAppPrefsHelper().setKeyName(name);
                                getmAppDataManager().getmAppPrefsHelper().setKeyBio(bio);
                                if (isViewAttached()) {
                                    getMvpView().hideProgressBar();
                                    reqListener.onSuccess();
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            NetworkUtils.parseError(TAG, anError);
                            if (isViewAttached()) {
                                getMvpView().hideProgressBar();
                                reqListener.onFail();
                            }

                        }
                    });
        }
    }
}
