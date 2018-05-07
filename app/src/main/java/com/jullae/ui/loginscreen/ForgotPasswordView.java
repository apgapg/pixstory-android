package com.jullae.ui.loginscreen;

import com.jullae.ui.base.MvpView;

public interface ForgotPasswordView extends MvpView {
    void showProgressBar();

    void hideProgressBar();

    void onForgotPasswordReqSuccess();

    void onEmailValidationFail();

    void onForgotPasswordReqFail(String message);
}
