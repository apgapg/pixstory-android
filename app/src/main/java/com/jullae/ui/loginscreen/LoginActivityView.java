package com.jullae.ui.loginscreen;

import com.jullae.ui.base.MvpView;
import com.jullae.utils.ErrorResponseModel;

public interface LoginActivityView extends MvpView {
    void emailValidationError();

    void passwordValidationError();

    void emailValidationSuccess(String email, String password);

    void signUpValidationError();

    void onSignUpSuccess();

    void onSignUpFail();

    void onLoginSuccess();

    void onLoginFail(ErrorResponseModel errorResponseModel);

    void showProgress();

    void hideProgress();
}
