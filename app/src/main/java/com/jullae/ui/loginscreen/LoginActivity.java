package com.jullae.ui.loginscreen;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.helpers.InputValidation;
import com.jullae.sql.DatabaseHelper;
import com.jullae.ui.homefeed.HomeActivity;
import com.jullae.utils.ErrorResponseModel;
import com.jullae.utils.dialog.MyProgressDialog;

import org.json.JSONObject;

/**
 * Class used to login into the app through credentials.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, LoginActivityView {

    //private final AppCompatActivity activity = LoginActivity.this;

    //Google
    private static final String TAG = "SignInActivity";
    private static final int RC_GOOGLE_SIGN_IN = 9001;

    //Facebook
    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;

    private InputValidation inputValidation;
    //TextView testViewLogin;
    private DatabaseHelper databaseHelper;
    private GoogleApiClient mGoogleApiClient;
    private LoginActivityPresentor mPresentor;
    private int emailLoginMode;
    private String password, email;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mPresentor = new LoginActivityPresentor(((AppController) getApplication()).getmAppDataManager());
        if (mPresentor.isUserLoggedIn()) {
            startHomeActivity();
        }

        mPresentor.attachView(this);
        // loadFontsOnStartUp();

        showFragment(new LoginFragment(), false);

        initViews();
        //initListeners();
        //initObjects();

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
        });

        //Google
        //testViewLogin = (TextView) findViewById(R.id.testViewForLoginGoogle);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this *//* FragmentActivity *//*, this *//* OnConnectionFailedListener *//*)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();*/

        //findViewById(R.id.google_login).setOnClickListener(this);
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

    /**
     * Initialize the views.
     */
    private void initViews() {

        fbLoginButton = findViewById(R.id.facebook_login);


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

    private void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);

    }

    /**
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
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.google_login:
                googleSignIn();
                break;
            case R.id.button_login:
                //verifyFromSQLite();
                break;
            case R.id.sign_up:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.forgot_password:
                Intent intentForgotPassword = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intentForgotPassword);
                break;
            default:
                break;
        }
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * google signup.
     *
     * @param result model
     */
    private void handleSignInResult(final GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            //GoogleSignInAccount acct = result.getSignInAccount();
            //testViewLogin.setText(getString(R.string.tempSignInGoogleTestString, acct.getDisplayName()));
            // updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }


    @Override
    public void onConnectionFailed(final ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    /**
     * Adding custom font "berkshireswash" to the app title
     * Adding custom font "Lato-Light" to all text on login screen
     */
    public void loadFontsOnStartUp() {

        Typeface customFontNunitoRegular = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Regular.ttf");
        Typeface customFontBerkshire = Typeface.createFromAsset(getAssets(), "fonts/berkshireswash-regular.ttf");

        TextView appName = findViewById(R.id.app_name);
        appName.setTypeface(customFontBerkshire);

        Button fbLogin = findViewById(R.id.facebook_login);
        fbLogin.setTypeface(customFontNunitoRegular);

        Button googleLogin = findViewById(R.id.google_login);
        googleLogin.setTypeface(customFontNunitoRegular);

        Button loginButton = findViewById(R.id.button_login);
        loginButton.setTypeface(customFontNunitoRegular);

        // forgotPasswordTextView = (TextView) findViewById(R.id.forgot_password);
        //forgotPasswordTextView.setTypeface(customFontNunitoRegular);

        TextView signUpTextView = findViewById(R.id.sign_up);
        signUpTextView.setTypeface(customFontNunitoRegular);

        TextView termsConditionsTextView = findViewById(R.id.terms_and_conditons);
        termsConditionsTextView.setTypeface(customFontNunitoRegular);
    }

    public void performEmailLogin(String email, String password, int emailLoginMode) {
        mPresentor.performEmailLogin(email, password, emailLoginMode);
    }

    public void performSignUp(String name, String penname, String bio) {
        mPresentor.performSignUp(email, password, name, penname, bio);
    }

    @Override
    protected void onDestroy() {
        mPresentor.detachView();
        super.onDestroy();
    }
}
