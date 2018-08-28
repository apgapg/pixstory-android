package com.jullae.ui.home.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.GlideApp;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.GlideUtils;
import com.jullae.utils.MyProgressDialog;
import com.jullae.utils.NetworkUtils;
import com.jullae.utils.ToastUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

public class ProfileEditActivity extends AppCompatActivity {

    private String TAG = ProfileEditActivity.class.getName();
    private ImageView user_image;
    private File imagefile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_profile);


        final EditText fieldName = findViewById(R.id.field_name);
        final EditText fieldBio = findViewById(R.id.field_bio);

        fieldName.setText(getIntent().getStringExtra("name"));
        fieldName.setSelection(getIntent().getStringExtra("name").length());
        fieldBio.setText(getIntent().getStringExtra("bio"));
        fieldBio.setSelection(getIntent().getStringExtra("bio").substring(49).length());
        String photo = getIntent().getStringExtra("bio");

        user_image = findViewById(R.id.image);
        GlideApp.with(this).load(photo).placeholder(R.drawable.default_pic).dontAnimate().error(R.drawable.default_pic).into(user_image);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.close1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.text_update_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = fieldName.getText().toString();
                final String bio = fieldBio.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(bio)) {
                    MyProgressDialog.showProgressDialog(ProfileEditActivity.this, "Please wait!");
                    AppDataManager.getInstance().getmApiHelper().updateProfileReq(name, bio, AppDataManager.getInstance().getmSharedPrefsHelper().getKeyUserId(), imagefile)
                            .getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {
                                @Override
                                public void onResponse(BaseResponseModel response) {
                                    NetworkUtils.parseResponse(TAG, response);
                                    AppDataManager.getInstance().getmSharedPrefsHelper().setKeyName(name);
                                    AppDataManager.getInstance().getmSharedPrefsHelper().setKeyBio(bio);
                                    MyProgressDialog.dismissProgressDialog();
                                    AppUtils.sendRefreshBroadcast(ProfileEditActivity.this, Constants.REFRESH_HOME_FEEDS);

                                    Toast.makeText(ProfileEditActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onError(ANError anError) {
                                    NetworkUtils.parseError(TAG, anError);
                                    MyProgressDialog.dismissProgressDialog();
                                    Toast.makeText(ProfileEditActivity.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();

                                }
                            });
                }

            }
        });

        findViewById(R.id.add_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.startImagePickActivity(ProfileEditActivity.this);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode);
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
        GlideUtils.loadImagefromUri(this, uri, user_image);
        imagefile = new File(uri.getPath());
        // updateDpReq(file);
    }


    private void onImagePickFail() {
        ToastUtils.showSomethingWentWrongToast(this);
    }

}
