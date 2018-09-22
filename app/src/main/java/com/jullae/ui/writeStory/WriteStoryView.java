package com.jullae.ui.writeStory;

import com.jullae.data.db.model.WriteStoryCategoryItem;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface WriteStoryView extends MvpView {
    void onStoryPublishFail();

    void onStoryPublishSuccess();

    void hideProgressBar();

    void showProgressBar();

    void onStoryDraftFail();

    void onStoryDraftSuccess();

    void onTitleEmpty();

    void onContentEmpty();

    void onFetchCategories(List<WriteStoryCategoryItem> categoryItemList);
}
