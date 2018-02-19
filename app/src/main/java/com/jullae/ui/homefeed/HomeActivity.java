package com.jullae.ui.homefeed;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.constant.AppConstant;
import com.jullae.retrofit.APIError;
import com.jullae.retrofit.ApiInterface;
import com.jullae.retrofit.CommonResponse;
import com.jullae.retrofit.MultipartParams;
import com.jullae.retrofit.ResponseResolver;
import com.jullae.retrofit.RestClient;
import com.jullae.ui.adapters.GridAdapter;
import com.jullae.ui.adapters.ViewPagerAdapter;
import com.jullae.ui.base.BaseActivity;
import com.jullae.ui.fragments.AddPostDialogFragment;
import com.jullae.ui.fragments.EditPostFragment;
import com.jullae.ui.fragments.HomeFeedFragments;
import com.jullae.ui.fragments.LikeDialogFragment;
import com.jullae.ui.fragments.ProfileFragment;
import com.jullae.ui.fragments.ShapeFragment;
import com.jullae.ui.fragments.StoryDialogFragment;
import com.jullae.ui.storydetails.StoryDetailsActivity;
import com.jullae.utils.Utils;
import com.jullae.utils.imagepicker.ImageChooser;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Rahul Abrol on 12/13/17.
 * <p>
 * Class @{@link HomeActivity} used to hold the
 * feed items, add, edit stories,images,remove etc.
 */
public class HomeActivity extends BaseActivity implements HomeFeedFragments.FeedListener,
        GridAdapter.StoryClickListener, StoryDialogFragment.UpdateFeed,
        AddPostDialogFragment.PicListner, ImageChooser.OnImageSelectListener {

    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private Button addButton;
    private ViewPager viewPager;
    private ApiInterface client;
    private File photoFile;
    private ImageChooser imageChooser;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_feed);
        //client
        client = RestClient.getApiInterface();

        //Initialize the button and bottomNavigation for listener.
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        //Setup pager and its adapter.
        pagerSetup();

        //Find bottom Sheet ID
        View bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(0);
    }

    /**
     * pager setup;
     */
    private void pagerSetup() {
        viewPager = findViewById(R.id.viewPager);
        //load all pages only once.
        viewPager.setOffscreenPageLimit(3);
        //set Adapter.
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // add all fragment name into the key.
        adapter.addFrag(new HomeFeedFragments());
        adapter.addFrag(new ShapeFragment());
        adapter.addFrag(new ProfileFragment());

        //set Adapter of pager.
        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            int iconId = -1;
            switch (i) {
                case 0:
                    iconId = R.drawable.ic_home_black_24dp;
                    break;
                case 1:
                    iconId = R.drawable.ic_language_black_24dp;
                    break;
                case 2:
                    iconId = R.drawable.ic_profile;
                    break;
                default:
                    break;
            }
            tabLayout.getTabAt(i).setIcon(iconId);
        }
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
                if (position == 0) {
                    addButton.setVisibility(View.VISIBLE);
                } else {
                    addButton.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.addButton:
                //Show the Bottom Sheet Fragment
                bottomSheetDialogFragment = new AddPostDialogFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getClass().getSimpleName());
                break;
            default:
                break;
        }
    }

    /**
     * Show dialog.
     *
     * @param id id of the user.
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
                bottomSheetDialogFragment = EditPostFragment.getInstance(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getClass().getSimpleName());
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
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0);
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
     * @param id           id of item
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

    @Override
    public void addPicture() {
        imageChooser = new ImageChooser.Builder(HomeActivity.this).build();
        imageChooser.selectImage(HomeActivity.this);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
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
     * @param title id.
     */
    private void uploadPicApi(final String title) {
        MultipartParams.Builder multipartParams = new MultipartParams.Builder()
                .add("picture_title", title)
                .addFile("image", photoFile);

        Call<CommonResponse> data = client.uploadPic(multipartParams.build().getMap());
        data.enqueue(new ResponseResolver<CommonResponse>(HomeActivity.this, false, false) {
            @Override
            public void success(final CommonResponse feedModel) {
                updateFeedFragment();
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(HomeActivity.this, addButton, error.getMessage());
            }
        });
    }

    /**
     * Method used to update the feed.
     */
    private void updateFeedFragment() {
        Fragment page = getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
        // based on the current position you can then cast the page to the correct
        // class and call the method:
        if (viewPager.getCurrentItem() == 0 && page != null) {
            ((HomeFeedFragments) page).updateList();
        }
    }

    @Override
    public void updateFeed() {
//        after successfully get the response in storyDialogFragment update feed data
        updateFeedFragment();
    }
}
