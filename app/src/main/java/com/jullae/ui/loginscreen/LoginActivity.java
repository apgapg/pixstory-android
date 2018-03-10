package com.jullae.ui.loginscreen;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jullae.R;
import com.jullae.helpers.InputValidation;
import com.jullae.sql.DatabaseHelper;
import com.jullae.ui.homefeed.HomeActivity;

import org.json.JSONObject;

/**
 * Class used to login into the app through credentials.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    //private final AppCompatActivity activity = LoginActivity.this;

    //Google
    private static final String TAG = "SignInActivity";
    private static final int RC_GOOGLE_SIGN_IN = 9001;
    //Facebook
    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;
    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private TextView emailErrorTextView;
    private TextView passowrdErrorTextView;
    private TextView forgotPasswordTextView;
    private Button buttonLogin;
    private TextView textViewLinkRegister;
    private InputValidation inputValidation;
    //TextView testViewLogin;
    private DatabaseHelper databaseHelper;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadFontsOnStartUp();

        initViews();
        initListeners();
        initObjects();


        callbackManager = CallbackManager.Factory.create();

        //Facebook
        //LoginButton fbLoginButton = (LoginButton) findViewById(R.id.facebook_login);
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
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //findViewById(R.id.google_login).setOnClickListener(this);
    }

    /**
     * Initialize the views.
     */
    private void initViews() {

        fbLoginButton = (LoginButton) findViewById(R.id.facebook_login);
        textInputEditTextEmail = (EditText) findViewById(R.id.username_field);
        textInputEditTextPassword = (EditText) findViewById(R.id.password_field);
        buttonLogin = (Button) findViewById(R.id.login_button);
        textViewLinkRegister = (TextView) findViewById(R.id.sign_up);
        emailErrorTextView = (TextView) findViewById(R.id.invalidEmailWarning);
        passowrdErrorTextView = (TextView) findViewById(R.id.invalidPasswordWarning);
//        forgotPasswordTextView = (TextView) findViewById(R.id.forgot_password);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        findViewById(R.id.google_login).setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);
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
            case R.id.login_button:
                verifyFromSQLite();
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

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, emailErrorTextView, getString(R.string.error_message_email))
                || !inputValidation.isInputEditTextEmail(textInputEditTextEmail, emailErrorTextView,
                passowrdErrorTextView, getString(R.string.error_message_email))
                || !inputValidation.isInputEditTextFilled(textInputEditTextPassword,
                passowrdErrorTextView, getString(R.string.empty_password))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim(),
                textInputEditTextPassword.getText().toString().trim())) {


            Intent homeFeedIntent = new Intent(this, HomeActivity.class);
            //accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(homeFeedIntent);


        } else {
            // Toast message to show success message that record is wrong
            Toast.makeText(this, R.string.error_valid_email_password, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }

    /**
     * Method used to login by google
     */
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
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    /**
     * Update ui if success else false.
     *
     * @param signedIn true if success else false.
     */
    private void updateUI(final Boolean signedIn) {
        if (signedIn) {
            //findViewById(R.id.login_button).setVisibility(View.GONE);
            Intent googleIntent = new Intent(this, HomeActivity.class);
            startActivity(googleIntent);
        }
        /*else {
            TextView tv = (TextView) findViewById(R.id.testViewForLoginGoogle);
            tv.setText("Login Failed!!!!!!");
        }*/

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

        TextView appName = (TextView) findViewById(R.id.app_name);
        appName.setTypeface(customFontBerkshire);

        Button fbLogin = (Button) findViewById(R.id.facebook_login);
        fbLogin.setTypeface(customFontNunitoRegular);

        Button googleLogin = (Button) findViewById(R.id.google_login);
        googleLogin.setTypeface(customFontNunitoRegular);

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setTypeface(customFontNunitoRegular);

        forgotPasswordTextView = (TextView) findViewById(R.id.forgot_password);
        forgotPasswordTextView.setTypeface(customFontNunitoRegular);

        TextView signUpTextView = (TextView) findViewById(R.id.sign_up);
        signUpTextView.setTypeface(customFontNunitoRegular);

        TextView termsConditionsTextView = (TextView) findViewById(R.id.terms_and_conditons);
        termsConditionsTextView.setTypeface(customFontNunitoRegular);
    }
}
