package com.jullae.ui.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jullae.utils.NetworkUtils;

/**
 * Created by Rahul Abrol on 12/14/17.
 * <p>
 * Base class for all the fragments used
 * to hold all the common functions and other details.
 */
public abstract class BaseFragment extends Fragment {

    private Activity mContext;
    private BroadcastReceiver mNetworkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkUtils.isNetworkAvailable(context)) {
                onNetworkAvailable();
            }
        }
    };

    public void onNetworkAvailable() {
        NetworkUtils.unRegisterNetworkChangeListener(getmContext(), mNetworkChangeReceiver);
    }

    public BroadcastReceiver getmNetworkChangeReceiver() {
        return mNetworkChangeReceiver;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            mContext = (Activity) context;
        else throw new IllegalArgumentException("Context should be an instance of Activity");
    }

    @Override
    public void onDestroy() {
        mContext = null;
        super.onDestroy();
    }

    public Activity getmContext() {
        return mContext;
    }


    @Override
    public void onDestroyView() {
        //NetworkUtils.un
        super.onDestroyView();
    }


}
