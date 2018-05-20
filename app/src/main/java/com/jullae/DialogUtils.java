package com.jullae;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.storydetails.StoryDetailPresentor;
import com.jullae.utils.MyAlertDialog;

public class DialogUtils {
    public static void showDeleteStoryDialog(Context context, final StoryDetailPresentor mPresentor, final String story_id) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(context, "Delete Story!", "Are you sure you want to delete this story?", "Yes", "No") {
            @Override
            public void onNegativeButtonClick(DialogInterface dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveButtonClick(DialogInterface dialog) {
                dialog.dismiss();
                mPresentor.sendStoryDeleteReq(story_id);
            }
        };
        myAlertDialog.show();
    }

    public static void showReportStoryDialog(final Activity context, final StoryDetailPresentor mPresentor, final StoryModel storyModel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View view = context.getLayoutInflater().inflate(R.layout.dialog_report_story, null);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();

        final EditText field_report = view.findViewById(R.id.field_report);
        final View btn_report = view.findViewById(R.id.report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String report = field_report.getText().toString().trim();
                if (report.length() != 0) {

                    view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                    btn_report.setVisibility(View.INVISIBLE);

                    mPresentor.reportStory(report, storyModel.getStory_id(), new StoryDetailPresentor.StringReqListener() {
                        @Override
                        public void onSuccess() {
                            view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            dialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Your report has been submitted!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFail() {
                            view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            btn_report.setVisibility(View.VISIBLE);
                            Toast.makeText(context.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                }
            }
        });

        dialog.show();


    }
}
