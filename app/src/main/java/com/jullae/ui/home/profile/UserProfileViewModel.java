package com.jullae.ui.home.profile;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.ProfileModel;
import com.jullae.utils.Resource;

public class UserProfileViewModel extends ViewModel {

   public UserProfileViewModel() {

    }

    public MutableLiveData<Resource<ProfileModel>> getmUserProfileModel(String penname) {
       return  AppDataManager.getInstance().getmUserProfile(penname);


    }
}
