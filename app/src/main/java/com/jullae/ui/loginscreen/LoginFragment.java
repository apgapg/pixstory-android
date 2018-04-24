package com.jullae.ui.loginscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jullae.R;
import com.jullae.ui.base.BaseFragment;

import static com.jullae.utils.Constants.SHOW_LOGIN;
import static com.jullae.utils.Constants.SHOW_SIGNUP;

public class LoginFragment extends BaseFragment {
    private View view;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView forgotPasswordTextView;
    private Button buttonLogin;
    private TextView buttonSignUp;
    private int emailLoginMode = SHOW_LOGIN;

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


        return view;


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
}
