package com.jullae.ui.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.jullae.ApplicationClass;
import com.jullae.R;
import com.jullae.ui.home.HomeActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.ErrorResponseModel;
import com.jullae.utils.InputValidation;
import com.jullae.utils.dialog.MyProgressDialog;

import org.json.JSONObject;

import java.util.Arrays;

import static com.jullae.ui.loginscreen.LoginFragment.RC_SIGN_IN;

/**
 * Class used to login into the app through credentials.
 */
public class LoginActivity extends AppCompatActivity implements
        LoginActivityView {

    //private final AppCompatActivity activity = LoginActivity.this;

    public static final int MODE_SIGNUP_EMAIL = 23;
    public static final int MODE_GOOGLE = 25;
    //Google
    private static final String TAG = "SignInActivity";
    private static final int RC_GOOGLE_SIGN_IN = 9001;
    //Facebook
    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;

    private InputValidation inputValidation;
    //TextView testViewLogin;
    private GoogleApiClient mGoogleApiClient;
    private LoginActivityPresentor mPresentor;
    private int emailLoginMode;
    private String password, email;
    private GoogleSignInClient mGoogleSignInClient;
    private int loginMode;
    private String google_idToken;
    private String google_name, google_email, google_photoUrl;
    private String user_id;
    private String token;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mPresentor = new LoginActivityPresentor(((ApplicationClass) getApplication()).getmAppDataManager());
        if (mPresentor.isUserLoggedIn()) {
            startHomeActivity();
        }

        mPresentor.attachView(this);
        // loadFontsOnStartUp();

        showFragment(new LoginFragment(), false);

        setUpFbLogin();

/*
        callbackManager = CallbackManager.Factory.create();

        //Facebook
        LoginButton fbLoginButton = (LoginButton) findViewById(R.id.facebook_login);
        fbLoginButton.setReadPermissions("email");
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(final FacebookException exception) {
                // App code
            }
        });*/
/*
        Google
      testViewLogin = (TextView) findViewById(R.id.testViewForLoginGoogle);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

       findViewById(R.id.google_login).setOnClickListener(this);*/

    }

    private void setUpFbLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getToken());
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
           /* if (b)
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
           */
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
        setLoginMode(MODE_SIGNUP_EMAIL);
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
    public void onSignUpFail() {
        Toast.makeText(getApplicationContext(), "couldn't connect!", Toast.LENGTH_SHORT).show();

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

    private void startHomeActivity() {

        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    /**
     * This method is to initialize objects to be used
     * <p>
     * /**
     * Method used to get the result after successful login
     * of the user .
     *
     * @param loginResult login model after result.
     */
    protected void getUserDetails(final LoginResult loginResult) {

        /*TextView fbTv = (TextView) findViewById(R.id.testViewForLoginGoogle);
        fbTv.setText("Facebook Login successful!!!!!!");*/

        Intent fbIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(fbIntent);

        GraphRequest dataRequest = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(final JSONObject jsonObject, final GraphResponse response) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("userProfile", jsonObject.toString());
                        startActivity(intent);
                    }

                });
            /*Bundle permission_param = new Bundle();
            permission_param.putString("fields", "id,name,email,
                    picture.width(120).height(120)");
                    dataRequest.setParameters(permission_param);
            dataRequest.executeAsync();*/

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

            setLoginMode(MODE_GOOGLE);


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


    public void performEmailLogin(String email, String password, int emailLoginMode) {
        mPresentor.performEmailLogin(email, password, emailLoginMode);
    }

    public void performSignUp(String name, String penname) {
        mPresentor.performSignUp(email, password, name, penname, loginMode);
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
}
