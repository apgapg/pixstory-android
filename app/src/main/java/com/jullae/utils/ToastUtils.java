package com.jullae.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.jullae.R;

public class ToastUtils {
    public static void showNoInternetToast(Activity activity) {
        Toast.makeText(activity.getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
    }

    public static void showNoInternetToast(Context context) {
        Toast.makeText(context.getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
    }

    public static void showSomethingWentWrongToast(Context context) {
        Toast.makeText(context.getApplicationContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();

    }
}
