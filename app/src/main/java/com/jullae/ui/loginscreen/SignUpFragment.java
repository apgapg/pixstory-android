package com.jullae.ui.loginscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.jullae.R;
import com.jullae.ui.base.BaseFragment;
import com.jullae.utils.AppUtils;
import com.jullae.utils.GlideUtils;
import com.jullae.utils.ToastUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.jullae.ui.loginscreen.LoginActivity.LOGIN_MODE_FACEBOOK;
import static com.jullae.ui.loginscreen.LoginActivity.LOGIN_MODE_GOOGLE;
import static com.jullae.ui.loginscreen.LoginActivity.LOGIN_MODE_SIGNUP_EMAIL;

public class SignUpFragment extends BaseFragment {


    private static final String TAG = SignUpFragment.class.getName();
    private View view;
    private EditText nameField;
    private EditText pennameField;
    private int loginMode = 23;
    private EditText emailField, bioField;
    private View addImage;
    private ImageView userImage;
    private File imageFile;

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
        bioField = view.findViewById(R.id.bio_field);
        addImage = view.findViewById(R.id.add_image);
        userImage = view.findViewById(R.id.image);

        if (getArguments() != null) {
            String email = getArguments().getString("email");
            int loginMode = getArguments().getInt("loginmode");

            if (email == null) {
                emailField.setVisibility(View.VISIBLE);

            } else {
                emailField.setVisibility(View.GONE);


            }
            this.loginMode = loginMode;
            if (loginMode == 0)
                throw new IllegalArgumentException("login mode isnt assigned");
        }
        view.findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + loginMode);
                if (loginMode == LOGIN_MODE_SIGNUP_EMAIL)
                    ((LoginActivity) getmContext()).performSignUp(nameField.getText().toString().trim(), pennameField.getText().toString().trim(), bioField.getText().toString(), imageFile);
                else if (loginMode == LOGIN_MODE_GOOGLE || loginMode == LOGIN_MODE_FACEBOOK)
                    ((LoginActivity) getmContext()).addProfileDetails(pennameField.getText().toString().trim(), emailField.getText().toString().trim());
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.startImagePickActivity(SignUpFragment.this);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + requestCode + "    " + resultCode);
        if (requestCode == AppUtils.REQUEST_CODE_PROFILE_PIC_CAPTURE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                onImagePickSuccess(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d(TAG, "onActivityResult: " + error);
                onImagePickFail();
            }
        }

    }

    private void onImagePickSuccess(Uri uri) {
        GlideUtils.loadImagefromUri(getmContext(), uri, userImage);
        imageFile = new File(uri.getPath());
        //  updateDpReq(imageFile);
    }

    private void onImagePickFail() {
        ToastUtils.showSomethingWentWrongToast(getmContext());


    }
}
