package com.jullae.utils;

import android.app.ProgressDialog;
import android.content.Context;


/**
 * Developer: Saurabh Verma
 * Dated: 5/13/16.
 */
public final class MyProgressDialog {

    private static ProgressDialog progressDialog;

    /**
     * Empty Constructor
     * not called
     */
    private MyProgressDialog() {

    }

    /**
     * Is showing boolean.
     *
     * @return the boolean
     */
    public static boolean isShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }

    /**
     * Method to show the progress dialog with a message
     *
     * @param context the context
     * @param message the message
     * @return
     */
    public static void showProgressDialog(final Context context, final String message) {

        if (progressDialog != null) {
            throw new MyRuntimeException("Progress Dialog isn't null!");
        }

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    /**
     * Dismisses the Progress Dialog
     *
     * @return the boolean
     */
    public static void dismissProgressDialog() {
        if (progressDialog == null) {
            throw new MyRuntimeException("Progress Dialog is null!");
        } else if (!progressDialog.isShowing()) {
            throw new MyRuntimeException("Progress Dialog isn't showing!");
        } else {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static class MyRuntimeException extends RuntimeException {
        public MyRuntimeException(String message) {
            super(message);
        }
    }
}
