package com.jullae.ui.home.homeFeed.freshfeed;

import com.jullae.data.db.model.ConversationModel;
import com.jullae.data.db.model.ProfileModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface ProfileFragmentView extends MvpView {
    void showPicUploadProgress();

    void hidePicUploadProgress();

    void onProfilePicUpdateFail();

    void onProfilePicUpdateSuccess(String profile_dp_url);

    void onProfileFetchSuccess(ProfileModel profileModel);

    void onProfileFetchFail();

    void onConversationListFetchSuccess(List<ConversationModel.Conversation> conversationList);

    void onConversationListFetchFail();

    void showProgressBar();

    void hideProgressBar();
}
