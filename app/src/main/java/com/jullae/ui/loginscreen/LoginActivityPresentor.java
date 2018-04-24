package com.jullae.ui.loginscreen;

import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.helpers.AppDataManager;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.AppUtils;
import com.jullae.utils.ErrorResponseModel;
import com.jullae.utils.NetworkUtils;

import static com.jullae.utils.Constants.SHOW_LOGIN;
import static com.jullae.utils.Constants.SHOW_SIGNUP;

public class LoginActivityPresentor extends BasePresentor<LoginActivityView> {

    private static final String TAG = LoginActivityPresentor.class.getName();

    public LoginActivityPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void performEmailLogin(String email, String password, int emailLoginMode) {
        if (AppUtils.isValidEmail(email)) {
            if (!TextUtils.isEmpty(password)) {
                if (emailLoginMode == SHOW_LOGIN) {
                    getMvpView().showProgress();
                    performEmailLoginReq(email, password);

                } else if (emailLoginMode == SHOW_SIGNUP) {
                    getMvpView().emailValidationSuccess(email, password);
                }
            } else {
                getMvpView().passwordValidationError();
            }
        } else {
            getMvpView().emailValidationError();
        }
    }


    /*{
        "success": true,
            "errorcode": 0,
            "message": "Data Ok.",
            "user_id": x,
            "token": "xxx"
    }*/
    public void performSignUp(final String email, final String password, final String name, final String penname, final String bio) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(penname) || TextUtils.isEmpty(bio)) {
            getMvpView().signUpValidationError();
        } else {
            getMvpView().showProgress();
            getmAppDataManager().getmApiHelper().signUpReq(email, password, name, penname, bio).getAsObject(SignUpResponseModel.class, new ParsedRequestListener<SignUpResponseModel>() {
                @Override
                public void onResponse(SignUpResponseModel signUpResponseModel) {
                    NetworkUtils.parseResponse(TAG, signUpResponseModel);
                    if (signUpResponseModel.isSignUpSuccess()) {
                        getmAppDataManager().getmAppPrefsHelper().saveUserDetails(signUpResponseModel.getAvatar(), signUpResponseModel.getName(), signUpResponseModel.getPenname(), signUpResponseModel.getBio(), signUpResponseModel.getUser_id(), signUpResponseModel.getToken());
                        if (isViewAttached()) {
                            getMvpView().hideProgress();
                            getMvpView().onSignUpSuccess();
                        }
                    } else {
                        getMvpView().onSignUpFail();
                    }

                }

                @Override
                public void onError(ANError anError) {
                    NetworkUtils.parseError(TAG, anError);
                    if (isViewAttached()) {
                        getMvpView().hideProgress();
                        getMvpView().onSignUpFail();
                    }
                }
            });
        }
    }


    private void performEmailLoginReq(final String email, String password) {
        getmAppDataManager().getmApiHelper().emailLoginReq(email, password).getAsObject(LoginResponseModel.class, new ParsedRequestListener<LoginResponseModel>() {
            @Override
            public void onResponse(LoginResponseModel loginResponseModel) {
                NetworkUtils.parseResponse(TAG, loginResponseModel);
                if (loginResponseModel.isLoginSuccess()) {
                    getmAppDataManager().getmAppPrefsHelper().saveUserDetails(loginResponseModel.getAvatar(), loginResponseModel.getName(), loginResponseModel.getPenname(), loginResponseModel.getBio(), loginResponseModel.getUser_id(), loginResponseModel.getToken());
                    if (isViewAttached()) {
                        getMvpView().hideProgress();
                        getMvpView().onLoginSuccess();
                    }
                }

            }

            @Override
            public void onError(ANError anError) {

                ErrorResponseModel errorResponseModel = NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().hideProgress();
                    getMvpView().onLoginFail(errorResponseModel);
                }
            }
        });
    }

    public boolean isUserLoggedIn() {
        return getmAppDataManager().getmAppPrefsHelper().getLoggedInMode();
    }
}
