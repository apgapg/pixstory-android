package com.jullae.ui.ProfileVisitor;

import com.jullae.ui.base.MvpView;
import com.jullae.ui.profileSelf.ProfileMainModel;

public interface ProfileVisitorView extends MvpView {
    void onProfileFetchFail();

    void onProfileFetchSuccess(ProfileMainModel.ProfileModel profileMainModel);


}
