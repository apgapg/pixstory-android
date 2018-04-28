package com.jullae.ui.home;

import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.NetworkUtils;

import java.io.File;

public class HomeActivityPresentor extends BasePresentor<HomeActivityView> {
    private static final String TAG = HomeActivityPresentor.class.getName();

    public HomeActivityPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void submitPicture(String title, File file, final HomeActivity.AddPictureListener addPictureListener) {
        checkViewAttached();
        getMvpView().showProgressBar();
        if (!TextUtils.isEmpty(title)) {
            getmAppDataManager().getmApiHelper().makeUploadPictureReq(title, file).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {

                @Override
                public void onResponse(BaseResponseModel response) {
                    NetworkUtils.parseResponse(TAG, response);
                    if (isViewAttached()) {
                        getMvpView().hideProgressBar();
                        addPictureListener.closeDialog();
                    }

                }

                @Override
                public void onError(ANError anError) {
                    NetworkUtils.parseError(TAG, anError);
                    if (isViewAttached()) {
                        getMvpView().hideProgressBar();
                    }
                }
            });
        } else addPictureListener.onPictureTitleEmpty();
    }
}
