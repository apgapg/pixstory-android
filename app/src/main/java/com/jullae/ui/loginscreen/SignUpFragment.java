package com.jullae.ui.loginscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jullae.R;
import com.jullae.ui.base.BaseFragment;

import static com.jullae.ui.loginscreen.LoginActivity.LOGIN_MODE_FACEBOOK;
import static com.jullae.ui.loginscreen.LoginActivity.LOGIN_MODE_GOOGLE;
import static com.jullae.ui.loginscreen.LoginActivity.LOGIN_MODE_SIGNUP_EMAIL;

public class SignUpFragment extends BaseFragment {


    private View view;
    private EditText nameField;
    private EditText pennameField;
    private int loginMode = 0;
    private EditText emailField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        nameField = view.findViewById(R.id.name_field);
        pennameField = view.findViewById(R.id.penname_field);
        emailField = view.findViewById(R.id.email_field);

        if (getArguments() != null) {
            String email = getArguments().getString("email");
            int loginMode = getArguments().getInt("loginmode");

            nameField.setVisibility(View.INVISIBLE);
            if (email == null) {
                emailField.setVisibility(View.VISIBLE);

            }
            this.loginMode = loginMode;
            if (loginMode == 0)
                throw new IllegalArgumentException("login mode isnt assigned");
        }
        view.findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginMode == LOGIN_MODE_SIGNUP_EMAIL)
                    ((LoginActivity) getmContext()).performSignUp(nameField.getText().toString().trim(), pennameField.getText().toString().trim());
                else if (loginMode == LOGIN_MODE_GOOGLE || loginMode == LOGIN_MODE_FACEBOOK)
                    ((LoginActivity) getmContext()).addProfileDetails(pennameField.getText().toString().trim(), emailField.getText().toString().trim());
            }
        });


        return view;
    }

}
