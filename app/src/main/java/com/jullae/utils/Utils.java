package com.jullae.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jullae.R;
import com.jullae.constant.AppConstant;
import com.jullae.utils.dialog.CustomAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Rahul Abrol on 12/13/17.
 */

public final class Utils {
    private static AlertDialog dialog;

    /**
     * Empty Constructor
     * not called
     */
    private Utils() {
    }

    /**
     * Method used to show image in imageview .
     *
     * @param context   contect of calling class.
     * @param url       url of image.
     * @param imageView where to show image.
     */
    public static void showImage(final Context context, final Object url, final ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    /**
     * Show snackbar .
     *
     * @param context calling class
     * @param view    view
     * @param message text.
     */
    public static void showSnackbar(final Context context,
                                    final View view,
                                    final String message) {
        if (context == null || message == null || message.isEmpty()) {
            return;
        }
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(context.getString(R.string.text_ok), null).show();
    }

//    /**
//     * Show single button dialog with title and message.
//     *
//     * @param context context of calling class.
//     * @param title   title on dialog.
//     * @param message error message.
//     */
//    public static void showSingleBtnDialog(final Context context, final String title, final String message) {
//        if (!"".equals(title)) {
//            if (dialog != null) {
//                dialog.dismiss();
//            }
//            dialog = new CustomAlertDialog.Builder(context)
//                    .setTitle(title)
//                    .setMessage(message)
//                    .setPositiveButton(R.string.text_ok, new CustomAlertDialog.CustomDialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick() {
//
//                        }
//                    })
//                    .show();
////            SingleBtnDialog.with(context).setHeading(title).setMessage(message).show();
//        } else {
////            SingleBtnDialog.with(context).setMessage(message).show();
//            if (dialog != null) {
//                dialog.dismiss();
//            }
//            dialog = new CustomAlertDialog.Builder(context)
//                    .setTitle(title)
//                    .setMessage(message)
//                    .setPositiveButton(R.string.text_ok, new CustomAlertDialog.CustomDialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick() {
//
//                        }
//                    })
//                    .show();
//        }
//    }

    /**
     * Method to check if internet is connected
     *
     * @param context context of calling class
     * @return true if connected to any network else return false
     */
    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) (context.getApplicationContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }

    /**
     * Method to show toast
     *
     * @param message  that disaply in the Toast
     * @param mContext context of calling activity or fragment
     */
    public static void showToast(final Context mContext, final String message) {
        if (mContext == null
                || message == null
                || message.isEmpty()) {
            return;
        }
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

//    /**
//     * @param mContext context of calling activity or fragment
//     */
//    public static void restartAppOnSessionExpired(final Context mContext) {
//        Util.showToast(mContext, mContext.getString(R.string.session_expired));
//        CommonData.clearData();
//        Intent intent = new Intent(mContext, SplashActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK
//                | Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
//    }

    /**
     * @param context of calling activity or fragment
     * @return pixel scale factor
     */
    private static float getPixelScaleFactor(final Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT;
    }

//    /**
//     * Method to convert dp into pixel
//     *
//     * @param context of calling activity or fragment
//     * @param dp      dp value that need to convert into px
//     * @return px converted dp into px
//     */
//    public static int dpToPx(final Context context, final int dp) {
//        int px = Math.round(dp * getPixelScaleFactor(context));
//        return px;
//    }

//    /**
//     * Method to convert pixel into dp
//     *
//     * @param context of calling activity or fragment
//     * @param px      pixel value that need to convert into dp
//     * @return dp converted px into dp
//     */
//    public static int pxToDp(final Context context, final int px) {
//        int dp = Math.round(px / getPixelScaleFactor(context));
//        return dp;
//    }

    /**
     * convert into the map.
     *
     * @param object object
     * @return return
     * @throws JSONException json exception;
     */
    public static Map<String, Object> toMap(final JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
//            Log.i("MAP KEYS", "toMap: " + value);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * convert into the list.
     *
     * @param array array
     * @return return
     * @throws JSONException json exception
     */
    public static List<Object> toList(final JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     * MEthod to check if called component is first time launch .
     *
     * @param mActivity calling class.
     * @return true if first time called else false.
     */
    public static boolean isFirstTimeLaunch(final Activity mActivity) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mActivity);
        if (pref.getBoolean(AppConstant.IS_FIRST_TIME_LAUNCH, true)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(AppConstant.IS_FIRST_TIME_LAUNCH, false);
            editor.apply();
            return true;
        }
        return false;
    }

    /**
     * MEthod used to strike over price.
     *
     * @param textView textview .
     * @param start    start strike
     * @param end      end strike
     * @param text     text.
     */
    public static void setStrikeThrough(final TextView textView, final int start, final int end, final String text) {
        textView.setText(text, TextView.BufferType.SPANNABLE);
        final StrikethroughSpan span = new StrikethroughSpan();
        Spannable spannable = (Spannable) textView.getText();
        spannable.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }

    /**
     * No internet popup.
     *
     * @param context context.
     */
    public static void noInternet(final Context context) {
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = new CustomAlertDialog.Builder(context)
                .setTitle(context.getString(R.string.error_no_internet))
                .setMessage(context.getString(R.string.error_check_connection))
                .setCancelable(false)
                .setPositiveButton(R.string.text_ok, new CustomAlertDialog.CustomDialogInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        if (isNetworkAvailable(context)) {
                            dialog.dismiss();
                        }
                    }
                })
                .show();
    }

    /**
     * open email.
     *
     * @param context   context
     * @param receivers Email Receivers
     * @throws Exception exception if now app found to send Email
     */
    public static void openEmailApp(final Context context, final String[] receivers) throws Exception {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, receivers);
     /*   emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);*/
        context.startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
    }


    /**
     * Method to open the Call Dialer
     *
     * @param context context
     * @param phone   Number to call
     */
    public static void openCallDialer(final Context context, final String phone) {
        try {
            String number = extractNumber(phone);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showToast(context, context.getString(R.string.error));
        }
    }

    /**
     * Extracts the phone number
     *
     * @param number phone number
     * @return phone without special chars
     */
    public static String extractNumber(final String number) {

        String phone = number.replaceAll(" ", "")
                .replaceAll("\\(", "")
                .replace(")", "")
                .replace("-", "");

        return phone;
    }

    /**
     * Method used to read a text file and get the json object.
     *
     * @param inputStream file.
     * @return JSONObject.
     * @throws JSONException exception.
     */
    public static JSONObject readTextFile(final InputStream inputStream) throws JSONException {
        BufferedInputStream resourceStream = null;
        BufferedReader reader = null;
        StringBuilder text = new StringBuilder();

        try {
            resourceStream = new BufferedInputStream(inputStream);
            reader = new BufferedReader(new InputStreamReader(resourceStream));

            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException ex) {
            ex.getMessage();
            Log.e("myApp", ex.getMessage());
        } finally {
            try {
                reader.close();
                resourceStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new JSONObject(text.toString());
    }
}
