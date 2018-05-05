package com.jullae.ui.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.jullae.R;
import com.jullae.ui.base.BaseFragment;

import java.util.Arrays;

import static com.jullae.utils.Constants.SHOW_LOGIN;
import static com.jullae.utils.Constants.SHOW_SIGNUP;

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    public static final int RC_SIGN_IN = 22;
    private static final String EMAIL = "email";
    private View view;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView forgotPasswordTextView;
    private Button buttonLogin;
    private TextView buttonSignUp;
    private int emailLoginMode = SHOW_LOGIN;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_login, container, false);
        editTextEmail = view.findViewById(R.id.username_field);
        editTextPassword = view.findViewById(R.id.password_field);
        forgotPasswordTextView = view.findViewById(R.id.forgot_password);

        buttonLogin = view.findViewById(R.id.button_login);
        buttonSignUp = view.findViewById(R.id.sign_up);

        buttonSignUp.getPaint().setUnderlineText(true);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getmContext()).performEmailLogin(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim(), emailLoginMode);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailLoginMode == SHOW_LOGIN)
                    updateUI(SHOW_SIGNUP);
                else updateUI(SHOW_LOGIN);
            }
        });
        setUpGoogleSignIn();
        setUpFacebookLogin();

        return view;


    }

    private void setUpFacebookLogin() {
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));


    }

    private void setUpGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getmContext(), gso);

        SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.findViewById(R.id.sign_in_button).setOnClickListener(this);


    }

    private void updateUI(int mode) {
        if (mode == SHOW_SIGNUP) {
            buttonLogin.setText("Sign In");
            forgotPasswordTextView.setVisibility(View.INVISIBLE);
            buttonSignUp.setText(R.string.logIn);
            emailLoginMode = SHOW_SIGNUP;
        } else if (mode == SHOW_LOGIN) {
            buttonLogin.setText("Login");
            forgotPasswordTextView.setVisibility(View.INVISIBLE);
            buttonSignUp.setText(R.string.signUp);
            emailLoginMode = SHOW_LOGIN;

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        getmContext().startActivityForResult(signInIntent, RC_SIGN_IN);

    }
}
