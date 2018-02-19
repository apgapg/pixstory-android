package com.jullae.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.ui.adapters.AddPostAdapter;
import com.jullae.utils.RecyclerTouchListener;
import com.jullae.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Rahul Abrol on 12/18/17.
 * <p>
 * Bottom Sheet @{@link AddPostDialogFragment} used to
 * display the  bottom sheet for add new stories and Add new
 * pictures etc.
 */

public class AddPostDialogFragment extends BottomSheetDialogFragment {

    private Activity mActivity;
    private PicListner listner;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        listner = (PicListner) context;
        if (context != null) {
            mActivity = (Activity) context;
        } else {
            mActivity = AddPostDialogFragment.this.getActivity();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, final int style) {
        super.setupDialog(dialog, style);
        //Get the content View
        View contentView = View.inflate(dialog.getContext(), R.layout.fragment_add_post, null);
        //initialization
        RecyclerView recyclerView = contentView.findViewById(R.id.rvBottomSheet);
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.edit_array)));
        recyclerView.setAdapter(new AddPostAdapter(dialog.getContext(), arrayList));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(dialog.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(dialog.getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                switch (position) {
                    case 0:
                        listner.addPicture();
                        break;
                    case 1:
                        Toast.makeText(dialog.getContext(), "unidentified user here not possible", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                dismiss();
            }

            @Override
            public void onLongClick(final View view, final int position) {

            }
        }));
        dialog.setContentView(contentView);

        //Set the coordinator layout behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        //Set callback
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull final View bottomSheet, final int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss();
                    }
                }

                @Override
                public void onSlide(@NonNull final View bottomSheet, final float slideOffset) {
                }
            });
        }
    }

    /**
     * Add pic
     */
    public interface PicListner {
        /**
         * Add pic.
         */
        void addPicture();

    }
}
