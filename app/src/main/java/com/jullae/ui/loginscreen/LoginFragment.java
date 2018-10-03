package com.jullae.ui.loginscreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.jullae.R;
import com.jullae.ui.base.BaseFragment;

import static com.jullae.ui.loginscreen.LoginActivity.isUserSignedUp;
import static com.jullae.utils.Constants.SHOW_LOGIN;
import static com.jullae.utils.Constants.SHOW_SIGNUP;

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    public static final int RC_SIGN_IN = 22;
    private static final String EMAIL = "email";
    private View view;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView button_forgotPassword;
    private Button buttonLogin;
    private TextView buttonSignUp;
    private int emailLoginMode = SHOW_LOGIN;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView loginButton;

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
        button_forgotPassword = view.findViewById(R.id.text_forgot_password);

        buttonLogin = view.findViewById(R.id.button_login);
        buttonSignUp = view.findViewById(R.id.sign_up);


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
        button_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getmContext()).showForgotPasswordFragment();
            }
        });
        setUpGoogleSignIn();
        setUpFacebookLogin();

        return view;


    }

    private void setUpFacebookLogin() {
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity) getmContext()).setUpFbLogin();
            }
        });

    }

    private void setUpGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getmContext(), gso);

       /* SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.findViewById(R.id.sign_in_button).setOnClickListener(this);
*/

        view.findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    private void updateUI(int mode) {
        editTextEmail.setText("");
        editTextPassword.setText("");
        if (mode == SHOW_SIGNUP) {
            buttonLogin.setText("Sign Up");

            button_forgotPassword.animate().alpha(0.0f).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    button_forgotPassword.setVisibility(View.INVISIBLE);
                    super.onAnimationEnd(animation);
                }
            });
            buttonSignUp.setText(R.string.logIn);
            emailLoginMode = SHOW_SIGNUP;
        } else if (mode == SHOW_LOGIN) {
            buttonLogin.setText("Login");
            button_forgotPassword.animate().alpha(1.0f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300).setListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    button_forgotPassword.setVisibility(View.VISIBLE);

                    super.onAnimationStart(animation);
                }
            });
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
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        getmContext().startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onDestroyView() {
        if (!isUserSignedUp)
            mGoogleSignInClient.signOut();
        super.onDestroyView();
    }

}
