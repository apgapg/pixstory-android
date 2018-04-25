package com.jullae.ui.homefeed.freshfeed;

import com.jullae.model.ConversationModel;
import com.jullae.ui.base.MvpView;
import com.jullae.ui.profileSelf.ProfileMainModel;

import java.util.List;

public interface ProfileFragmentView extends MvpView {
    void showPicUploadProgress();

    void hidePicUploadProgress();

    void onProfilePicUpdateFail();

    void onProfilePicUpdateSuccess(String profile_dp_url);

    void onProfileFetchSuccess(ProfileMainModel.ProfileModel profileModel);

    void onProfileFetchFail();

    void onConversationListFetchSuccess(List<ConversationModel.Conversation> conversationList);

    void onConversationListFetchFail();
}
