package com.jullae.ui.loginscreen;

import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jullae.data.AppDataManager;
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

    private void performEmailLoginReq(final String email, String password) {
        getmAppDataManager().getmApiHelper().emailLoginReq(email, password).getAsObject(LoginResponseModel.class, new ParsedRequestListener<LoginResponseModel>() {
            @Override
            public void onResponse(LoginResponseModel loginResponseModel) {
                NetworkUtils.parseResponse(TAG, loginResponseModel);


                getmAppDataManager().getmAppPrefsHelper().saveUserDetails(loginResponseModel);
                getmAppDataManager().getmApiHelper().updateToken(loginResponseModel.getToken());

                if (isViewAttached()) {
                    getMvpView().hideProgress();

                    getMvpView().onLoginSuccess();
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

    public void performSignUp(final String email, final String password, final String name, final String penname, int loginMode) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(penname)) {
            getMvpView().signUpValidationError();
        } else {
            getMvpView().showProgress();
            getmAppDataManager().getmApiHelper().signUpReq(email, password, name, penname).getAsObject(LoginResponseModel.class, new ParsedRequestListener<LoginResponseModel>() {
                @Override
                public void onResponse(LoginResponseModel loginResponseModel) {
                    NetworkUtils.parseResponse(TAG, loginResponseModel);
                    getmAppDataManager().getmAppPrefsHelper().saveUserDetails(loginResponseModel);
                    getmAppDataManager().getmApiHelper().updateToken(loginResponseModel.getToken());

                    if (isViewAttached()) {
                        getMvpView().hideProgress();
                        getMvpView().onSignUpSuccess();
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

    public void makeFbLoginReq(String token) {
        checkViewAttached();
        getMvpView().showProgress();
        getmAppDataManager().getmApiHelper().makeFbLoginReq(token).getAsObject(LoginResponseModel.class, new ParsedRequestListener<LoginResponseModel>() {

            @Override
            public void onResponse(LoginResponseModel loginResponseModel) {
                NetworkUtils.parseResponse(TAG, loginResponseModel);

                if (loginResponseModel.getAccount_status().equals("act")) {
                    getmAppDataManager().getmAppPrefsHelper().saveUserDetails(loginResponseModel);
                    getmAppDataManager().getmApiHelper().updateToken(loginResponseModel.getToken());

                    if (isViewAttached()) {
                        getMvpView().hideProgress();
                        getMvpView().onLoginSuccess();
                    }
                } else if (loginResponseModel.getAccount_status().equals("incomp")) {
                    if (isViewAttached()) {
                        getMvpView().hideProgress();
                        Log.d(TAG, "onResponse: " + loginResponseModel.getToken());
                        getMvpView().onFacebookSignInSuccess(loginResponseModel.getUser_id(), loginResponseModel.getToken(), loginResponseModel.getEmail());
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

    public void makeGoogleSignInReq(String idToken) {
        checkViewAttached();
        getMvpView().showProgress();
        getmAppDataManager().getmApiHelper().googleSignInReq(idToken).getAsObject(LoginResponseModel.class, new ParsedRequestListener<LoginResponseModel>() {

            @Override
            public void onResponse(LoginResponseModel loginResponseModel) {
                NetworkUtils.parseResponse(TAG, loginResponseModel);
                if (loginResponseModel.getAccount_status().equals("act")) {
                    getmAppDataManager().getmAppPrefsHelper().saveUserDetails(loginResponseModel);
                    getmAppDataManager().getmApiHelper().updateToken(loginResponseModel.getToken());

                    if (isViewAttached()) {
                        getMvpView().hideProgress();
                        getMvpView().onLoginSuccess();
                    }
                } else if (loginResponseModel.getAccount_status().equals("incomp")) {
                    if (isViewAttached()) {
                        getMvpView().hideProgress();
                        Log.d(TAG, "onResponse: " + loginResponseModel.getToken());
                        getMvpView().onGoogleSignInSuccess(loginResponseModel.getUser_id(), loginResponseModel.getToken());
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

    public void addProfileDetails(String user_id, String token, String penname, String email) {
        checkViewAttached();
        if (TextUtils.isEmpty(penname)) {
            getMvpView().signUpValidationError();
        } else {
            getmAppDataManager().getmApiHelper().addProfileDetailReq(user_id, token, penname, email).getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "onResponse: " + response);

                }

                @Override
                public void onError(ANError anError) {
                    NetworkUtils.parseError(TAG, anError);

                }
            });
        }
    }

    public boolean isUserLoggedIn() {
        return getmAppDataManager().getmAppPrefsHelper().getLoggedInMode();
    }

}
