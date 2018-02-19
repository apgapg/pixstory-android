package com.jullae.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jullae.constant.ApiKeyConstant;
import com.jullae.constant.AppConstant;
import com.jullae.utils.LocaleHelper;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Developer: Rahul Abrol
 * <p>
 * Dated: 13-12-2017.
 * <p>
 * Base class for all the activities used
 * to hold all the common functions and other details.
 */
public abstract class BaseActivity extends AppCompatActivity implements AppConstant, ApiKeyConstant, View.OnClickListener {
    protected static final String TAG = BaseActivity.class.getSimpleName();

    /**
     * Receiver To handle Location When App is in Foreground state
     */
    private BroadcastReceiver notificationReceiver;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context c, final Intent intent) {
                showNotificationDialog(intent.getExtras());
            }
        };
    }

    @Override
    protected void attachBaseContext(final Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(getCurrentContext(newBase)));
    }

    /**
     * Get current context and used in resources.
     *
     * @param newBase base context.
     * @return context.
     */
    public Context getCurrentContext(final Context newBase) {
        return LocaleHelper.wrap(newBase, new Locale("EN"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver, new IntentFilter(NOTIFICATION_RECEIVED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReceiver);
    }

    @Override
    public void onClick(final View v) {

    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent event) {
        View view = getCurrentFocus();
        try {
            boolean ret = super.dispatchTouchEvent(event);

            if (view != null && view instanceof EditText) {
                View w = getCurrentFocus();
                int[] scrcoords = new int[2];
                assert w != null;
                w.getLocationOnScreen(scrcoords);
                float x = event.getRawX() + w.getLeft() - scrcoords[0];
                float y = event.getRawY() + w.getTop() - scrcoords[1];

                if (event.getAction() == MotionEvent.ACTION_UP
                        && (x < w.getLeft() || x >= w.getRight()
                        || y < w.getTop() || y > w.getBottom())) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * show notification dialog.
     *
     * @param mBundle notification bundle
     */
    public void showNotificationDialog(final Bundle mBundle) {
//        Utils.showSingleBtnDialog(BaseActivity.this, "", mBundle.getString(MESSAGE));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Launch the new screen.
     *
     * @param className class name.
     */
    public void launchScreen(final Class className) {
        startActivity(new Intent(this, className));
    }

    /**
     * Launch the new screen.
     *
     * @param className class name.
     * @param bundle    bundle
     */
    public void launchScreen(final Class className, final Bundle bundle) {
        Intent intent = new Intent(this, className);
        intent.putExtra(AppConstant.BUNDLE, bundle);
        startActivity(intent);
    }

    /**
     * Close all previous activities and launch.
     *
     * @param launchClass clas that will ve launched.
     */
    public void clearStackAndLaunch(final Class launchClass) {
        Intent intent = new Intent(getApplicationContext(), launchClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Close all previous activities and launch.
     *
     * @param launchClass clas that will ve launched.
     * @param bundle      bundle l.
     */
    public void clearStackWithBundle(final Class launchClass, final Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), launchClass);
        intent.putExtra(BUNDLE, bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
