//package com.jullae.utils.dialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.aswaq.R;
//import com.jullae.R;
//
///**
// * Created by soc-mac-6 on 8/30/17.
// * DIalog to handle single button view.
// */
//public final class SingleBtnDialog {
//    private Dialog dialog;
//
//    /**
//     * Constructor.
//     *
//     * @param context calling class instance.
//     */
//    private SingleBtnDialog(@NonNull final Context context) {
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//        // custom dialog
//        dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.dialog_single_btn);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                dialog.dismiss();
//            }
//        });
//    }
//
//    /**
//     * get the instance of calling class.
//     *
//     * @param context context
//     * @return istance
//     */
//    @NonNull
//    public static SingleBtnDialog with(@NonNull final Context context) {
//        return new SingleBtnDialog(context);
//    }
//
//    /**
//     * Set mesage.
//     *
//     * @param msg msg
//     * @return instance
//     */
//    @NonNull
//    public SingleBtnDialog setMessage(final String msg) {
//        if (dialog != null) {
//            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
//            tvMessage.setText(msg);
//        }
//        return this;
//    }
//
//    /**
//     * attach context.
//     *
//     * @param imageDrawable image to show
//     * @return instance
//     */
//    @NonNull
//    public SingleBtnDialog setImage(final Drawable imageDrawable) {
//        if (dialog != null) {
//            ImageView ivDialogImage = (ImageView) dialog.findViewById(R.id.ivDialogImage);
//            ivDialogImage.setBackground(imageDrawable);
//        }
//        return this;
//    }
//
//
//    /**
//     * Is dialoge cancellable.
//     *
//     * @param bool val
//     * @return instance.
//     */
//    @NonNull
//    public SingleBtnDialog setCancelable(final boolean bool) {
//        if (dialog != null) {
//            dialog.setCancelable(false);
//        }
//        return this;
//    }
//
//    /**
//     * set cnacellable.
//     *
//     * @param bool value
//     * @return instance
//     */
//    @NonNull
//    public SingleBtnDialog setCancelableOnTouchOutside(final boolean bool) {
//        if (dialog != null) {
//            dialog.setCanceledOnTouchOutside(false);
//        }
//        return this;
//    }
//
//    /**
//     * Set title of dialog.
//     *
//     * @param heading title
//     * @return instance
//     */
//    @NonNull
//    public SingleBtnDialog setHeading(final String heading) {
//        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvTitle);
//        if (dialog != null) {
//            tvMessage.setText(heading);
//            tvMessage.setVisibility(View.VISIBLE);
//        } else {
//            tvMessage.setVisibility(View.GONE);
//        }
//        return this;
//    }
//
//    /**
//     * Set option postive.
//     *
//     * @param optionPositive string
//     * @return instance.
//     */
//    @NonNull
//    public SingleBtnDialog setOptionPositive(final String optionPositive) {
//        if (dialog != null) {
//            Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
//            btnOk.setText(optionPositive);
//            btnOk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    dialog.dismiss();
//                }
//            });
//        }
//        return this;
//    }
//
//    /**
//     * Set callback here.
//     *
//     * @param onActionPerformed click listner.
//     * @return instance
//     */
//    @NonNull
//    public SingleBtnDialog setCallback(@Nullable final OnActionPerformed onActionPerformed) {
//        if (dialog != null && onActionPerformed != null) {
//            Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
//            btnOk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    dialog.dismiss();
//                    onActionPerformed.positive();
//                }
//            });
//        }
//        return this;
//    }
//
//    /**
//     * Shoe dialog.
//     */
//    public void show() {
//        if (dialog != null) {
//            dialog.show();
//        }
//    }
//
//    /**
//     * Interface for click handle.
//     */
//    public interface OnActionPerformed {
//        /**
//         * Mthod for click handle.
//         */
//        void positive();
//    }
//
//}
