package com.jullae.ui.writeStory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.utils.dialog.MyProgressDialog;

public class WriteStoryActivity extends AppCompatActivity implements WriteStoryView {

    private WriteStoryPresentor mPresentor;
    private String picture_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        picture_id = getIntent().getStringExtra("picture_id");
        mPresentor = new WriteStoryPresentor(((AppController) getApplication()).getmAppDataManager());
        mPresentor.attachView(this);


        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText field_title = findViewById(R.id.field_title);
        final EditText field_story = findViewById(R.id.field_story);

        findViewById(R.id.text_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresentor.sendStoryPublishReq(field_title.getText().toString().trim(), field_story.getText().toString().trim(), picture_id);
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
        finish();
    }
}
