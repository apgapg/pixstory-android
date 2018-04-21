package com.jullae;

import com.jullae.helpers.AppDataManager;

public class BasePresentor {


    private final AppDataManager mAppDataManager;

    public BasePresentor(AppDataManager appDataManager) {
        this.mAppDataManager = appDataManager;
    }

    public AppDataManager getmAppDataManager() {
        return mAppDataManager;
    }
}
