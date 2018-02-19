package com.jullae.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Abrol on 29/12/17.
 * pager adapter.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param manager managed of fragment.
     */
    public ViewPagerAdapter(final FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(final int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * Add fragment here and set title.
     *
     * @param fragment frag.
     */
    public void addFrag(final Fragment fragment) {
        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return null;
    }
}
