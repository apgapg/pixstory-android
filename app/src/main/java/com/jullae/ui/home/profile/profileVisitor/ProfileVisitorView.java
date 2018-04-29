package com.jullae.ui.home.profile.profileVisitor;

import com.jullae.data.db.model.ProfileModel;
import com.jullae.ui.base.MvpView;

public interface ProfileVisitorView extends MvpView {
    void onProfileFetchFail();

    void onProfileFetchSuccess(ProfileModel profileMainModel);


}
