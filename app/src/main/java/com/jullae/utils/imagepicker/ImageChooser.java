package com.jullae.utils.imagepicker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.jullae.R;
import com.kbeanie.multipicker.api.CacheLocation;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * Developer: Saurabh Verma
 * Dated: 03-03-2017.
 */

public class ImageChooser implements ImagePickerCallback {
    private static final String[] PERMISSION_CAMERA_READ_WRITE
            = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /*CacheLocation.EXTERNAL_STORAGE_APP_DIR
    CacheLocation.EXTERNAL_STORAGE_PUBLIC_DIR;
    CacheLocation.EXTERNAL_CACHE_DIR
    CacheLocation.INTERNAL_APP_DIR*/
    private static final int CACHE_LOCATION = CacheLocation.EXTERNAL_STORAGE_APP_DIR;
    private static final int PERMISSION_REQUEST_CODE_CAMERA_READ_WRITE = 0x11;
    private static final int MAX_SIZE = 1024;
    private static final int REQUEST_ID = 1234;

    private String filePath;

    private ImagePicker mImagePicker;
    private CameraImagePicker mCameraImagePicker;
    private Activity mActivity;
    private Fragment mFragment;
    private boolean cropEnabled = false;
    private OnImageSelectListener onImageSelectListener;
    private AlertDialog mAlertDialog;

    /**
     * the X value of the aspect ratio.
     */
    private int aspectRatioX = 1;
    /**
     * the Y value of the aspect ratio.
     */
    private int aspectRatioY = 1;


    /**
     * Instantiates a new Image chooser.
     *
     * @param builder the builder
     */
    public ImageChooser(final Builder builder) {
        this.mActivity = builder.mActivity;
        this.mFragment = builder.mFragment;
        this.cropEnabled = builder.cropEnabled;
        this.aspectRatioX = builder.aspectRatioX;
        this.aspectRatioY = builder.aspectRatioY;
    }


    /**
     * Select image.
     *
     * @param imageSelectListener the on image select listener
     */
    public void selectImage(@NonNull final OnImageSelectListener imageSelectListener) {
        this.onImageSelectListener = imageSelectListener;
        if (hasPermissions(getContext(), PERMISSION_CAMERA_READ_WRITE)) {
            dismissDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getContext().getString(R.string.text_choose_image))
                    .setPositiveButton(getContext().getString(R.string.text_camera), new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            mAlertDialog.dismiss();
                            takePicture();
                        }
                    })
