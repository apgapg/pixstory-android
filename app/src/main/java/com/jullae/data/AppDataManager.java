package com.jullae.data;


import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.db.model.ProfileMainModel;
import com.jullae.data.db.model.ProfileModel;
import com.jullae.data.network.ApiHelper;
import com.jullae.data.prefs.SharedPrefsHelper;
import com.jullae.utils.NetworkUtils;
import com.jullae.utils.Resource;

/**
 * Created by master on 7/4/18.
 */

public class AppDataManager {

    private static final String TAG = AppDataManager.class.getCanonicalName();
    private static AppDataManager uniqueInstance;
    private final ApiHelper mApiHelper;

    private MutableLiveData<Resource<ProfileModel>> mUserProfile;

    private AppDataManager(Context context) {

        //Prevent form the reflection api.
        if (uniqueInstance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");

        SharedPrefsHelper.initialize(context);
        mApiHelper = new ApiHelper(SharedPrefsHelper.getInstance().getKeyToken());
    }

    public static AppDataManager getInstance() {
        if (uniqueInstance == null)
            throw new NullPointerException("Please call initialize() before getting the instance.");
        return uniqueInstance;
    }

    public synchronized static void initialize(Context applicationContext) {
        if (applicationContext == null)
            throw new NullPointerException("Provided application context is null");
        else if (uniqueInstance == null) {
            uniqueInstance = new AppDataManager(applicationContext);
        }
    }

    public SharedPrefsHelper getmSharedPrefsHelper() {
        return SharedPrefsHelper.getInstance();
    }

    public ApiHelper getmApiHelper() {
        return mApiHelper;
    }

    public void fetchSelfProfile(final String penname) {

        getmApiHelper().fetchVisitorProfileData(penname).getAsObject(ProfileMainModel.class, new ParsedRequestListener<ProfileMainModel>() {
            @Override
            public void onResponse(ProfileMainModel profileMainModel) {
                NetworkUtils.parseResponse(TAG, profileMainModel);
                mUserProfile.setValue(Resource.success(profileMainModel.getProfileModel()));
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                mUserProfile.setValue(Resource.error(anError.getErrorBody(), new ProfileModel()));

            }
        });
    }


    public MutableLiveData<Resource<ProfileModel>> getmUserProfile() {
        if (mUserProfile == null) {
            mUserProfile = new MutableLiveData<>();
            mUserProfile.setValue(Resource.success(getmSharedPrefsHelper().getPrefsUserData()));
            fetchSelfProfile(getmSharedPrefsHelper().getKeyPenname());
        }
        return mUserProfile;
    }
}
