package com.jullae.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jullae.R;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import java.util.Date;

public class BindingAdapter {
 /*   @android.databinding.BindingAdapter("imageUrl")
    public static void loadImagefromUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        if (url != null)
            GlideApp.with(context).load(new GlideUtils.CustomGlideUrl(url)).thumbnail(0.1f).into(imageView);

    }*/

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
            textView.setTextColor(context.getResources().getColor(R.color.white));
            textView.setBackground(context.getResources().getDrawable(R.drawable.button_border));
        }

    }

    @android.databinding.BindingAdapter("htmlText")
    public static void setHtmlText(TextView htmlTextView, String text) {
        if (text != null)
            htmlTextView.setText(Html.fromHtml(text));
    }

    @android.databinding.BindingAdapter("setDate")
    public static void setDate(TextView textView, Date date) {
        if (date != null)
            textView.setText(DateUtils.getTimeAgo(date));

    }

    @android.databinding.BindingAdapter("html")
    public static void setHtmlText(AutoLinkTextView autoLinkTextView, String text) {
        autoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_HASHTAG);
        autoLinkTextView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {
                Log.d("abc", "onAutoLinkTextClick: " + matchedText);
            }
        });
        if (text != null)
            autoLinkTextView.setAutoLinkText(text);
    }

    @android.databinding.BindingAdapter("refreshing")
    public static void refreshing(SwipeRefreshLayout swipeRefreshLayout, boolean isLoading) {
        if (isLoading)
            swipeRefreshLayout.setRefreshing(true);
        else swipeRefreshLayout.setRefreshing(false);
    }
}
