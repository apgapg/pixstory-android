package com.jullae.ui.base;

public interface Presentor<T extends MvpView> {

    void attachView(T mvpView);
    void detachView();
}
