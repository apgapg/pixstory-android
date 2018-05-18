package com.jullae.ui.writeStory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.dialog.MyProgressDialog;

public class WriteStoryActivity extends AppCompatActivity implements WriteStoryView {

    private WriteStoryPresentor mPresentor;
    private String picture_id;
    private EditText field_story, field_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);


        mPresentor = new WriteStoryPresentor();
        mPresentor.attachView(this);


        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        field_title = findViewById(R.id.field_title);
        field_story = findViewById(R.id.field_story);


        picture_id = getIntent().getStringExtra("picture_id");
        if (getIntent().getStringExtra("story_title") != null) {
            field_title.setText(getIntent().getStringExtra("story_title"));
        }
        if (getIntent().getStringExtra("story_text") != null) {
            field_story.setText(getIntent().getStringExtra("story_text"));
            field_story.setSelection(getIntent().getStringExtra("story_text").length());
        }

        findViewById(R.id.text_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresentor.checkNonEmptyFields(field_title.getText().toString(), field_story.getText().toString())) {
                    showPublishDialog();
                }
            }
        });

        findViewById(R.id.text_save_draft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresentor.sendStoryDraftReq(field_title.getText().toString().trim(), field_story.getText().toString().trim(), picture_id);
            }
        });
    }

    @Override
    protected void onDestroy() {
        mPresentor.detachView();
        super.onDestroy();
    }

    @Override
    public void onStoryPublishFail() {
        Toast.makeText(getApplicationContext(), "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStoryPublishSuccess() {
        AppUtils.sendRefreshBroadcast(WriteStoryActivity.this, Constants.REFRESH_HOME_FEEDS);

        Toast.makeText(getApplicationContext(), "Story published successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void hideProgressBar() {
        MyProgressDialog.dismissProgressDialog();
    }

    @Override
    public void showProgressBar() {
        MyProgressDialog.showProgressDialog(this, "Please Wait!");
    }

    @Override
    public void onStoryDraftFail() {
        Toast.makeText(getApplicationContext(), "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStoryDraftSuccess() {
        Toast.makeText(getApplicationContext(), "Story saved as Draft!", Toast.LENGTH_SHORT).show();
        AppUtils.sendRefreshBroadcast(WriteStoryActivity.this, Constants.REFRESH_DRAFTS_TAB);

        finish();
    }

    @Override
    public void onTitleEmpty() {
        Toast.makeText(getApplicationContext(), "Story title cannnot be empty!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onContentEmpty() {
        Toast.makeText(getApplicationContext(), "story cannnot be empty!", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(field_title.getText().toString()) || !TextUtils.isEmpty(field_story.getText().toString())) {
            showExitDialog();
        } else super.onBackPressed();
    }

    private void showExitDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("All your changes will be discarded.");
        builder1.setTitle("Confirm Exit?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private void showPublishDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Publish Story?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPresentor.sendStoryPublishReq(field_title.getText().toString().trim(), field_story.getText().toString().trim(), picture_id);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
