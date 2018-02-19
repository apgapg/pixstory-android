package com.jullae.utils.imagepicker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;

import com.jullae.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Developer: Saurabh Verma
 * Dated: 19-02-2017.
 */
public final class ImageCompressor {
    private static final float MAX_WIDTH = 800.0f;
    private static final float MAX_HEIGHT = 800.0f;
    private static final int QUALITY = 90;
    //10 hour
    private static final long FILE_DELETE_HOUR = 10 * 60 * 60 * 1000;
    private static final int MIN_QUALITY = 60;
    private static final int MAX_QUALITY = 100;
    //16 mb in bytes
    private static final int TEMP_STORAGE = 16 * 1024;
    private static final int ROTATE_90 = 90;
    private static final int ROTATE_180 = 180;
    private static final int ROTATE_270 = 270;

    private static final String TAG = ImageCompressor.class.getName();

    private final Context context;
    private final String destinationDirectoryPath;
    //max width and height values of the compressed image is taken as 800x1000
    private final ArrayList<File> files = new ArrayList<>();
    private OnCompressionDone onCompassionDone;
    private float maxWidth = MAX_WIDTH;
    private float maxHeight = MAX_HEIGHT;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private int quality = QUALITY;

    /**
     * @param context context of application from where it's called
     */
    private ImageCompressor(final Context context) {
        this.context = context.getApplicationContext();
        destinationDirectoryPath = context.getCacheDir().getPath() + File.pathSeparator + CompressionFileUtil.FILES_PATH;
    }

    /**
     * @param file array of files to compress
     */
    private void compressToFileWithCallback(final ArrayList<File> file) {
        if (onCompassionDone != null) {
            if (file.size() == 0) {
                onCompassionDone.onError("No files added");
            } else {
                new ImageCompressionAsyncTask().execute(file.toArray(new File[file.size()]));
            }
        }
    }

    /**
     * Deletes all files in directory ImageCompressor which have not been used since 24 hours to clear the cache
     */
    private void deletePreviousFiles() {
        int filesDeleted = 0;
        File f = new File(destinationDirectoryPath);
        File[] file = f.listFiles();
        if (file != null) {
            for (File aFile : file) {
                //if it has been 10 hours since the file was last accessed, delete the file
                if (System.currentTimeMillis() - aFile.lastModified() > FILE_DELETE_HOUR) {
                    if (aFile.delete()) {
                        filesDeleted++;
                    }
                }
            }
        }
        Log.i(getClass().getSimpleName(), "Total files deleted " + filesDeleted);
    }

