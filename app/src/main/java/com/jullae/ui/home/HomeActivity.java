package com.jullae.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jullae.NotificationActivity;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.SearchPeopleMainModel;
import com.jullae.ui.adapters.SearchPersonAdapter;
import com.jullae.ui.base.BaseActivity;
import com.jullae.ui.fragments.HomeFragment;
import com.jullae.ui.fragments.ProfileFragment;
import com.jullae.ui.home.homeFeed.freshfeed.FreshFeedFragment;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.KeyboardUtils;
import com.jullae.utils.dialog.MyProgressDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;
import java.util.Random;


/**
 * Created by Rahul Abrol on 12/13/17.
 * <p>
 * Class @{@link HomeActivity} used to hold the
 * feed items, add, edit stories,images,remove etc.
 */
public class HomeActivity extends BaseActivity implements HomeActivityView {
    private static final int CAMERA_REQUEST = 24;
    private static final int REQ_CODE_ADD_PICTURE = 45;
    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private Button addButton;
    private ImageView tab_home, tab_explore, tab_profile;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private ProfileFragment.ImagePickListener imagePickListener;
    private ImagePicker imagePicker;
    private Uri imageToUploadUri;
    private HomeActivityPresentor mPresentor;
    private ImageView button_notification;
    private View button_search;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_feed);
        mPresentor = new HomeActivityPresentor();
        mPresentor.attachView(this);


        showFragment(new HomeFragment(), false);
        if (getIntent().getBooleanExtra("showprofile", false)) {
            showHomeFragment(2);
        }
        //client

        //Initialize the button and bottomNavigation for listener.
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        setUpSearchButton();
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

        button_notification = findViewById(R.id.image_notification);
        button_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_notification.setImageResource(R.drawable.ic_bell_normal);
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                //showFragment(new NotificationFragment(), true);
            }
        });
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
                cleanUpSearchContainer();
            }
        });


    }

    private void cleanUpSearchContainer() {
        findViewById(R.id.search_container).setVisibility(View.GONE);
        autoCompleteTextView.setText("");
    }

    private void showAddImageOption() {
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


 /*   private void showImageChooseDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_choose_image, null);

        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


        view.findViewById(R.id.text_open_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkCameraPermission();
                } else {
                    startImageCapture();
                }

            }
        });
        view.findViewById(R.id.text_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkstoragepermissionforimage();
                } else {
                    chooseImage();

                }

            }
        });


    }*/

    private void checkCameraPermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted())
                            startImageCapture();
                        else {
                            Log.d(TAG, "onPermissionDenied: ");
                            Toast.makeText(HomeActivity.this, "Please allow the permission to proceed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void startImageCapture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Random r = new Random();
        int i1 = r.nextInt(1000) + 100;
        File fs = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Farm2Fork");
        if (!fs.exists())
            fs.mkdirs();
        File f = new File(fs, +i1 + ".jpg");
        imageToUploadUri = FileProvider.getUriForFile(HomeActivity.this, HomeActivity.this.getApplicationContext().getPackageName() + ".provider", f);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri);
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void checkstoragepermissionforimage() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        chooseImage();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(HomeActivity.this, "Please allow the permission to proceed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();
                    }

                }).check();

    }

    public void chooseImage() {
        imagePicker = new ImagePicker(this);
        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
                                               @Override
                                               public void onImagesChosen(List<ChosenImage> images) {
                                                   if (images != null) {
                                                       if (images.size() > 0) {
                                                           onImageChosenByUser(images.get(0).getQueryUri());

                                                       } else Log.d(TAG, "onImagesChosen: empty");
                                                   } else
                                                       Log.e(TAG, "onError: onimagechoosen: ");

                                               }

                                               @Override
                                               public void onError(String message) {
                                                   // Do error handling
                                                   Log.e(TAG, "onError: " + message);
                                               }
                                           }

        );

        imagePicker.shouldGenerateMetadata(true);
        imagePicker.shouldGenerateThumbnails(false);
        imagePicker.pickImage();
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

    @Override
    public void onBackPressed() {

        if (findViewById(R.id.search_container).getVisibility() == View.VISIBLE) {
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
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                if (imagePickListener != null)
                    imagePickListener.onImagePickSucccess(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                if (imagePickListener != null)

                    imagePickListener.onImagePickFail();

                Log.d(TAG, "onActivityResult: " + error);

            }
        }

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
        //  imageChooser.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            onImageChosenByUser(String.valueOf(imageToUploadUri));
        } else {
            Log.d(TAG, "onActivityResult: Image cant be captured");
            imageToUploadUri = null;
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == Picker.PICK_IMAGE_DEVICE) {
                if (imagePicker == null) {
                    imagePicker = new ImagePicker(this);
                    imagePicker.setImagePickerCallback(new ImagePickerCallback() {
                                                           @Override
                                                           public void onImagesChosen(List<ChosenImage> images) {
                                                               if (images != null)
                                                                   if (images.size() > 0) {

                                                                       onImageChosenByUser(images.get(0).getQueryUri());

                                                                   } else Log.d(TAG, "onImagesChosen: empty");
                                                               else
                                                                   Log.e(TAG, "onError: onimagechoosen: ");

                                                           }

                                                           @Override
                                                           public void onError(String message) {
                                                               // Do error handling
                                                               Log.e(TAG, "onError: " + message);
                                                           }
                                                       }

                    );
                }
                imagePicker.submit(data);
            }
        }

        if (requestCode == AppUtils.REQUEST_CODE_WRTIE_STORY && resultCode == Activity.RESULT_OK) {

            AppUtils.sendRefreshBroadcast(HomeActivity.this, Constants.REFRESH_HOME_FEEDS);
        }
        if (requestCode == AppUtils.REQUEST_CODE_SEARCH_TAG && resultCode == Activity.RESULT_OK) {
            AppUtils.showSearchActivity(HomeActivity.this, data.getStringExtra("searchtag"));
        }
        if (requestCode == AppUtils.REQUEST_CODE_WRITESTORY_FROM_PICTURE_TAB && resultCode == Activity.RESULT_OK) {
            AppUtils.sendRefreshBroadcast(HomeActivity.this, Constants.REFRESH_PICTURES_TAB);
        }
    }


    private void onImageChosenByUser(String uri) {
        Log.d(TAG, "onImageChosenByUser: " + uri);
        // showAddPictureDialog(uri);

    }


    private void showAddPictureDialog(final Uri uri) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
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

        Glide.with(HomeActivity.this).load(uri).into((ImageView) view.findViewById(R.id.image));


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


    }


    public void showFreshFeedFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        FreshFeedFragment freshFeedFragment = new FreshFeedFragment();
        freshFeedFragment.setArguments(bundle);
        showFragment(freshFeedFragment, true);
    }


    public void showCropImage(ProfileFragment.ImagePickListener imagePickListener) {
        this.imagePickListener = imagePickListener;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setFixAspectRatio(true)

                .setRequestedSize(500, 500)
                .setMinCropResultSize(200, 200)
                .start(this);
    }

    public void removeListener() {
        imagePickListener = null;
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
        AppUtils.sendRefreshBroadcast(this, Constants.REFRESH_PICTURES_TAB);


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


    interface AddPictureListener {
        void onPictureTitleEmpty();

        void closeDialog();
    }
}
