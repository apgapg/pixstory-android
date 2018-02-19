package com.jullae.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.ui.base.BaseFragment;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * Shape fragment;
 */

public class ShapeFragment extends BaseFragment {

    private Activity mActivity;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shape, container, false);
    }

}
