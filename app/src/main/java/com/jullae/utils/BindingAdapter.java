package com.jullae.utils;

import android.content.Context;
import android.widget.ImageView;

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

}
