package com.jullae.ui.profileSelf.draftTab;

import com.jullae.ui.base.MvpView;

public interface DraftTabView extends MvpView {
    void onDraftsFetchSuccess();

    void onDraftsFetchFail();
}
