package com.jullae.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Rahul Abrol on 12/14/17.
 * <p>
 * Base class for all the fragments used
 * to hold all the common functions and other details.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
