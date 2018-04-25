package com.jullae.ui.profileSelf.draftTab;

import com.jullae.model.DraftModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface DraftTabView extends MvpView {
    void onDraftsFetchSuccess(List<DraftModel.FreshFeed> list);

    void onDraftsFetchFail();
}
