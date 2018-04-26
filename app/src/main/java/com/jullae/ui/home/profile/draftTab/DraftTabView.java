package com.jullae.ui.home.profile.draftTab;

import com.jullae.data.db.model.DraftModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface DraftTabView extends MvpView {
    void onDraftsFetchSuccess(List<DraftModel.FreshFeed> list);

    void onDraftsFetchFail();
}
