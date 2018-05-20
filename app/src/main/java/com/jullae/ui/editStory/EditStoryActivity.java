package com.jullae.ui.editStory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.dialog.MyProgressDialog;

import java.util.Arrays;

public class EditStoryActivity extends AppCompatActivity implements EditStoryView {

    private EditText field_title;
    private EditText field_story;
    private TextView text_discard, text_update;
    private String story_id;
    private String story_title;
    private String story_text;
    private EditStoryPresentor mPresentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);

        getIntentData();

        field_title = findViewById(R.id.field_title);
        field_story = findViewById(R.id.field_story);

        text_discard = findViewById(R.id.text_discard);
        text_update = findViewById(R.id.text_update);


        setIntentData();

        setClickListeners();
        mPresentor = new EditStoryPresentor();
        mPresentor.attachView(this);


    }


    private void getIntentData() {

        story_id = getIntent().getStringExtra("story_id");
        story_title = getIntent().getStringExtra("story_title");
        story_text = getIntent().getStringExtra("story_text");

        AppUtils.checkforNull(Arrays.asList(story_id, story_text, story_title));

    }


    private void setIntentData() {
        field_title.setText(story_title);
        field_story.setText(story_text);

        field_story.setSelection(story_text.length());
        field_story.requestFocus();
    }

    private void setClickListeners() {
        text_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add warning dialog
                finish();
            }
        });
        text_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPublishDialog();
            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWarningDialog();
            }
        });
    }


    @Override
    protected void onDestroy() {
        mPresentor.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        MyProgressDialog.showProgressDialog(this, "Please Wait!");
    }

    @Override
    public void hideProgress() {
        MyProgressDialog.dismissProgressDialog();

    }

    @Override
    public void onStoryUpdateSuccess() {
        Toast.makeText(this.getApplicationContext(), "Story updated successfully!", Toast.LENGTH_SHORT).show();
        AppUtils.sendRefreshBroadcast(this, Constants.REFRESH_STORY);
        AppUtils.sendRefreshBroadcast(this, Constants.REFRESH_HOME_FEEDS);

        finish();
    }

    @Override
    public void onStoryUpdateFail(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStoryTitleEmpty() {
        Toast.makeText(this, "Title cannot be empty!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStoryTextEmpty() {
        Toast.makeText(this, "Story cannot be empty!", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        if (!story_text.equals(field_story.getText().toString().trim()) || !story_title.equals(field_title.getText().toString().trim()))
            showWarningDialog();
        else
            super.onBackPressed();
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("All your changes will be lost. Proceed back?");

        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
                dialog.cancel();
            }
        });

        //creating alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showPublishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Publish Changes???");

        builder.setPositiveButton("Publish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresentor.updateStory(story_id, field_title.getText().toString(), field_story.getText().toString());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
                dialog.cancel();
            }
        });

        //creating alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
