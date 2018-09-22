package com.jullae.ui.writeStory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ebolo.krichtexteditor.RichEditor;
import com.ebolo.krichtexteditor.fragments.KRichEditorFragment;
import com.ebolo.krichtexteditor.fragments.Options;
import com.ebolo.krichtexteditor.ui.widgets.EditorButton;
import com.jullae.R;
import com.jullae.data.db.model.WriteStoryCategoryItem;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.KeyboardUtils;
import com.jullae.utils.MyProgressDialog;
import com.nex3z.flowlayout.FlowLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class WriteStoryActivity extends AppCompatActivity implements WriteStoryView {

    private WriteStoryPresentor mPresentor;
    private String picture_id;
    private EditText field_title;
    private View imageBold;
    private KRichEditorFragment editorFragment;
    private String pictureUrl;
    private String storyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_story);


        mPresentor = new WriteStoryPresentor();
        mPresentor.attachView(this);
        mPresentor.loadCategories();

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        field_title = findViewById(R.id.field_title);


        picture_id = getIntent().getStringExtra("picture_id");
        if (getIntent().getStringExtra("story_title") != null) {
            field_title.setText(getIntent().getStringExtra("story_title"));
        }

        storyText = getIntent().getStringExtra("story_text");


       /* pictureUrl = getIntent().getStringExtra("image");

        if (pictureUrl != null)
            GlideUtils.loadImagefromUrl((ImageView) findViewById(R.id.image), pictureUrl);
*/
        findViewById(R.id.text_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editorFragment.getEditor().getHtml(new RichEditor.OnHtmlReturned() {
                    @Override
                    public void process(final String html) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mPresentor.checkNonEmptyFields(field_title.getText().toString(), html)) {
                                    //showPublishDialog();
                                    showCategoryScreen();
                                }
                            }
                        });

                    }
                });

            }
        });

        findViewById(R.id.text_save_draft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editorFragment.getEditor().getHtml(new RichEditor.OnHtmlReturned() {
                    @Override
                    public void process(@NotNull final String html) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPresentor.sendStoryDraftReq(field_title.getText().toString().trim(), html, picture_id);

                            }
                        });
                    }
                });
            }
        });


        editorFragment = (KRichEditorFragment) getSupportFragmentManager().findFragmentByTag("EDITOR");

        if (editorFragment == null)
            editorFragment = KRichEditorFragment.getInstance(
                    new Options()
                            .placeHolder("Start Writing Magic!")
                            .buttonLayout(Arrays.asList(
                                    EditorButton.UNDO,
                                    EditorButton.REDO,
                                    EditorButton.BOLD,
                                    EditorButton.ITALIC,
                                    EditorButton.UNDERLINE,
                                    EditorButton.SUBSCRIPT,
                                    EditorButton.SUPERSCRIPT,
                                    EditorButton.STRIKETHROUGH,
                                    EditorButton.JUSTIFY_LEFT,
                                    EditorButton.JUSTIFY_CENTER,
                                    EditorButton.JUSTIFY_RIGHT,
                                    EditorButton.JUSTIFY_FULL,
                                    EditorButton.ORDERED,
                                    EditorButton.UNORDERED,
                                    EditorButton.CHECK,
                                    EditorButton.NORMAL,
                                    EditorButton.H1,
                                    EditorButton.H2,
                                    EditorButton.H3,
                                    EditorButton.H4,
                                    EditorButton.H5,
                                    EditorButton.H6,
                                    EditorButton.INDENT,
                                    EditorButton.OUTDENT,
                                    EditorButton.BLOCK_QUOTE,
                                    EditorButton.BLOCK_CODE,
                                    EditorButton.CODE_VIEW
                            ))
                            .onInitialized(new Runnable() {
                                @Override
                                public void run() {
                                    if (storyText != null)
                                        editorFragment.getEditor().setHtml(storyText);
                                }
                            })

            );


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder, editorFragment, "EDITOR")
                .commit();
    }

    private void showCategoryScreen() {

        KeyboardUtils.hideKeyboard(WriteStoryActivity.this);
        findViewById(R.id.category_view).setVisibility(View.VISIBLE);

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
        Toast.makeText(getApplicationContext(), "Story cannnot be empty!", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onFetchCategories(List<WriteStoryCategoryItem> list) {
        for (WriteStoryCategoryItem item : list) {
            View child = View.inflate(WriteStoryActivity.this, R.layout.item_write_story_category, null);
            ((TextView) child).setText(item.getName());
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPublishDialog();
                }
            });
            ((FlowLayout) findViewById(R.id.flowLayout)).addView(child);

        }

    }

    @Override
    public void onBackPressed() {
        if (findViewById(R.id.category_view).getVisibility() == View.VISIBLE)
            findViewById(R.id.category_view).setVisibility(View.INVISIBLE);
        else if (!TextUtils.isEmpty(field_title.getText().toString()) || !TextUtils.isEmpty(editorFragment.getEditor().getHtml())) {
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

                        editorFragment.getEditor().getHtml(new RichEditor.OnHtmlReturned() {
                            @Override
                            public void process(@NotNull final String html) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPresentor.sendStoryPublishReq(field_title.getText().toString().trim(), html, picture_id);

                                    }
                                });
                            }
                        });
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
