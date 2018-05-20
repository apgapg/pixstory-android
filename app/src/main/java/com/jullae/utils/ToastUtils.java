package com.jullae.utils;

import android.app.Activity;
import android.widget.Toast;

import com.jullae.R;

public class ToastUtils {
    public static void showNoInternetToast(Activity activity) {
        Toast.makeText(activity.getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
    }
}
