package com.jullae.ui.loginscreen;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.AppUtils;
import com.jullae.utils.ErrorResponseModel;
import com.jullae.utils.NetworkUtils;

public class ForgotPasswordPresentor extends BasePresentor<ForgotPasswordView> {

    private static final String TAG = ForgotPasswordPresentor.class.getName();

    public ForgotPasswordPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void makeForgotPasswordReq(String email) {
        checkViewAttached();
        if (!AppUtils.isValidEmail(email)) {
            getMvpView().onEmailValidationFail();
        } else {
            getMvpView().showProgressBar();
            getmAppDataManager().getmApiHelper().makeForgotPasswordReq(email).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {

                @Override
                public void onResponse(BaseResponseModel response) {
                    NetworkUtils.parseResponse(TAG, response);
                    if (isViewAttached()) {
                        getMvpView().hideProgressBar();
                        getMvpView().onForgotPasswordReqSuccess();
                    }
                }

                @Override
                public void onError(ANError anError) {
                    ErrorResponseModel errorResponseModel = NetworkUtils.parseError(TAG, anError);

                    if (isViewAttached()) {
                        getMvpView().hideProgressBar();

                        getMvpView().onForgotPasswordReqFail(errorResponseModel.getMessage());
                    }
                }
            });
        }
    }
}
