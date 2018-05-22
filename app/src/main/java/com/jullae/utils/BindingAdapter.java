package com.jullae.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.jullae.GlideApp;
import com.jullae.R;

public class BindingAdapter {
    @android.databinding.BindingAdapter("imageUrl")
    public static void loadImagefromUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        if (url != null)
            GlideApp.with(context).load(new GlideUtils.CustomGlideUrl(url)).thumbnail(0.1f).into(imageView);

    }

    @android.databinding.BindingAdapter("setlike")
    public static void setLike(ImageView imageView, Boolean isLike) {
        Context context = imageView.getContext();
        if (isLike)
            imageView.setImageResource(R.drawable.ic_like);
        else imageView.setImageResource(R.drawable.ic_unlike);


    }

    @android.databinding.BindingAdapter("setfollow")
    public static void setFollow(TextView textView, Boolean isFollow) {
        Context context = textView.getContext();

        if (isFollow) {
            textView.setText("Followed");
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setBackground(context.getResources().getDrawable(R.drawable.button_active));
        } else {
            textView.setText("Follow");
            textView.setTextColor(context.getResources().getColor(R.color.black75));
            textView.setBackground(context.getResources().getDrawable(R.drawable.button_border));
        }

    }
}
