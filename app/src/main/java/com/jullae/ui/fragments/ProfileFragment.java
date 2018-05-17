package com.jullae.ui.fragments;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.ConversationModel;
import com.jullae.data.db.model.ProfileModel;
import com.jullae.databinding.FragmentProfileBinding;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.home.HomeActivity;
import com.jullae.ui.home.homeFeed.ProfileFragmentPresentor;
import com.jullae.ui.home.homeFeed.freshfeed.ProfileFragmentView;
import com.jullae.ui.home.profile.bookmarkTab.BookmarkTabFragment;
import com.jullae.ui.home.profile.draftTab.DraftTabFragment;
import com.jullae.ui.home.profile.message.ConversationAdapter;
import com.jullae.ui.home.profile.pictureTab.PictureTabFragment;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorActivity;
import com.jullae.ui.home.profile.storyTab.StoryTabFragment;
import com.jullae.utils.AppUtils;
import com.jullae.utils.GlideUtils;
import com.jullae.utils.ReqListener;
import com.jullae.utils.dialog.MyProgressDialog;

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
    private ProfileFragmentPresentor mPresentor;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private ProfileModel mProfileModel;
    private ImageView button_message;
    private ConversationAdapter conversationAdapter;
    private View button_edit_profile;
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        view = binding.getRoot();
        // view = inflater.inflate(R.layout.fragment_profile, container, false);
        user_image = view.findViewById(R.id.image_avatar);

        button_message = view.findViewById(R.id.button_message);
        button_edit_profile = view.findViewById(R.id.button_edit_profile);

        button_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });

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
                        GlideUtils.loadImagefromUrl(getmContext(), (uri).toString(), user_image);
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
        view.findViewById(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuOptions(v);
            }
        });

        mPresentor = new ProfileFragmentPresentor();

        if (getmContext() instanceof ProfileVisitorActivity) {
            user_image.setOnClickListener(null);
            LinearLayout close_container = (LinearLayout) inflater.inflate(R.layout.close_button, (CoordinatorLayout) view.findViewById(R.id.rootview), false);
            close_container.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getmContext().finish();
                }
            });

            ((CoordinatorLayout) view.findViewById(R.id.rootview)).addView(close_container);

        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mProfileModel = mPresentor.getStaticUserData();


        binding.setProfileModel(mProfileModel);
        mPresentor.loadProfile(mProfileModel.getPenname());
    }

    @Override
    public void onProfileFetchSuccess(ProfileModel profileModel) {

        mProfileModel.setFollower_count(profileModel.getFollower_count());
        mProfileModel.setFollowing_count(profileModel.getFollowing_count());
        mProfileModel.setStories_count(profileModel.getStories_count());
        mProfileModel.setPictures_count(profileModel.getPictures_count());
        if (getmContext() instanceof HomeActivity)
            ((HomeActivity) getmContext()).updateNotificationIcon(profileModel.getUnread_notifications());

    }

    @Override
    public void onProfileFetchFail() {

    }

    private void showEditProfileDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getmContext());
        View view = getmContext().getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);
        final EditText fieldName = view.findViewById(R.id.field_name);
        final EditText fieldBio = view.findViewById(R.id.field_bio);

        fieldName.setText(mProfileModel.getName());
        fieldBio.setText(mProfileModel.getBio());
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.text_update_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresentor.updateProfile(fieldName.getText().toString().trim(), fieldBio.getText().toString(), new ReqListener() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                        Toast.makeText(getmContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(getmContext(), "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        dialog.show();
    }

    private void showConversationDialog() {
        showMessageDialog();
    }

    private void showMessageDialog() {

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

    private void showPasswordChangeDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getmContext());
        final View view = getmContext().getLayoutInflater().inflate(R.layout.dialog_password_change, null);

        final EditText field_old_password = view.findViewById(R.id.field_old_password);
        final EditText field_new_password = view.findViewById(R.id.field_new_password);


        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
      /*  view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(field_old_password.getText().toString())) {
                    Toast.makeText(getmContext().getApplicationContext(), "Please enter your old password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(field_old_password.getText().toString())) {
                    Toast.makeText(getmContext().getApplicationContext(), "Please enter your new password", Toast.LENGTH_SHORT).show();

                } else {
                    view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.submit).setVisibility(View.INVISIBLE);
                    mPresentor.makePasswordChange(field_old_password.getText().toString(), field_new_password.getText().toString(), new PasswordChangeListener() {
                        @Override
                        public void onPasswordChangeSuccess() {

                            dialog.dismiss();
                            Toast.makeText(getmContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPasswordChangeFail() {
                            view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.submit).setVisibility(View.INVISIBLE);
                            Toast.makeText(getmContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            }
        });

        dialog.show();


    }

    @Override
    public void onConversationListFetchSuccess(List<ConversationModel.Conversation> conversationList) {
        if (conversationAdapter != null)
            conversationAdapter.add(conversationList);
    }

    @Override
    public void onConversationListFetchFail() {

    }

    private void showMenuOptions(View ivMore) {
        PopupMenu popup = new PopupMenu(getmContext(), ivMore);
        //inflating menu from xml resource

        if (mPresentor.isEmailModeLogin())
            popup.inflate(R.menu.profile_options_email);
        else popup.inflate(R.menu.profile_options_without_email);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        //handle menu1 click
                        showPasswordChangeDialog();
                        break;
                    case R.id.menu2:
                        //handle menu2 click
                        AppUtils.showLogoutDialog(getmContext());
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }

    @Override
    public void showProgressBar() {
        MyProgressDialog.showProgressDialog(getmContext(), "Please Wait!");
    }

    @Override
    public void hideProgressBar() {
        MyProgressDialog.dismissProgressDialog();

    }

    private void updateDpReq(File file) {
        mPresentor.makeDpReq(file);
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
        GlideUtils.loadImagefromUrl(getmContext(), mProfileModel.getUser_dp_url(), user_image);
        Toast.makeText(getmContext().getApplicationContext(), "Failed to update avatar!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProfilePicUpdateSuccess(String profile_dp_url) {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        GlideUtils.loadImagefromUrl(getmContext(), profile_dp_url, user_image);
        Toast.makeText(getmContext().getApplicationContext(), "Avatar updated successfully!", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        if (getmContext() instanceof HomeActivity)
            ((HomeActivity) getmContext()).removeListener();
        super.onDestroyView();

    }


    public interface PasswordChangeListener {
        void onPasswordChangeSuccess();

        void onPasswordChangeFail();
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
            Bundle bundle = new Bundle();
            bundle.putString("penname", AppDataManager.getInstance().getmSharedPrefsHelper().getKeyPenname());
            switch (position) {
                case 0:
                    PictureTabFragment pictureTabFragment = new PictureTabFragment();
                    pictureTabFragment.setArguments(bundle);
                    return pictureTabFragment;

                case 1:
                    StoryTabFragment storyTabFragment = new StoryTabFragment();
                    storyTabFragment.setArguments(bundle);
                    return storyTabFragment;

                case 2:
                    return new BookmarkTabFragment();
                case 3:
                    return new DraftTabFragment();
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
