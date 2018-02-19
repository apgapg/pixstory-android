//package com.jullae.ui.adapters;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//
//import java.util.List;
//
///**
// * Created by Rahul Abrol on 12/18/17.
// * <p>
// * Pager Adapter.
// */
//public class SlideAdapter extends FragmentStatePagerAdapter {
//    private List<Fragment> fragments;
//
//    /**
//     * Instantiates a new Pager adapter.
//     *
//     * @param fm        the fm
//     * @param fragments the fragments
//     */
//    public SlideAdapter(final FragmentManager fm, final List<Fragment> fragments) {
//        super(fm);
//        this.fragments = fragments;
//
//    }
//
//    @Override
//    public Fragment getItem(final int position) {
//        return fragments.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        if (fragments == null) {
//            return 0;
//        } else {
//            return fragments.size();
//        }
//    }
//}