package com.jullae.ui.fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
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
import com.jullae.ui.home.profile.ProfileEditActivity;
import com.jullae.ui.home.profile.bookmarkTab.BookmarkTabFragment;
import com.jullae.ui.home.profile.draftTab.DraftTabFragment;
import com.jullae.ui.home.profile.message.ConversationAdapter;
import com.jullae.ui.home.profile.pictureTab.PictureTabFragment;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorActivity;
import com.jullae.ui.home.profile.storyTab.StoryTabFragment;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.DialogUtils;
import com.jullae.utils.GlideUtils;
import com.jullae.utils.MyProgressDialog;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

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
    private ConversationAdapter conversationAdapter;
    private View button_edit_profile;
    private FragmentProfileBinding binding;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBar;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int refreshMode = intent.getIntExtra(Constants.REFRESH_MODE, -1);
            Log.d("receiver", "Got message: " + refreshMode);
            switch (refreshMode) {
                case Constants.REFRESH_PROFILE:
                    mPresentor.loadProfile(mProfileModel.getPenname());
                    break;
            }
        }
    };


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

        appBar = view.findViewById(R.id.appbar);
        user_image = view.findViewById(R.id.image_avatar);

        button_edit_profile = view.findViewById(R.id.button_edit_profile);

        button_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // showEditProfileDialog();

                Intent i = new Intent(getContext(), ProfileEditActivity.class);
                i.putExtra("name", mProfileModel.getName());
                i.putExtra("bio", mProfileModel.getBio());
                i.putExtra("photo", mProfileModel.getUser_avatar());
                getContext().startActivity(i);
            }
        });


       /* //user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startImagePickActivity();
            }
        });*/
        binding.containerFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showFollowersDialog(getmContext(), mProfileModel.getId());
            }
        });
        binding.containerFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showFollowingDialog(getmContext(), mProfileModel.getId());
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
                showMenuOptions();
            }
        });

        setupTabIcons();

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresentor.loadProfile(mProfileModel.getPenname());
            }
        });

        mPresentor = new ProfileFragmentPresentor();

        if (getmContext() instanceof ProfileVisitorActivity) {
            LinearLayout close_container = (LinearLayout) inflater.inflate(R.layout.close_button, (CoordinatorLayout) view.findViewById(R.id.rootview), false);
            close_container.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getmContext().finish();
                }
            });

            ((CoordinatorLayout) view.findViewById(R.id.rootview)).addView(close_container);

            swipeRefreshLayout.setEnabled(false);
        } else {
            appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    Log.d(TAG, "onOffsetChanged: " + verticalOffset);
                    if (verticalOffset == 0) {
                        swipeRefreshLayout.setEnabled(true);
                    } else swipeRefreshLayout.setEnabled(false);
                }
            });
        }


        return view;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_outline_photo_24px_white);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_edit_white);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_outline_bookmark_border_24px);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_outline_save_24px);
    }

    private void startImagePickActivity() {
        Intent i = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setFixAspectRatio(true)

                .setRequestedSize(500, 500)
                .setMinCropResultSize(200, 200)
                .getIntent(getmContext());

        this.startActivityForResult(i, AppUtils.REQUEST_CODE_PROFILE_PIC_CAPTURE);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mProfileModel = mPresentor.getStaticUserData();


        binding.setProfileModel(mProfileModel);
        mPresentor.loadProfile(mProfileModel.getPenname());
        setupRefreshBroadcastListener();

    }


    private void setupRefreshBroadcastListener() {
        LocalBroadcastManager.getInstance(getmContext()).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.REFRESH_INTENT_FILTER));


    }

    @Override
    public void onProfileFetchSuccess(ProfileModel profileModel) {
        swipeRefreshLayout.setRefreshing(false);

        mProfileModel.setFollower_count(profileModel.getFollower_count());
        mProfileModel.setFollowing_count(profileModel.getFollowing_count());
        mProfileModel.setStories_count(profileModel.getStories_count());
        mProfileModel.setPictures_count(profileModel.getPictures_count());
        mProfileModel.setUser_avatar(profileModel.getUser_avatar());
        mProfileModel.setBio(profileModel.getBio());

        if (getmContext() instanceof HomeActivity)
            ((HomeActivity) getmContext()).updateNotificationIcon(profileModel.getUnread_notifications());

        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        setupTabIcons();


    }

    @Override
    public void onProfileFetchFail() {
        swipeRefreshLayout.setRefreshing(false);

    }

   /* private void showEditProfileDialog() {
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

       *//* view.findViewById(R.id.text_update_profile).setOnClickListener(new View.OnClickListener() {
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
        });*//*
        dialog.show();
    }

    private void showConversationDialog() {
        DialogUtils.showMessageDialog(getmContext());
    }
*/

    private void showPasswordChangeDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getmContext());
        final View view = getmContext().getLayoutInflater().inflate(R.layout.dialog_password_change, null);

        final EditText field_old_password = view.findViewById(R.id.field_old_password);
        final EditText field_new_password = view.findViewById(R.id.field_new_password);
        final EditText field_confirm_new_password = view.findViewById(R.id.field_confirm_new_password);


        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
      /*  view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/

        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(field_old_password.getText().toString().trim())) {
                    Toast.makeText(getmContext().getApplicationContext(), "Please enter your old password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(field_new_password.getText().toString().trim())) {
                    Toast.makeText(getmContext().getApplicationContext(), "Please enter your new password", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(field_confirm_new_password.getText().toString().trim())) {
                    Toast.makeText(getmContext().getApplicationContext(), "Please confirm again your new password", Toast.LENGTH_SHORT).show();

                } else if (field_confirm_new_password.getText().toString().trim().equals(field_new_password.getText().toString().trim())) {
                    Toast.makeText(getmContext().getApplicationContext(), "New Passwords don't match. Confirm again your new password", Toast.LENGTH_SHORT).show();

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

    private void showMenuOptions() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getmContext());
        View view;
        Log.d(TAG, "showMenuOptions: " + mPresentor.isEmailModeLogin());
        if (mPresentor.isEmailModeLogin())
            view = getmContext().getLayoutInflater().inflate(R.layout.profile_options_email, null);
        else
            view = getmContext().getLayoutInflater().inflate(R.layout.profile_options_without_email, null);


        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
        if (view.findViewById(R.id.menu1) != null)
            view.findViewById(R.id.menu1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPasswordChangeDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();

                        }
                    }, 100);


                }
            });
        view.findViewById(R.id.menu2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppUtils.showLogoutDialog(getmContext());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();

                    }
                }, 100);


            }
        });


        dialog.show();

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
        GlideUtils.loadImagefromUrl(getmContext(), mProfileModel.getUser_avatar(), user_image);
        Toast.makeText(getmContext().getApplicationContext(), "Failed to update avatar!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProfilePicUpdateSuccess(String profile_dp_url) {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        GlideUtils.loadImagefromUrl(getmContext(), profile_dp_url, user_image);
        Toast.makeText(getmContext().getApplicationContext(), "Avatar updated successfully!", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode);
        if (requestCode == AppUtils.REQUEST_CODE_PROFILE_PIC_CAPTURE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                onImagePickSuccess(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d(TAG, "onActivityResult: " + error);
                onImagePickFail();
            }
        }

    }

    private void onImagePickSuccess(Uri uri) {
        GlideUtils.loadImagefromUri(getmContext(), uri, user_image);
        File file = new File(uri.getPath());
        updateDpReq(file);
    }


    private void onImagePickFail() {

    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        LocalBroadcastManager.getInstance(getmContext()).unregisterReceiver(mMessageReceiver);

        super.onDestroyView();

    }


    public interface PasswordChangeListener {
        void onPasswordChangeSuccess();

        void onPasswordChangeFail();
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
        public int getCount() {

            return tabs.length;
        }


    }


}
