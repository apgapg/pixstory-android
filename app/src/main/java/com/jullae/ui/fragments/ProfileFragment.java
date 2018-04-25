package com.jullae.ui.fragments;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.model.ConversationModel;
import com.jullae.model.UserPrefsModel;
import com.jullae.ui.adapters.ConversationAdapter;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.homefeed.HomeActivity;
import com.jullae.ui.homefeed.ProfileFragmentPresentor;
import com.jullae.ui.homefeed.freshfeed.ProfileFragmentView;
import com.jullae.ui.profileSelf.CommonTabFragment;
import com.jullae.ui.profileSelf.ProfileMainModel;
import com.jullae.ui.profileSelf.draftTab.DraftTabFragment;

import java.io.File;
import java.util.List;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * Profile fragment.
 */

public class ProfileFragment extends BaseFragment implements ProfileFragmentView {


    private static final String TAG = ProfileFragment.class.getName();
    private View view;
    private ImageView user_image;
    private TextView user_name, user_penname, user_bio, user_followers, user_following, user_stories, user_pictures;
    private ProfileFragmentPresentor mPresentor;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private UserPrefsModel userPrefsModel;
    private ImageView button_message;
    private ConversationAdapter conversationAdapter;

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
        button_message = view.findViewById(R.id.button_message);

        button_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConversationDialog();
            }
        });
        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getmContext()).showCropImage(new ImagePickListener() {
                    @Override
                    public void onImagePickSucccess(Uri uri) {
                        Glide.with(getmContext()).load(uri).into(user_image);
                        File file = new File(uri.getPath());
                        updateDpReq(file);
                    }

                    @Override
                    public void onImagePickFail() {
                        Toast.makeText(getmContext().getApplicationContext(), "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(pagerAdapter);

        mPresentor = new ProfileFragmentPresentor(((AppController) getmContext().getApplication()).getmAppDataManager());

        return view;
    }

    private void showConversationDialog() {
        showLikesDialog();
    }

    private void showLikesDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getmContext());
        View view = getmContext().getLayoutInflater().inflate(R.layout.dialog_conversation, null);

        setupRecyclerView(view);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        conversationAdapter = new ConversationAdapter(getmContext());
        recyclerView.setAdapter(conversationAdapter);

        mPresentor.getConversationList();
    }

    @Override
    public void onConversationListFetchSuccess(List<ConversationModel.Conversation> conversationList) {
        if (conversationAdapter != null)
            conversationAdapter.add(conversationList);
    }

    @Override
    public void onConversationListFetchFail() {

    }

    private void updateDpReq(File file) {
        mPresentor.makeDpReq(file);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        userPrefsModel = mPresentor.getStaticUserData();


        Glide.with(getmContext()).load(userPrefsModel.getUser_dp_url()).into(user_image);
        user_name.setText(userPrefsModel.getUser_name());
        user_penname.setText(userPrefsModel.getUser_penname());
        user_bio.setText(userPrefsModel.getUser_bio());


        mPresentor.loadProfile(userPrefsModel.getUser_penname());


    }

    @Override
    public void showPicUploadProgress() {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePicUploadProgress() {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);

    }

    @Override
    public void onProfilePicUpdateFail() {
        Glide.with(getmContext()).load(userPrefsModel.getUser_dp_url()).into(user_image);
        Toast.makeText(getmContext().getApplicationContext(), "Failed to update avatar!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProfilePicUpdateSuccess(String profile_dp_url) {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        Glide.with(getmContext()).load(profile_dp_url).into(user_image);
        Toast.makeText(getmContext().getApplicationContext(), "Avatar updated successfully!", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onProfileFetchSuccess(ProfileMainModel.ProfileModel profileModel) {
      /*  Glide.with(getmContext()).load(profileModel.getUser_dp_url()).into(user_image);
        user_name.setText(profileModel.getName());
        user_penname.setText(profileModel.getPenname());
        user_bio.setText(profileModel.getBio());*/
        user_followers.setText(profileModel.getFollower_count());
        user_following.setText(profileModel.getFollowing_count());
        user_stories.setText(profileModel.getStories_count());
        user_pictures.setText(profileModel.getPictures_count());
    }

    @Override
    public void onProfileFetchFail() {

    }


    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        ((HomeActivity) getmContext()).removeListener();
        super.onDestroyView();

    }


    public interface ImagePickListener {
        void onImagePickSucccess(Uri uri);

        void onImagePickFail();
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        String[] tabs = {"Pics", "Story", "Bookmark", "Drafts"};

        private PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    CommonTabFragment commonTabFragment = new CommonTabFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("position", 1);
                    commonTabFragment.setArguments(bundle1);
                    return commonTabFragment;

                case 1:
                    CommonTabFragment commonTabFragment2 = new CommonTabFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("position", 1);
                    commonTabFragment2.setArguments(bundle2);
                    return commonTabFragment2;
                case 2:
                    CommonTabFragment commonTabFragment3 = new CommonTabFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("position", 2);
                    commonTabFragment3.setArguments(bundle3);
                    return commonTabFragment3;

                case 3:
                    DraftTabFragment draftTabFragment = new DraftTabFragment();
                    Bundle bundle4 = new Bundle();
                    bundle4.putInt("position", 3);
                    draftTabFragment.setArguments(bundle4);
                    return draftTabFragment;

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
                case 3:
                    return tabs[3];
                default:
                    return super.getPageTitle(position);
            }
        }

        @Override
        public int getCount() {

            return tabs.length;
        }


    }


}
