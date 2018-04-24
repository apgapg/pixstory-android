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

public class SignUpFragment extends BaseFragment {


    private View view;
    private EditText nameField;
    private EditText pennameField;
    private EditText bioField;

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
        bioField = view.findViewById(R.id.bio_field);

        view.findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getmContext()).performSignUp(nameField.getText().toString().trim(), pennameField.getText().toString().trim(), bioField.getText().toString().trim());
            }
        });


        return view;
    }
}
