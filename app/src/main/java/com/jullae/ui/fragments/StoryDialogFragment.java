package com.jullae.ui.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jullae.R;
import com.jullae.constant.AppConstant;
import com.jullae.model.PostRequestModel;
import com.jullae.retrofit.APIError;
import com.jullae.retrofit.ApiInterface;
import com.jullae.retrofit.CommonResponse;
import com.jullae.retrofit.ResponseResolver;
import com.jullae.retrofit.RestClient;
import com.jullae.utils.Utils;

import retrofit2.Call;

/**
 * Created by Rahul Abrol on 12/14/17.
 * <p>
 * Class @{@link StoryDialogFragment} used to show the dialog
 * for writing the story and publish it on the App or user
 * can save it as a draft for post it later.
 */

public class StoryDialogFragment extends DialogFragment implements View.OnClickListener {

    private ApiInterface client;
    private TextView tvSaveAsDraft, tvPublish;
    private EditText edPostStory, etYourTitle;
    private int id;
    private UpdateFeed listner;

    /**
     * Instance of this class.
     *
     * @param bundle extra data
     * @return instance of Dialog.
     */
    public static DialogFragment getInstance(final Bundle bundle) {
        DialogFragment dialogFragment = new StoryDialogFragment();
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = RestClient.getApiInterface();
        listner = (UpdateFeed) getActivity();
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                id = bundle.getInt(AppConstant.EXTRA_ID, 0);
            }
        }
    }

    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View rootView = inflater.inflate(R.layout.dialog_new_story, container,
                false);

        etYourTitle = rootView.findViewById(R.id.etYourTitle);
        TextView tvClose = rootView.findViewById(R.id.tvClose);
        tvSaveAsDraft = rootView.findViewById(R.id.tvSaveAsDraft);
        tvPublish = rootView.findViewById(R.id.tvPublish);
        edPostStory = rootView.findViewById(R.id.edPostStory);
        //Initialize Listener.
        tvClose.setOnClickListener(this);
        tvSaveAsDraft.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(final View v) {
        String title = etYourTitle.getText().toString();
        String story = edPostStory.getText().toString();
        switch (v.getId()) {
            case R.id.tvSaveAsDraft:
                if (!title.isEmpty() && !story.isEmpty()) {
                    saveAsDraft(title, story);
                }
                break;
            case R.id.tvPublish:
                if (!title.isEmpty() && !story.isEmpty()) {
                    publish(title, story);
                }
                break;
            default:
                break;
        }
        dismiss();
    }

    /**
     * Used to publish the story.
     *
     * @param title   title of the story.
     * @param content content of story.
     */
    private void publish(final String title, final String content) {
        PostRequestModel requestModel = new PostRequestModel();
        requestModel.setTitle(title);
        requestModel.setContent(content);
        requestModel.setPictureId(id);
        Call<CommonResponse> data = client.publish(requestModel);
        data.enqueue(new ResponseResolver<CommonResponse>(getActivity(), true, false) {
            @Override
            public void success(final CommonResponse commonResponse) {
                listner.updateFeed();
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(getActivity(), getView(), error.getMessage());
            }
        });
    }


    /**
     * Report user api hit.
     *
     * @param title title of the story.
     * @param text  content of story.
     */
    private void saveAsDraft(final String title, final String text) {
        PostRequestModel requestModel = new PostRequestModel();
        requestModel.setTitle(title);
        requestModel.setContent(text);
        requestModel.setPictureId(id);
        Call<CommonResponse> data = client.saveAsDraft(requestModel);
        data.enqueue(new ResponseResolver<CommonResponse>(getActivity(), true, false) {
            @Override
            public void success(final CommonResponse likeModel) {
                listner.updateFeed();
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(getActivity(), getView(), error.getMessage());
            }
        });
    }

    /**
     * Used to update feed
     */
    public interface UpdateFeed {
        /**
         * Update feed.
         */
        void updateFeed();
    }
}