//                    .setNeutralButton(getContext().getString(R.string.text_multiple), new DialogInterface.OnClickListener() {
//                        public void onClick(final DialogInterface dialog, final int id) {
//                            mAlertDialog.dismiss();
//                            pickImageMultiple();
//                        }
//                    })
                    .setNegativeButton(getContext().getString(R.string.text_gallery), new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            mAlertDialog.dismiss();
                            pickImageSingle();
                        }
                    });
            mAlertDialog = builder.create();
            mAlertDialog.show();
        } else {
            if (shouldShowRationale(PERMISSION_CAMERA_READ_WRITE)) {
                dismissDialog();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(getContext().getString(R.string.permission_camera_storage_msg))
                        .setPositiveButton(getContext().getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                requestPermissions(PERMISSION_CAMERA_READ_WRITE);
                            }
                        });
                mAlertDialog = builder.create();
                mAlertDialog.show();
            } else {
                requestPermissions(PERMISSION_CAMERA_READ_WRITE);
            }
        }
    }

    /**
     * Method to dismiss dialog
     */
    private void dismissDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    /**
     * @return Context of activity or fragment.
     */
    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        } else {
            return mFragment.getContext();
        }
    }

    /**
     * Check if the calling context has a set of permissions.
     *
     * @param context the calling context.
     * @param perms   one ore more permissions, such as {@link Manifest.permission#CAMERA}.
     * @return true if all permissions are already granted, false if at least one permission is not
     * yet granted.
     * @see Manifest.permission
     */
    private boolean hasPermissions(@NonNull final Context context, @NonNull final String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //Log.w(TAG, "hasPermissions: API version < M, returning true by progressBar");
            // DANGER ZONE!!! Changing this will break the library.
            return true;
        }

        for (String perm : perms) {
            boolean hasPerm = ContextCompat.checkSelfPermission(context, perm)
                    == PackageManager.PERMISSION_GRANTED;
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }
    //============================================= M permissions ==================================

    /**
     * @param perms array of requested permission
     * @return true if the user has previously denied any of the {@code perms} and we should show a
     * rationale, false otherwise.
     */
    private boolean shouldShowRationale(@NonNull final String[] perms) {
        boolean shouldShowRationale = false;
        for (String perm : perms) {
            shouldShowRationale =
                    shouldShowRationale || shouldShowRequestPermissionRationale(perm);
        }
        return shouldShowRationale;
    }

    /**
     * @param perm permission requested
     * @return true if request permission is rational else return false
     */
    private boolean shouldShowRequestPermissionRationale(@NonNull final String perm) {
        if (mActivity != null) {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, perm);
        } else if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(perm);
        } else {
            throw new IllegalArgumentException("Object was neither an Activity nor a Fragment.");
        }
    }

    /**
     * @param perms array of requested permission
     */
    private void requestPermissions(@NonNull final String[] perms) {
        if (mActivity != null) {
            ActivityCompat.requestPermissions(mActivity, perms, PERMISSION_REQUEST_CODE_CAMERA_READ_WRITE);
        } else if (mFragment != null) {
            mFragment.requestPermissions(perms, PERMISSION_REQUEST_CODE_CAMERA_READ_WRITE);
        } else {
            throw new IllegalArgumentException("Object was neither an Activity nor a Fragment.");
        }
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @param grantResults list of permission grant result
     * @return true if all permission granted else return false
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    private boolean verifyPermissions(final int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }
        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * On request permissions result.
     *
     * @param requestCode  the request code
     * @param permissions  the permissions
     * @param grantResults the grant results
     */
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE_CAMERA_READ_WRITE) {
            if (verifyPermissions(grantResults)) {
                selectImage(onImageSelectListener);
            } else {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(getContext().getString(R.string.permission_camera_storage_denied_msg))
                        .setPositiveButton(getContext().getString(R.string.text_setting), new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                mAlertDialog.dismiss();
                                startInstalledAppDetailsActivity(getContext());
                            }
                        })
                        .setNegativeButton(getContext().getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                mAlertDialog.dismiss();
                            }
                        });
                mAlertDialog = builder.create();
                mAlertDialog.show();
            }
        }
    }

    //============================================= ImagePickerCallback ============================
    @Override
    public void onImagesChosen(final List<ChosenImage> list) {
        if (list != null && list.size() > 0) {
            if (list.size() == 1 && cropEnabled) {
                File mFile = new File(list.get(0).getOriginalPath());
                if (isImage(mFile)) {
                    if (mActivity != null) {
                        CropImage.activity(Uri.fromFile(mFile))
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(aspectRatioX, aspectRatioY)
                                .start(mActivity);
                    } else {
                        CropImage.activity(Uri.fromFile(mFile))
                                .setAspectRatio(aspectRatioX, aspectRatioY)
                                .start(mFragment.getContext(), mFragment);
                    }
                } else {
                    onError(getContext().getString(R.string.error_not_a_valid_image_file));
                }
            } else {
                File mFile = new File(list.get(0).getOriginalPath());
                if (list.size() == 1) {
                    if (isImage(mFile)) {
                        onImageSelectListener.loadImage(list);
                    } else {
                        onError(getContext().getString(R.string.error_not_a_valid_image_file));
                    }
                } else {
                    onImageSelectListener.loadImage(list);
                }
            }
        }
    }

    @Override
    public void onError(final String s) {
        dismissDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(s)
                .setPositiveButton(getContext().getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        mAlertDialog.dismiss();
                    }
                });
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    /**
     * Method to click image from camera.
     */
    private void takePicture() {
        mCameraImagePicker = getCameraImagePicker();
        mCameraImagePicker.setDebugglable(true);
        mCameraImagePicker.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR);
        mCameraImagePicker.setImagePickerCallback(this);
        mCameraImagePicker.shouldGenerateMetadata(true);
        mCameraImagePicker.shouldGenerateThumbnails(true);
        filePath = mCameraImagePicker.pickImage();
    }

    /**
     * Method to pick single image from gallery.
     */
    private void pickImageSingle() {
        mImagePicker = getImagePicker();
        mImagePicker.setFolderName("Random");
        mImagePicker.setRequestId(REQUEST_ID);
        //mImagePicker.ensureMaxSize(MAX_SIZE, MAX_SIZE);
        mImagePicker.shouldGenerateMetadata(true);
        mImagePicker.shouldGenerateThumbnails(true);
        mImagePicker.setImagePickerCallback(this);
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extras.CAMERA_FACING", 1);
        mImagePicker.setCacheLocation(CACHE_LOCATION);
        mImagePicker.pickImage();
    }


    //==============================================================================================

    /**
     * Method to pick multiple image from gallery.
     */
    private void pickImageMultiple() {
        mImagePicker = getImagePicker();
        mImagePicker.setImagePickerCallback(this);
        mImagePicker.allowMultiple();
        mImagePicker.pickImage();
    }

    /**
     * @return Object of ImagePicker
     */
    private ImagePicker getImagePicker() {
        ImagePicker mImgPicker;
        if (mActivity != null) {
            mImgPicker = new ImagePicker(mActivity);
        } else {
            mImgPicker = new ImagePicker(mFragment);
        }

        return mImgPicker;
    }

    /**
     * @return Object of CameraImagePicker
     */
    private CameraImagePicker getCameraImagePicker() {
        CameraImagePicker mCameraImgPicker;
        if (mActivity != null) {
            mCameraImgPicker = new CameraImagePicker(mActivity);
        } else {
            mCameraImgPicker = new CameraImagePicker(mFragment);
        }
        return mCameraImgPicker;
    }

    /**
     * Open app setting of installed application.
     *
     * @param mContext context of calling class
     */
    private void startInstalledAppDetailsActivity(final Context mContext) {
        if (mContext == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + mContext.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        mContext.startActivity(i);
    }

    /**
     * To detect whether file is image or not if user is uploading text file with any image extension
     *
     * @param file file selected
     * @return true if valid image file else false
     */
    private boolean isImage(final File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);
        return options.outWidth != -1 && options.outHeight != -1;
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Picker.PICK_IMAGE_DEVICE) {
                if (mImagePicker == null) {
                    mImagePicker = getImagePicker();
                    mImagePicker.setImagePickerCallback(this);
                }
                mImagePicker.submit(data);
            } else if (requestCode == Picker.PICK_IMAGE_CAMERA) {
                if (mCameraImagePicker == null) {
                    mCameraImagePicker = getCameraImagePicker();
                    mCameraImagePicker.setImagePickerCallback(this);
                    mCameraImagePicker.reinitialize(filePath);
                }
                mCameraImagePicker.submit(data);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                this.onImageSelectListener.croppedImage(new File(result.getUri().getPath()));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                dismissDialog();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(result.getError().getMessage())
                        .setPositiveButton(getContext().getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                mAlertDialog.dismiss();
                            }
                        });
                mAlertDialog = builder.create();
                mAlertDialog.show();
            }
        }
    }

    /**
     * The interface On image select listener.
     */
    public interface OnImageSelectListener {
        /**
         * Load image.
         *
         * @param list the list
         */
        void loadImage(List<ChosenImage> list);

        /**
         * Cropped image.
         *
         * @param mCroppedImage the m cropped image
         */
        void croppedImage(File mCroppedImage);
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        /**
         * the X value of the aspect ratio.
         */
        private int aspectRatioX = 1;
        /**
         * the Y value of the aspect ratio.
         */
        private int aspectRatioY = 1;
        private Activity mActivity;
        private Fragment mFragment;
        private boolean cropEnabled = false;


        /**
         * Instantiates a new Builder.
         *
         * @param mActivity the m activity
         */
        public Builder(@NonNull final Activity mActivity) {
            this.mActivity = mActivity;
        }

        /**
         * Instantiates a new Builder.
         *
         * @param mFragment the m fragment
         */
        public Builder(@NonNull final Fragment mFragment) {
            this.mFragment = mFragment;
        }

        /**
         * Sets crop enabled.
         *
         * @param isEnabled the crop enabled
         * @return the crop enabled
         */
        public Builder setCropEnabled(final boolean isEnabled) {
            this.cropEnabled = isEnabled;
            return this;
        }

        /**
         * Sets aspect ratio.
         *
         * @param mAspectRatioX the aspect ratio x
         * @param mAspectRatioY the aspect ratio y
         * @return the aspect ratio
         */
        public Builder setAspectRatio(final int mAspectRatioX, final int mAspectRatioY) {
            this.aspectRatioX = mAspectRatioX;
            this.aspectRatioY = mAspectRatioY;
            return this;
        }

        /**
         * Build image chooser.
         *
         * @return the image chooser
         */
        public ImageChooser build() {
            return new ImageChooser(this);
        }
    }
}
