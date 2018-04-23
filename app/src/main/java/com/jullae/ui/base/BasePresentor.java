package com.jullae.ui.base;

import com.jullae.helpers.AppDataManager;

public class BasePresentor<T extends MvpView> implements Presentor<T> {


    private final AppDataManager mAppDataManager;
    private T mMvpView;

    public BasePresentor(AppDataManager appDataManager) {
        this.mAppDataManager = appDataManager;
    }

    public AppDataManager getmAppDataManager() {
        return mAppDataManager;
    }


    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }


    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
