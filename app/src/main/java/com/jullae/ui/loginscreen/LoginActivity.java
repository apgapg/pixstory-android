package com.jullae.ui.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.jullae.R;
import com.jullae.ui.home.HomeActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.ErrorResponseModel;
import com.jullae.utils.MyProgressDialog;

import java.io.File;
import java.util.Arrays;

import static com.jullae.ui.loginscreen.LoginFragment.RC_SIGN_IN;

/**
 * Class used to login into the app through credentials.
 */
public class LoginActivity extends AppCompatActivity implements LoginActivityView {

    public static final int LOGIN_MODE_SIGNUP_EMAIL = 23;
    public static final int LOGIN_MODE_GOOGLE = 25;
    public static final int LOGIN_MODE_FACEBOOK = 26;

    private static final String TAG = LoginActivity.class.getName();
    public static Boolean isUserSignedUp = false;
    private CallbackManager callbackManager;
    private LoginActivityPresentor mPresentor;
    private String password, email;
    private int loginMode;
    private String google_idToken;
    private String google_name, google_email, google_photoUrl;
    private String user_id;
    private String token;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mPresentor = new LoginActivityPresentor();

        if (isUserLoggedIn()) {
            startHomeActivity();
        }

        mPresentor.attachView(this);

        showFragment(new LoginFragment(), false);

        setUpFbLogin();


    }

    private boolean isUserLoggedIn() {
        String provider = mPresentor.getLoggedInProvider();
        if (provider.equals("facebook")) {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            return accessToken != null && !accessToken.isExpired();
        } else return mPresentor.isUserLoggedIn();
    }

    private void setUpFbLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getToken());
                        setLoginMode(LOGIN_MODE_FACEBOOK);
                        mPresentor.makeFbLoginReq(loginResult.getAccessToken().getToken());
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d(TAG, exception.toString());
                        Toast.makeText(LoginActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void showFragment(Fragment fragment, boolean shouldAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        if (shouldAddToBackStack)
            fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
        else fragmentTransaction.replace(R.id.container, fragment).commit();
    }


    @Override
    public void emailValidationError() {
        Toast.makeText(getApplicationContext(), "Please check your Email!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void passwordValidationError() {
        Toast.makeText(getApplicationContext(), "Password cannot be Empty!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void emailValidationSuccess(String email, String password) {
        this.email = email;
        this.password = password;
        setLoginMode(LOGIN_MODE_SIGNUP_EMAIL);
        showFragment(new SignUpFragment(), true);
    }

    @Override
    public void signUpValidationError() {
        Toast.makeText(getApplicationContext(), "Please fill all details!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(getApplicationContext(), "SignUp successful!", Toast.LENGTH_SHORT).show();
        startHomeActivity();
    }

    @Override
    public void onSignUpFail(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
        startHomeActivity();
    }


    @Override
    public void onLoginFail(ErrorResponseModel errorResponseModel) {
        Toast.makeText(getApplicationContext(), errorResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        MyProgressDialog.showProgressDialog(this, "Please Wait!");
    }

    @Override
    public void hideProgress() {
        MyProgressDialog.dismissProgressDialog();
    }

    @Override
    public void onGoogleSignInSuccess(String user_id, String token) {
        this.user_id = user_id;
        this.token = token;
        Bundle bundle = new Bundle();
        bundle.putString("name", google_name);
        bundle.putString("email", google_email);
        bundle.putString("photo", google_photoUrl);

        bundle.putInt("loginmode", loginMode);
        SignUpFragment signUpFragment = new SignUpFragment();
        signUpFragment.setArguments(bundle);
        showFragment(signUpFragment, true);
    }

    @Override
    public void onFacebookSignInSuccess(String user_id, String token, String email) {
        this.user_id = user_id;
        this.token = token;
        Bundle bundle = new Bundle();
        bundle.putString("email", email);

        bundle.putInt("loginmode", loginMode);
        SignUpFragment signUpFragment = new SignUpFragment();
        signUpFragment.setArguments(bundle);
        showFragment(signUpFragment, true);
    }

    private void startHomeActivity() {
        isUserSignedUp = true;
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Log.d(TAG, "handleSignInResult: " + account.getDisplayName());


            this.google_idToken = account.getIdToken();
            this.google_name = account.getDisplayName();
            this.google_email = account.getEmail();
            this.google_photoUrl = String.valueOf(account.getPhotoUrl());

            setLoginMode(LOGIN_MODE_GOOGLE);


            /**/
            mPresentor.makeGoogleSignInReq(google_idToken);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
            //updateUI(null);
        }
    }

    public void showForgotPasswordFragment() {
        showFragment(new ForgotPasswordFragment(), true);
    }

    public void performEmailLogin(String email, String password, int emailLoginMode) {
        mPresentor.performEmailLogin(email, password, emailLoginMode);
    }

    public void performSignUp(String name, String penname, String bio, File imageFile) {
        mPresentor.performSignUp(email, password, name, penname, bio, loginMode, imageFile);
    }

    @Override
    protected void onDestroy() {
        mPresentor.detachView();
        super.onDestroy();
    }

    public void setLoginMode(int loginMode) {
        this.loginMode = loginMode;
    }

    public void addProfileDetails(String penname, String email) {
        Log.d(TAG, "addProfileDetails: " + user_id);
        AppUtils.checkforNull(Arrays.asList(user_id, token));
        mPresentor.addProfileDetails(user_id, token, penname, email);
    }

    public void logoutUser() {
        Log.d(TAG, "logoutUser: called");
        if (!isUserSignedUp) {
            LoginManager.getInstance().logOut();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
