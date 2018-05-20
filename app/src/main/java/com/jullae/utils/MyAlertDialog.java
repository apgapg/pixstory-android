package com.jullae.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public abstract class MyAlertDialog {
    private AlertDialog.Builder dialog;
    private AlertDialog dlg;


    public MyAlertDialog(Context context, String title, String message, String positive, String negative) {
        dialog = new AlertDialog.Builder(context);
        if (title != null)
            dialog.setTitle(title);
        if (message != null) {
            dialog.setMessage(message);
        }
        if (positive != null)
            dialog.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onPositiveButtonClick(dialog);
                }
            });

        if (negative != null)
            dialog.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onNegativeButtonClick(dialog);
                }
            });
    }


    public abstract void onNegativeButtonClick(DialogInterface dialog);

    public abstract void onPositiveButtonClick(DialogInterface dialog);

    public void show() {
        dlg = dialog.create();
        dlg.show();
    }

    public void hide() {
        dlg.hide();
    }


    public void setCancellable(boolean cancellable) {
        dialog.setCancelable(cancellable);
    }
}