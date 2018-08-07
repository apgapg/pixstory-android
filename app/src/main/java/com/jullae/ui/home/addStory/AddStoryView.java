package com.jullae.ui.home.addStory;

import com.jullae.data.db.model.PictureModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

/**
 * Created by master on 28/5/18.
 */
public interface AddStoryView extends MvpView {
    void showProgressBar();

    void hideProgressBar();

    void onListFetch(List<PictureModel> picturesList);

    void onListFetchFail();
}
