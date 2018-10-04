package com.jullae.ui.tut;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.ui.fragments.ExploreFragment;
import com.jullae.ui.fragments.ProfileFragment;
import com.jullae.ui.home.homeFeed.HomeFeedFragment;
import com.jullae.ui.loginscreen.LoginActivity;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        if (AppDataManager.getInstance().getmSharedPrefsHelper().getvaluefromsharedprefBoolean("tut")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        viewpager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewpager);
    }


    private class PagerAdapter extends FragmentStatePagerAdapter {
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        private PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();

            switch (position) {
                case 0:
                    bundle.putInt("page", 1);

                    break;
                case 1:
                    bundle.putInt("page", 2);
                    break;

                case 2:
                    bundle.putInt("page", 3);

                    break;

                default:
                    break;


            }

            TutFragment tutFragment = new TutFragment();
            tutFragment.setArguments(bundle);
            return tutFragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }


        @Override
        public int getCount() {
            return 3;
        }


    }


}