    /**
     * Method to check read & write permission for target SDK <=23
     *
     * @return true if permission granted else return false
     */
    private boolean hasReadWritePermissions() {
        return ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * The interface On compression done.
     */
    public interface OnCompressionDone {
        /**
         * On done.
         *
         * @param compressesFiles the compresses files
         */
        void onDone(ArrayList<File> compressesFiles);

        /**
         * On error.
         *
         * @param s the s
         */
        void onError(String s);
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        private final ImageCompressor compressor;

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         */
        public Builder(final Context context) {
            compressor = new ImageCompressor(context);
        }

        /**
         * Sets max width.
         *
         * @param maxWidth the max width
         * @return the max width
         */
        public Builder setMaxWidth(final float maxWidth) {
            compressor.maxWidth = maxWidth;
            return this;
        }

        /**
         * Sets max height.
         *
         * @param maxHeight the max height
         * @return the max height
         */
        public Builder setMaxHeight(final float maxHeight) {
            compressor.maxHeight = maxHeight;
            return this;
        }

        /**
         * Sets compress format.
         *
         * @param compressFormat the compress format
         * @return the compress format
         */
        public Builder setCompressFormat(final Bitmap.CompressFormat compressFormat) {
            compressor.compressFormat = compressFormat;
            return this;
        }

        /**
         * Sets file.
         *
         * @param file the file
         * @return the file
         */
        public Builder setFile(final File file) {
            compressor.files.clear();
            if (file != null) {
                compressor.files.add(file);
            }
            return this;
        }

        /**
         * Sets files.
         *
         * @param files the files
         * @return the files
         */
        public Builder setFiles(final ArrayList<File> files) {
            compressor.files.clear();
            if (files != null) {
                compressor.files.addAll(files);
            }
            return this;
        }

        /**
         * Sets quality.
         *
         * @param quality the quality
         * @return the quality
         */
//quality should range between 60 and 100
        public Builder setQuality(@IntRange(from = MIN_QUALITY, to = MAX_QUALITY) final int quality) {
            if (quality > MIN_QUALITY && quality <= MAX_QUALITY) {
                compressor.quality = quality;
            }
            return this;
        }

        /**
         * Sets callback.
         *
         * @param onCompassionDone the on compassion done
         * @return the callback
         */
        public Builder setCallback(final OnCompressionDone onCompassionDone) {
            compressor.onCompassionDone = onCompassionDone;
            return this;
        }

        /**
         * Build.
         */
        public void build() {
            if (compressor.files.size() > 0) {
                compressor.compressToFileWithCallback(compressor.files);
            }
        }
    }

    /**
     * Util class for compression of image
     */
    private static final class CompressionImageUtil {

        /**
         * Empty Constructor
         * not called
         */
        private CompressionImageUtil() {
        }

        /**
         * Gets scaled aswaq_header.
         *
         * @param context   the context
         * @param imageUri  the image uri
         * @param maxWidth  the max width
         * @param maxHeight the max height
         * @return the scaled aswaq_header
         */
        static Bitmap getScaledBitmap(final Context context, final Uri imageUri, final float maxWidth, final float maxHeight) {
            String filePath = CompressionFileUtil.getRealPathFromURI(context, imageUri);
            Bitmap scaledBitmap;
            BitmapFactory.Options options = new BitmapFactory.Options();
            //by setting this field as true, the actual aswaq_header pixels are not loaded in the memory. Just the bounds are loaded. If
            //you try the use the aswaq_header here, you will get null.
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
            if (bmp == null) {
                InputStream inputStream;
                try {
                    inputStream = new FileInputStream(filePath);
                    BitmapFactory.decodeStream(inputStream, null, options);
                    inputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            float imgRatio = (float) actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;
            //width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }
            //setting inSampleSize value allows to load a scaled down version of the original image
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            //inJustDecodeBounds set to false to load the actual aswaq_header
            options.inJustDecodeBounds = false;
            //this options allow android to claim the aswaq_header memory if it runs low on memory
            //options.inPurgeable = true;
            // options.inInputShareable = true;
            options.inTempStorage = new byte[TEMP_STORAGE];
            try {
                //load the aswaq_header from its path
                bmp = BitmapFactory.decodeFile(filePath, options);
                if (bmp == null) {
                    InputStream inputStream;
                    try {
                        inputStream = new FileInputStream(filePath);
                        BitmapFactory.decodeStream(inputStream, null, options);
                        inputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        return null;
                    }
                }
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
                return null;
            }
            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, 0, 0);
            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            if (bmp == null) {
                return null;
            }
            canvas.drawBitmap(bmp, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
            //check the rotation of the image and display it properly
            ExifInterface exif;
            try {
                exif = new ExifInterface(filePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                Matrix matrix = new Matrix();
                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    matrix.postRotate(ROTATE_90);
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                    matrix.postRotate(ROTATE_180);
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    matrix.postRotate(ROTATE_270);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                        scaledBitmap.getWidth(), scaledBitmap.getHeight(),
                        matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return scaledBitmap;
        }

        /**
         * Compress image file.
         *
         * @param context        the context
         * @param imageUri       the image uri
         * @param maxWidth       the max width
         * @param maxHeight      the max height
         * @param compressFormat the compress format
         * @param quality        the quality
         * @param parentPath     the parent path
         * @return the file
         */
        static File compressImage(final Context context, final Uri imageUri, final float maxWidth, final float maxHeight,
                                  final Bitmap.CompressFormat compressFormat, final int quality, final String parentPath) {
            FileOutputStream out = null;
            String filename = generateFilePath(context, parentPath, imageUri, compressFormat.name().toLowerCase());
            try {
                out = new FileOutputStream(filename);
                //write the compressed aswaq_header at the destination specified by filename.
                Bitmap bitmap = CompressionImageUtil.getScaledBitmap(context, imageUri, maxWidth, maxHeight);
                if (bitmap == null) {
                    return null;
                }
                bitmap.compress(compressFormat, quality, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException ignored) {
                    Log.e(TAG, ignored.getMessage());
                }
            }
            return new File(filename);
        }

        /**
         * @param context    content from where its called
         * @param parentPath parent path of file
         * @param uri        uri of file
         * @param extension  extension of file
         * @return filePath
         */
        private static String generateFilePath(final Context context, final String parentPath, final Uri uri, final String extension) {
            File file = new File(parentPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath() + File.separator
                    + CompressionFileUtil.splitFileName(CompressionFileUtil.getFileName(context, uri))[0] + "." + extension;
        }

        /**
         * @param options   aswaq_header options
         * @param reqWidth  width of required image
         * @param reqHeight height of required image
         * @return image size
         */
        private static int calculateInSampleSize(final BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                if (heightRatio < widthRatio) {
                    inSampleSize = heightRatio;
                } else {
                    inSampleSize = widthRatio;
                }
            }
            final float totalPixels = width * height;
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
            return inSampleSize;
        }
    }

    /**
     *
     */
    private static class CompressionFileUtil {
        /**
         * The Files path.
         */
        static final String FILES_PATH = TAG;
        private static final int EOF = -1;
        private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

        /**
         * From file.
         *
         * @param context the context
         * @param uri     the uri
         * @return the file
         * @throws IOException the io exception
         */
        public static File from(final Context context, final Uri uri) throws IOException {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            String fileName = getFileName(context, uri);
            String[] splitName = splitFileName(fileName);
            File tempFile = File.createTempFile(splitName[0], splitName[1]);
            tempFile = rename(tempFile, fileName);
            tempFile.deleteOnExit();
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(tempFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (inputStream != null) {
                copy(inputStream, out);
                inputStream.close();
            }
            if (out != null) {
                out.close();
            }
            return tempFile;
        }

        /**
         * Split file name string [ ].
         *
         * @param fileName the file name
         * @return the string [ ]
         */
        static String[] splitFileName(final String fileName) {
            String name = fileName;
            String extension = "";
            int i = fileName.lastIndexOf(".");
            if (i != -1) {
                name = fileName.substring(0, i);
                extension = fileName.substring(i);
            }
            return new String[]{name, extension};
        }

        /**
         * Gets file name.
         *
         * @param context the context
         * @param uri     the uri
         * @return the file name
         */
        static String getFileName(final Context context, final Uri uri) {
            String result = null;
            if ("content".equals(uri.getScheme())) {
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf(File.separator);
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
            return result;
        }

        /**
         * Gets real path from uri.
         *
         * @param context    the context
         * @param contentUri the content uri
         * @return the real path from uri
         */
        static String getRealPathFromURI(final Context context, final Uri contentUri) {
            Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
            if (cursor == null) {
                return contentUri.getPath();
            } else {
                cursor.moveToFirst();
                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                String realPath = cursor.getString(index);
                cursor.close();
                return realPath;
            }
        }

        /**
         * Rename file.
         *
         * @param file    the file
         * @param newName the new name
         * @return the file
         */
        static File rename(final File file, final String newName) {
            File newFile = new File(file.getParent(), newName);
            if (!newFile.equals(file)) {
                if (newFile.exists()) {
                    if (newFile.delete()) {
                        Log.d(TAG, "Delete old " + newName + " file");
                    }
                }
                if (file.renameTo(newFile)) {
                    Log.d(TAG, "Rename file to " + newName);
                }
            }
            return newFile;
        }

        /**
         * Copy int.
         *
         * @param input  the input
         * @param output the output
         * @return the int
         * @throws IOException the io exception
         */
        static int copy(final InputStream input, final OutputStream output) throws IOException {
            long count = copyLarge(input, output);
            if (count > Integer.MAX_VALUE) {
                return -1;
            }
            return (int) count;
        }

        /**
         * Copy large long.
         *
         * @param input  the input
         * @param output the output
         * @return the long
         * @throws IOException the io exception
         */
        static long copyLarge(final InputStream input, final OutputStream output)
                throws IOException {
            return copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
        }

        /**
         * Copy large long.
         *
         * @param input  the input
         * @param output the output
         * @param buffer the buffer
         * @return the long
         * @throws IOException the io exception
         */
        static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
                throws IOException {
            long count = 0;
            int n;
            while (EOF != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                count += n;
            }
            return count;
        }
    }

    /**
     * AsyncTask class for compression of files
     */
    private class ImageCompressionAsyncTask extends AsyncTask<File, Void, ArrayList<File>> {

        @Override
        protected ArrayList<File> doInBackground(final File... filesList) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasReadWritePermissions()) {
                onError("System does not have read and write permissions");
                return null;
            }
            deletePreviousFiles();
            ArrayList<File> compressesFiles = new ArrayList<>();
            for (File file : filesList) {
                File compressedFile = CompressionImageUtil.compressImage(context, Uri.fromFile(file),
                        maxWidth, maxHeight, compressFormat, quality, destinationDirectoryPath);
                if (compressedFile != null) {
                    compressesFiles.add(compressedFile);
                } else {
                    onError("Could not compress the file " + file.getName());
                    return null;
                }
            }
            return compressesFiles;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final ArrayList<File> compressesFiles) {
            if (compressesFiles != null && !isCancelled()) {
                onCompassionDone.onDone(compressesFiles);
            }
        }

        /**
         * @param s error message
         */
        private void onError(final String s) {
            this.cancel(true);
            onCompassionDone.onError(s);
        }
    }
}
