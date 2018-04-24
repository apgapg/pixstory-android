package com.jullae.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.model.UserPrefsModel;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.homefeed.ProfileFragmentPresentor;
import com.jullae.ui.homefeed.freshfeed.ProfileFragmentView;
import com.jullae.ui.profile.StoryTabFragment;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * Profile fragment.
 */

public class ProfileFragment extends BaseFragment implements ProfileFragmentView {


    private View view;
    private ImageView user_image;
    private TextView user_name, user_penname, user_bio, user_followers, user_following, user_stories, user_pictures;
    private ProfileFragmentPresentor mPresentor;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        user_image = view.findViewById(R.id.image_avatar);
        user_name = view.findViewById(R.id.text_name);
        user_penname = view.findViewById(R.id.text_penname);
        user_bio = view.findViewById(R.id.user_bio);
        user_followers = view.findViewById(R.id.text_followers);
        user_following = view.findViewById(R.id.text_following);
        user_stories = view.findViewById(R.id.text_stories);
        user_pictures = view.findViewById(R.id.text_pictures);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(pagerAdapter);

        mPresentor = new ProfileFragmentPresentor(((AppController) getmContext().getApplication()).getmAppDataManager());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);


        UserPrefsModel userPrefsModel = mPresentor.getStaticUserData();
        Glide.with(getmContext()).load(userPrefsModel.getUser_dp_url()).into(user_image);
        user_name.setText(userPrefsModel.getUser_name());
        user_penname.setText(userPrefsModel.getUser_penname());
        user_bio.setText(userPrefsModel.getUser_bio());


    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        super.onDestroyView();

    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        String[] tabs = {"Pics", "Story", "Bookmark"};

        private PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    StoryTabFragment storyTabFragment = new StoryTabFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("position", 1);
                    storyTabFragment.setArguments(bundle1);
                    return storyTabFragment;

                case 1:
                    StoryTabFragment storyTabFragment2 = new StoryTabFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("position", 1);
                    storyTabFragment2.setArguments(bundle2);
                    return storyTabFragment2;
                case 2:
                    StoryTabFragment storyTabFragment3 = new StoryTabFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("position", 2);
                    storyTabFragment3.setArguments(bundle3);
                    return storyTabFragment3;

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
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return tabs[0];
                case 1:
                    return tabs[1];
                case 2:
                    return tabs[2];
                default:
                    return super.getPageTitle(position);
            }
        }

        @Override
        public int getCount() {

            return 3;
        }


    }
}
