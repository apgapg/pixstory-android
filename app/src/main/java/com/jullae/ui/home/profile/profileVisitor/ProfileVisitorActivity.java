package com.jullae.ui.home.profile.profileVisitor;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.ui.fragments.ProfileFragment;

public class ProfileVisitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_visitor);
        String penname = getIntent().getStringExtra("penname");
        if (penname == null)
            throw new NullPointerException("penname cannot be empty! Make sure you have passed penname to the activity");

        if (!penname.equals(AppDataManager.getInstance().getmSharedPrefsHelper().getKeyPenname()))
            showProfileVisitorFragment(penname);
        else showProfileFragment();
    }

    private void showProfileFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, new ProfileFragment()).commit();
    }


    private void showProfileVisitorFragment(String penname) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfileVisitorFragment profileVisitorFragment = new ProfileVisitorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("penname", penname);
        profileVisitorFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, profileVisitorFragment).commit();

    }

}
