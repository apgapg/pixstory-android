package com.jullae.ui.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.constant.AppConstant;
import com.jullae.model.AllLikeModel;
import com.jullae.retrofit.APIError;
import com.jullae.retrofit.ApiInterface;
import com.jullae.retrofit.CommonResponse;
import com.jullae.retrofit.ResponseResolver;
import com.jullae.retrofit.RestClient;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.utils.SimpleDividerItemDecoration;
import com.jullae.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by Rahul Abrol on 12/20/17.
 * <p>
 * Class @{@link LikeDialogFragment} used to show the list
 * of users who like the story.
 */
public class LikeDialogFragment extends DialogFragment implements View.OnClickListener,
        LikeAdapter.LikeListener {

    //private Activity mActivity;
    private ApiInterface client;
    private TextView tvClose;
    private AllLikeModel model;
    private LikeAdapter adapter;
    private boolean itemType;
    private int userId;
    private RecyclerView recyclerView;

    /**
     * Return instance of dialog.
     *
     * @param bundle extra data.
     * @return instance.
     */
    public static DialogFragment getInstance(final Bundle bundle) {
        DialogFragment likeDialog = new LikeDialogFragment();
        likeDialog.setArguments(bundle);
        return likeDialog;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = RestClient.getApiInterface();
        Bundle bundle = getArguments();
        if (bundle != null) {
            itemType = bundle.getBoolean(AppConstant.EXTRA_ITEM_TYPE);
            userId = bundle.getInt(AppConstant.EXTRA_ID, 0);

        }
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //add root view for dialog.
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_likes, null, false);
        tvClose = rootView.findViewById(R.id.tvClose);
        recyclerView = rootView.findViewById(R.id.rvLikes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        JSONObject jsonObject;
//        try {
//            jsonObject = Utils.readTextFile(getResources().openRawResource(R.raw.all_likes));
//            model = new Gson().fromJson(jsonObject.toString(), AllLikeModel.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        getFeeds(userId, itemType);

        //Initialize Listener.
        tvClose.setOnClickListener(this);
        // set custom alert view here
        builder.setView(rootView);
        builder.setCancelable(false);

        return builder.create();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tvClose:
                break;
            case R.id.tvSaveAsDraft:
                Toast.makeText(getActivity(), "Save as Draft", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvPublish:
                Toast.makeText(getActivity(), "Publish", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        dismiss();
    }

    @Override
    public void onClick(final int position, final String tag, final int clickedId) {
        switch (tag) {
            case AppConstant.FOLLOW:
                followUser(position, clickedId);
                break;
            default:
                break;
        }
        dismiss();
    }

    /**
     * Follow user api hit.
     *
     * @param position  position of clicked item.
     * @param clickedId id of user that going to be followed.
     */
    private void followUser(final int position, final int clickedId) {
        Call<CommonResponse> data = client.followUser(clickedId);
        data.enqueue(new ResponseResolver<CommonResponse>(getActivity(), true, false) {
            @Override
            public void success(final CommonResponse likeModel) {
//                adapter.notifyItemChanged(position, );
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(getActivity(), tvClose, error.getMessage());
            }
        });
    }

    /**
     * Get all the like.
     *
     * @param id          id of users.
     * @param isStoryUser if story user true else false.
     */
    private void getFeeds(final int id, final boolean isStoryUser) {
//        CommonParams.Builder params = new CommonParams.Builder();
//        params.build().getMap()
        Call<AllLikeModel> data;
        if (isStoryUser) {
            data = client.getAllStoryLike(id + "");
        } else {
            data = client.getAllPicLike(id + "");
        }
        data.enqueue(new ResponseResolver<AllLikeModel>(getActivity(), true, false) {
            @Override
            public void success(final AllLikeModel likeModel) {
                if (likeModel != null && likeModel.getLikes().size() > 0) {
                    model = likeModel;
                    if (model.getLikes() != null && model.getLikes().size() > 0) {
                        ArrayList<AllLikeModel.Like> arrayList = (ArrayList<AllLikeModel.Like>) model.getLikes();
                        adapter = new LikeAdapter(getActivity(), arrayList, LikeDialogFragment.this);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(getActivity(), tvClose, error.getMessage());
            }
        });
    }
}