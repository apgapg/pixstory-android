package com.jullae.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.SearchPeopleMainModel;
import com.jullae.ui.adapters.SearchPersonAdapter;
import com.jullae.ui.base.BaseActivity;
import com.jullae.ui.fragments.HomeFragment;
import com.jullae.ui.home.addStory.AddStoryFragment;
import com.jullae.ui.home.homeFeed.freshfeed.FreshFeedFragment;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorActivity;
import com.jullae.ui.notification.NotificationActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.DialogUtils;
import com.jullae.utils.KeyboardUtils;
import com.jullae.utils.MyProgressDialog;
import com.kbeanie.multipicker.api.ImagePicker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;


/**
 * Created by Rahul Abrol on 12/13/17.
 * <p>
 * Class @{@link HomeActivity} used to hold the
 * feed items, add, edit stories,images,remove etc.
 */
public class HomeActivity extends BaseActivity implements HomeActivityView, View.OnClickListener {
    private static final String TAG = HomeActivity.class.getName();
    private static final int CAMERA_REQUEST = 24;
    private static final int REQ_CODE_ADD_PICTURE = 45;
    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private FloatingActionButton fab;
    private ImageView tab_home, tab_explore, tab_profile;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private ImagePicker imagePicker;
    private Uri imageToUploadUri;
    private HomeActivityPresentor mPresentor;
    private ImageView button_notification;
    private View button_search;
    private AutoCompleteTextView autoCompleteTextView;
    private AppBarLayout appBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_feed);
        mPresentor = new HomeActivityPresentor();
        mPresentor.attachView(this);


        showHomeFragment();

        if (getIntent().getBooleanExtra("showprofile", false)) {
            showHomeFragment(2);
        }
        //client

        //Initialize the button and bottomNavigation for listener.
        fab = findViewById(R.id.addButton);
        appBar = findViewById(R.id.appbar);
        setUpSearchButton();
        tab_explore = findViewById(R.id.tab_explore);
        tab_profile = findViewById(R.id.tab_profile);
        tab_home = findViewById(R.id.tab_home);

        tab_explore.setOnClickListener(this);
        tab_profile.setOnClickListener(this);
        tab_home.setOnClickListener(this);

        tab_home.setColorFilter(getResources().getColor(R.color.colorPrimary));


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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptions();
             /*   if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    fab.hide();
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    fab.show();
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }*/
            }
        });


        findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });


        findViewById(R.id.text_add_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showAddImageOption();
                        // showImageChooseDialog();

                    }
                }, 300);
            }
        });
        findViewById(R.id.text_add_story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        showFragment(new AddStoryFragment(), true);                        // showImageChooseDialog();

                    }
                }, 300);
            }
        });

        button_notification = findViewById(R.id.image_notification);
        button_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_notification.setImageResource(R.drawable.ic_bell_normal);
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                //showFragment(new NotificationFragment(), true);
            }
        });

        findViewById(R.id.image_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showMessageDialog(HomeActivity.this);
            }
        });

    }

    public void showOptions() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        final View view = HomeActivity.this.getLayoutInflater().inflate(R.layout.bottom_sheet_content, null);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();


        view.findViewById(R.id.text_add_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddImageOption();
                dialog.hide();
            }
        });

        view.findViewById(R.id.text_add_story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new AddStoryFragment(), true);
                dialog.hide();

            }
        });


        dialog.show();
    }

    private void setUpSearchButton() {
        button_search = findViewById(R.id.image_search);

        autoCompleteTextView = findViewById(R.id.search_person);
        final SearchPersonAdapter searchPersonAdapter = new SearchPersonAdapter(this);
        autoCompleteTextView.setAdapter(searchPersonAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                KeyboardUtils.hideKeyboard(HomeActivity.this);

                SearchPeopleMainModel.SearchPeopleModel searchPeopleModel = (((SearchPersonAdapter) parent.getAdapter())).getItemAtPosition(position);
                final String penname = searchPeopleModel.getUser_penname();
                autoCompleteTextView.setText(penname);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!penname.equals(AppDataManager.getInstance().getmSharedPrefsHelper().getKeyPenname())) {
                            Intent intent = new Intent(HomeActivity.this, ProfileVisitorActivity.class);
                            intent.putExtra("penname", penname);
                            startActivity(intent);
                        } else showHomeFragment(2);
                        cleanUpSearchContainer();
                    }
                }, 500);
            }
        })
        ;

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.search_container).setVisibility(View.VISIBLE);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(autoCompleteTextView.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                autoCompleteTextView.requestFocus();

            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void cleanUpSearchContainer() {
        findViewById(R.id.search_container).setVisibility(View.GONE);
        autoCompleteTextView.setText("");
        KeyboardUtils.hideKeyboard(this);
    }

    public void showAddImageOption() {
        Intent i = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(900, 600)
                .setFixAspectRatio(true)

                .setRequestedSize(900, 600)
                .setMinCropResultSize(600, 400)
                .getIntent(this);
        startActivityForResult(i, REQ_CODE_ADD_PICTURE);
    }


    public void showFragment(Fragment fragment, boolean shouldAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (shouldAddToBackStack)
            fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
        else fragmentTransaction.replace(R.id.container, fragment).commit();


    }

    private void showHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new HomeFragment(), HomeFragment.class.getName()).commit();


    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {

            case R.id.tab_explore:
                tab_home.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                tab_explore.setColorFilter(getResources().getColor(R.color.colorPrimary));
                tab_profile.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                showHomeFragment(1);
                fab.hide();
                break;
            case R.id.tab_home:
                tab_home.setColorFilter(getResources().getColor(R.color.colorPrimary));
                tab_explore.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                tab_profile.setColorFilter(getResources().getColor(R.color.tab_icon_grey));

                showHomeFragment(0);
                fab.show();
                break;
            case R.id.tab_profile:
                tab_home.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                tab_explore.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                tab_profile.setColorFilter(getResources().getColor(R.color.colorPrimary));

                showHomeFragment(2);
                fab.hide();

                break;
            default:
                break;
        }
    }

    private void showHomeFragment(int i) {
        getSupportFragmentManager().popBackStackImmediate();
        ((HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName())).showFragment(i);

    }

    @Override
    public void onBackPressed() {

        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (findViewById(R.id.search_container).getVisibility() == View.VISIBLE) {
            cleanUpSearchContainer();
        } else if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof HomeFragment && getSupportFragmentManager().findFragmentById(R.id.container).isVisible()) {
            HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.container);
            if (!homeFragment.onBackPressByUser())
                super.onBackPressed();

        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode);
        if (requestCode == REQ_CODE_ADD_PICTURE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                showAddPictureDialog(imageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(HomeActivity.this.getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onActivityResult: " + error);

            }
        }


        if (requestCode == AppUtils.REQUEST_CODE_SEARCH_TAG && resultCode == Activity.RESULT_OK) {
            AppUtils.showSearchActivity(HomeActivity.this, data.getStringExtra("searchtag"));
        }
        if (requestCode == AppUtils.REQUEST_CODE_WRITESTORY_FROM_PICTURE_TAB && resultCode == Activity.RESULT_OK) {
            AppUtils.sendRefreshBroadcast(HomeActivity.this, Constants.REFRESH_PICTURES_TAB);
        }
    }


    private void showAddPictureDialog(final Uri uri) {

        File file = new File(uri.getPath());
        mPresentor.submitPicture(file);


    /*    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_add_picture, null);

        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

        final EditText field_title = view.findViewById(R.id.field_title);

        GlideUtils.loadImagefromUri(HomeActivity.this, uri, (ImageView) view.findViewById(R.id.image));


        view.findViewById(R.id.text_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(uri.getPath());
                mPresentor.submitPicture(field_title.getText().toString().trim(), file, new AddPictureListener() {
                    @Override
                    public void onPictureTitleEmpty() {
                        Toast.makeText(HomeActivity.this.getApplicationContext(), "Title cannot be empty!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void closeDialog() {
                        dialog.dismiss();

                    }
                });
            }
        });
*/

    }


    public void showFreshFeedFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        FreshFeedFragment freshFeedFragment = new FreshFeedFragment();
        freshFeedFragment.setArguments(bundle);
        showFragment(freshFeedFragment, true);
    }


    public void showVisitorProfile(String photographer_penname) {
        AppUtils.showVisitorProfile(this, photographer_penname);
    }

    @Override
    protected void onDestroy() {
        mPresentor.detachView();
        super.onDestroy();
    }


    @Override
    public void hideProgressBar() {
        MyProgressDialog.dismissProgressDialog();
    }

    @Override
    public void showProgressBar() {
        MyProgressDialog.showProgressDialog(this, "Uploading! Please wait.");
    }

    @Override
    public void onPictureUploadSuccess() {
        AppUtils.sendRefreshBroadcast(this, Constants.REFRESH_HOME_FEEDS);
    }

    public void updateNotificationIcon(boolean unread_notifications) {
        Log.d(TAG, "updateNotificationIcon: ");
        button_notification.setVisibility(View.VISIBLE);
        if (unread_notifications)
            button_notification.setImageResource(R.drawable.ic_bell_active);
        else button_notification.setImageResource(R.drawable.ic_bell_normal);

    }

    public void showSearchActivity(String searchTag) {
        AppUtils.showSearchActivity(this, searchTag);
    }

    public void onPageSelected(int position) {
        appBar.setExpanded(true);

        switch (position) {

            case 1:

                tab_home.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                tab_explore.setColorFilter(getResources().getColor(R.color.colorPrimary));
                tab_profile.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                fab.hide();
                break;
            case 0:
                tab_home.setColorFilter(getResources().getColor(R.color.colorPrimary));
                tab_explore.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                tab_profile.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                fab.show();
                break;
            case 2:
                tab_home.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                tab_explore.setColorFilter(getResources().getColor(R.color.tab_icon_grey));
                tab_profile.setColorFilter(getResources().getColor(R.color.colorPrimary));
                fab.hide();

                break;
            default:
                break;
        }
    }

    public void showHomeFeedFragment(int i) {
        showFreshFeedFragment(0);
        onPageSelected(1);
    }


    interface AddPictureListener {
        void onPictureTitleEmpty();

        void closeDialog();
    }
}
