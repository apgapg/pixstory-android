package com.jullae.ui.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Rahul Abrol on 12/14/17.
 * <p>
 * Base class for all the fragments used
 * to hold all the common functions and other details.
 */
public abstract class BaseFragment extends Fragment {

    private Activity mContext;


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
        super.onDestroyView();
    }


}
