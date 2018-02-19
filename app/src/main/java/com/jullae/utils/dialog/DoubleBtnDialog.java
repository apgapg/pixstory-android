//package com.jullae.utils.dialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.jullae.R;
//
//
///**
// * Created by cl-mac-mini-3 on 1/4/17.
// * Double button Dialog;
// */
//
//public final class DoubleBtnDialog {
//    private Dialog dialog;
//
//    /**
//     * constructor.
//     *
//     * @param context calling class context.
//     */
//    private DoubleBtnDialog(@NonNull final Context context) {
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//        // custom dialog
//        dialog = new Dialog(context);
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.dialog_edit_post);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//
//
////        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
////        btnYes.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(final View v) {
////                dialog.dismiss();
////
////            }
////        });
////        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
////        btnNo.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(final View v) {
////                dialog.dismiss();
////
////            }
////        });
//    }
//
//    /**
//     * attach context.
//     *
//     * @param context context
//     * @return instance
//     */
//    @NonNull
//    public static DoubleBtnDialog with(@NonNull final Context context) {
//        return new DoubleBtnDialog(context);
//    }
//
//    /**
//     * attach context.
//     *
//     * @param bool context
//     * @return instance
//     */
//    @NonNull
//    public DoubleBtnDialog setCancelable(final boolean bool) {
//        if (dialog != null) {
//            dialog.setCancelable(false);
//        }
//        return this;
//    }
//
//    /**
//     * attach context.
//     *
//     * @param bool context
//     * @return instance
//     */
//    @NonNull
//    public DoubleBtnDialog setCancelableOnTouchOutside(final boolean bool) {
//        if (dialog != null) {
//            dialog.setCanceledOnTouchOutside(false);
//        }
//        return this;
//    }
//
//    /**
//     * attach context.
//     *
//     * @param msg context
//     * @return instance
//     */
//    @NonNull
//    public DoubleBtnDialog setCenterMessage(final String msg) {
//        if (dialog != null) {
//            TextView tvCenterText = (TextView) dialog.findViewById(R.id.tvCenterText);
//            tvCenterText.setText(msg);
//            tvCenterText.setVisibility(View.VISIBLE);
//        }
//        return this;
//    }
//
//    /**
//     * attach context.
//     *
//     * @param msg context
//     * @return instance
//     */
//    @NonNull
//    public DoubleBtnDialog setMessage(final String msg) {
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
//     * @param heading context
//     * @return instance
//     */
//    @NonNull
//    public DoubleBtnDialog setHeading(final String heading) {
//        if (dialog != null) {
//            TextView tvHeader = (TextView) dialog.findViewById(R.id.tvHeader);
//            tvHeader.setText(heading);
//            tvHeader.setVisibility(View.VISIBLE);
//        }
//        return this;
//    }
//
//    /**
//     * attach context.
//     *
//     * @param optionPositive context
//     * @return instance
//     */
//    @NonNull
//    public DoubleBtnDialog setOptionPositive(final String optionPositive) {
//        if (dialog != null) {
//            Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
//            btnYes.setText(optionPositive);
//            btnYes.setOnClickListener(new View.OnClickListener() {
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
//     * attach context.
//     *
//     * @param optionNegative context
//     * @return instance
//     */
//    @NonNull
//    public DoubleBtnDialog setOptionNegative(final String optionNegative) {
//        if (dialog != null) {
//            Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
//            btnNo.setText(optionNegative);
//            btnNo.setOnClickListener(new View.OnClickListener() {
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
//     * attach context.
//     *
//     * @param imageDrawable image to show
//     * @return instance
//     */
//    @NonNull
//    public DoubleBtnDialog setImage(final Drawable imageDrawable) {
//        if (dialog != null) {
//            ImageView ivDialogImage = (ImageView) dialog.findViewById(R.id.ivDialogImage);
//            ivDialogImage.setBackground(imageDrawable);
//        }
//        return this;
//    }
//
//    /**
//     * attach context.
//     *
//     * @param onActionPerformed context
//     * @return instance
//     */
//    @NonNull
//    public DoubleBtnDialog setCallback(@Nullable final OnActionPerformed onActionPerformed) {
//        if (dialog != null && onActionPerformed != null) {
//            Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
//            btnYes.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    dialog.dismiss();
//                    onActionPerformed.positive();
//                }
//            });
//            Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
//            btnNo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    dialog.dismiss();
//                    onActionPerformed.negative();
//                }
//            });
//        }
//        return this;
//    }
//
//    /**
//     * attach context.
//     */
//    public void show() {
//        if (dialog != null) {
//            dialog.show();
//        }
//    }
//
//    /**
//     * Interface to perform action.
//     */
//    public interface OnActionPerformed {
//        /**
//         * positive button
//         */
//        void positive();
//
//        /**
//         * -ve button
//         */
//        void negative();
//
//    }
//}
