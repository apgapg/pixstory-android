package com.jullae.ui.homefeed;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.constant.AppConstant;
import com.jullae.retrofit.ApiInterface;
import com.jullae.retrofit.MultipartParams;
import com.jullae.retrofit.RestClient;
import com.jullae.ui.adapters.GridAdapter;
import com.jullae.ui.base.BaseActivity;
import com.jullae.ui.fragments.HomeFragment;
import com.jullae.ui.fragments.LikeDialogFragment;
import com.jullae.ui.fragments.SearchFragment;
import com.jullae.ui.fragments.StoryDialogFragment;
import com.jullae.ui.homefeed.freshfeed.FreshFeedFragment;
import com.jullae.ui.storydetails.StoryDetailsActivity;
import com.jullae.utils.Utils;
import com.jullae.utils.imagepicker.ImageChooser;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.util.List;


/**
 * Created by Rahul Abrol on 12/13/17.
 * <p>
 * Class @{@link HomeActivity} used to hold the
 * feed items, add, edit stories,images,remove etc.
 */
public class HomeActivity extends BaseActivity implements HomeFeedFragmentold.FeedListener,
        GridAdapter.StoryClickListener, StoryDialogFragment.UpdateFeed,
        ImageChooser.OnImageSelectListener {

    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private Button addButton;
    private ApiInterface client;
    private File photoFile;
    private ImageChooser imageChooser;
    private ImageView tab_home, tab_explore, tab_profile;
    private BottomSheetBehavior<View> mBottomSheetBehavior;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_feed);
        showFragment(new HomeFragment(), false);
        //client
        client = RestClient.getApiInterface();

        //Initialize the button and bottomNavigation for listener.
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        tab_explore = findViewById(R.id.tab_explore);
        tab_profile = findViewById(R.id.tab_profile);
        tab_home = findViewById(R.id.tab_home);

        tab_explore.setOnClickListener(this);
        tab_profile.setOnClickListener(this);
        tab_home.setOnClickListener(this);

        //Find bottom Sheet ID
        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    findViewById(R.id.bg).setVisibility(View.GONE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                findViewById(R.id.bg).setVisibility(View.VISIBLE);
                findViewById(R.id.bg).setAlpha(slideOffset);
            }
        });

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });
        findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });
    }


    private void showFragment(Fragment fragment, boolean shouldAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           /* if (b)
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
           */
        if (shouldAddToBackStack)
            fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
        else fragmentTransaction.replace(R.id.container, fragment).commit();


    }


    public void showSearchFragment() {
        showFragment(new SearchFragment(), true);
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.tab_explore:
                showHomeFragment(1);
                break;
            case R.id.tab_home:
                showHomeFragment(0);
                break;
            case R.id.tab_profile:
                showHomeFragment(2);
                break;
            default:
                break;
        }
    }

    private void showHomeFragment(int i) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof HomeFragment && fragment.isVisible())
            ((HomeFragment) getSupportFragmentManager().findFragmentById(R.id.container)).showFragment(i);
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentManager.popBackStackImmediate();

            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.container, homeFragment).commit();

        }
    }

    /**
     * Show dialog.
     *
     * @param id story_id of the user.
     */
    public void showStoryDialog(final int id) {
        // Create an instance of the dialog fragment and show it.
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ID, id);
        DialogFragment dialog = StoryDialogFragment.getInstance(bundle);
        dialog.show(getFragmentManager(), dialog.getClass().getName());
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    @Override
    public void onFeedClick(final int position, final String tag, final int id) {
        switch (tag) {
            case ITEM_LIKE:
                showLikeDialog(position, false);
                break;
            case ITEM_EDIT:
                showStoryDialog(0);
                break;
            case ITEM_LOC:
                Toast.makeText(this, "Loc", Toast.LENGTH_SHORT).show();
                break;
            case ITEM_USER_NAME:
                Toast.makeText(this, "UserName", Toast.LENGTH_SHORT).show();
                break;
            case ITEM_STORY_PIC:
                Toast.makeText(this, "StoryPic", Toast.LENGTH_SHORT).show();
                break;
            case ITEM_USER_PIC:
                Toast.makeText(this, "UserPic", Toast.LENGTH_SHORT).show();
                break;
            case ITEM_MORE:
                //Show the Bottom Sheet Fragment
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_ID, id);
                /*bottomSheetDialogFragment = EditPostFragment.getInstance(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getClass().getSimpleName());
                */
                break;
            default:
                break;
        }
    }

    @Override
    public void onScroll(final boolean isHide) {
//        if (isHide) {
//            addButton.setVisibility(View.GONE);
//        } else {
//            addButton.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof HomeFragment && getSupportFragmentManager().findFragmentById(R.id.container).isVisible()) {
            HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.container);
            if (!homeFragment.onBackPressByUser())
                super.onBackPressed();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStoryClick(final int position, final int id, final String tag) {
        switch (tag) {
            case AppConstant.TAG_LIKE:
                showLikeDialog(id, true);
                break;
            case AppConstant.TAG_COMMENT:
                Toast.makeText(this, "comments", Toast.LENGTH_SHORT).show();
                break;
            case AppConstant.TAG_STORY:
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_ID, id);
                launchScreen(StoryDetailsActivity.class, bundle);
                break;
            default:
                break;

        }
    }

    /**
     * Show like dialog.
     *
     * @param id           story_id of item
     * @param isStoryLikes identify user.
     */
    private void showLikeDialog(final int id, final boolean isStoryLikes) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.EXTRA_ID, id);
        bundle.putBoolean(AppConstant.EXTRA_ITEM_TYPE, isStoryLikes);
        DialogFragment likeDialog = LikeDialogFragment.getInstance(bundle);
        likeDialog.show(getFragmentManager(), likeDialog.getClass().getSimpleName());
        likeDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    @Override
    public void onAddFeed(final int position, final int id) {
        showStoryDialog(id);
    }

   /* @Override
    public void addPicture() {
        imageChooser = new ImageChooser.Builder(HomeActivity.this).build();
        imageChooser.selectImage(HomeActivity.this);
    }*/

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageChooser.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loadImage(final List<ChosenImage> list) {
        try {
            photoFile = new File(list.get(0).getOriginalPath());
            uploadPicApi(list.get(0).getDisplayName());
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showSnackbar(this, addButton, getString(R.string.unable_to_load_image));
        }
    }

    @Override
    public void croppedImage(final File mCroppedImage) {

    }


    /**
     * Get all the feeds.
     *
     * @param title story_id.
     */
    private void uploadPicApi(final String title) {
        MultipartParams.Builder multipartParams = new MultipartParams.Builder()
                .add("picture_title", title)
                .addFile("image", photoFile);

       /* Call<CommonResponse> data = client.uploadPic(multipartParams.build().getMap());
        data.enqueue(new ResponseResolver<CommonResponse>(HomeActivity.this, false, false) {
            @Override
            public void success(final CommonResponse feedModel) {
                updateFeedFragment();
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(HomeActivity.this, addButton, error.getMessage());
            }
        });*/
    }

    /**
     * Method used to update the feed.
     */
   /* private void updateFeedFragment() {
        Fragment page = getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.story_id.viewPager + ":" + viewPager.getCurrentItem());
        // based on the current position you can then cast the page to the correct
        // class and call the method:
        if (viewPager.getCurrentItem() == 0 && page != null) {
            ((HomeFeedFragmentold) page).updateList();
        }
    }*/
    @Override
    public void updateFeed() {
//        after successfully get the response in storyDialogFragment update feed data
        // updateFeedFragment();
    }

    public void showFreshFeedFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        FreshFeedFragment freshFeedFragment = new FreshFeedFragment();
        freshFeedFragment.setArguments(bundle);
        showFragment(freshFeedFragment, true);
    }
}
