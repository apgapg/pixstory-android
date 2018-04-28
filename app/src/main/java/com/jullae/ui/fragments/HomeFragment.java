package com.jullae.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.home.HomeActivity;
import com.jullae.ui.home.homeFeed.HomeFeedFragment;

public class HomeFragment extends BaseFragment {

    private View view;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_home, container, false);
        pagerSetup();
        if (getArguments() != null && getArguments().containsKey("position"))
            viewPager.setCurrentItem(getArguments().getInt("position"));
        return view;
    }

    private void pagerSetup() {
        viewPager = view.findViewById(R.id.viewPager);
        //load all pages only once.
        viewPager.setOffscreenPageLimit(2);
        //set Adapter.
        PagerAdapter adapter = new PagerAdapter(((HomeActivity) getmContext()).getSupportFragmentManager());

        //set Adapter of pager.
        viewPager.setAdapter(adapter);

        //setupTabLayout();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }

            @Override
            public void onPageSelected(final int position) {
                //if Fragment 0 is opened then show Button else hide.
               /* if (position == 0) {
                    addButton.setVisibility(View.VISIBLE);
                } else {
                    addButton.setVisibility(View.GONE);
                }*/
            }
        });

    }


    public boolean onBackPressByUser() {
        if (isAdded()) {
            if (viewPager.getCurrentItem() != 0) {
                viewPager.setCurrentItem(0);
                return true;
            } else return false;

        }
        return false;
    }

    public void showFragment(int i) {
        if (isAdded())
            viewPager.setCurrentItem(i);
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        private PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFeedFragment();
                case 1:
                    return new ExploreFragment();
                case 2:
                    return new ProfileFragment();
                default:
                    return null;
            }
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
