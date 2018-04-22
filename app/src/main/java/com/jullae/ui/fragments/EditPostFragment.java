/*
package com.jullae.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.constant.AppConstant;
import com.jullae.retrofit.APIError;
import com.jullae.retrofit.ApiInterface;
import com.jullae.retrofit.CommonResponse;
import com.jullae.retrofit.ResponseResolver;
import com.jullae.retrofit.RestClient;
import com.jullae.ui.adapters.BottomSheetAdapter;
import com.jullae.utils.RecyclerTouchListener;
import com.jullae.utils.SimpleDividerItemDecoration;
import com.jullae.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;

*/
/**
 * Created by Rahul Abrol on 12/13/17.
 * <p>
 * Class @{@link EditPostFragment} act as a bottom
 * sheet to show menu with option that perform actions.
 *//*

public class EditPostFragment extends BottomSheetDialogFragment {

    private ApiInterface client;
    private int id;

    */
/**
 * Instance of this class.
 *
 * @param bundle extra data
 * @return instance of Dialog.
 *//*

    public static BottomSheetDialogFragment getInstance(final Bundle bundle) {
        BottomSheetDialogFragment dialogFragment = new EditPostFragment();
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = RestClient.getApiInterface();
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt(AppConstant.EXTRA_ID, 0);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, final int style) {
        super.setupDialog(dialog, style);
        //Get the content View
        View contentView = View.inflate(dialog.getContext(), R.layout.fragment_edit_post, null);
        //initialize recycler view for list of items.
        RecyclerView recyclerView = contentView.findViewById(R.id.rvBottomSheet);
        final ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.more_array)));
        recyclerView.setAdapter(new BottomSheetAdapter(dialog.getContext(), arrayList));
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
                        reportUser();
                        break;
                    case 1:
                        followUser();
                        break;
                    case 2:
                        savePost();
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

    */
/**
 * Save the user post.
 *//*

    private void savePost() {
        Call<CommonResponse> data = client.savePost(id);
        data.enqueue(new ResponseResolver<CommonResponse>(getContext(), true, false) {
            @Override
            public void success(final CommonResponse likeModel) {
                Toast.makeText(getActivity(), likeModel.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(getContext(), getView(), error.getMessage());
            }
        });
    }

    */
/**
 * Follow user api hit.
 *//*

    private void followUser() {
        Call<CommonResponse> data = client.followUser(id);
        data.enqueue(new ResponseResolver<CommonResponse>(getContext(), true, false) {
            @Override
            public void success(final CommonResponse likeModel) {
                Toast.makeText(getActivity(), likeModel.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(getContext(), getView(), error.getMessage());
            }
        });
    }

    */
/**
 * Report user api hit.
 *//*

    private void reportUser() {
        Call<CommonResponse> data = client.reportUser(id);
        data.enqueue(new ResponseResolver<CommonResponse>(getContext(), true, false) {
            @Override
            public void success(final CommonResponse likeModel) {
                Toast.makeText(getActivity(), likeModel.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(getContext(), getView(), error.getMessage());
            }
        });
    }
}*/
