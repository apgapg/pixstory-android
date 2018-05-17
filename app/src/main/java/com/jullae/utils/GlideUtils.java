package com.jullae.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.jullae.GlideApp;

public class GlideUtils {

    public static void loadImagefromUrl(Context mContext, String url, ImageView imageView) {
        GlideApp.with(mContext).load(new CustomGlideUrl(url)).into(imageView);

    }

    public static class CustomGlideUrl extends GlideUrl {

        public CustomGlideUrl(String url) {
            super(url);
        }

        @Override
        public String getCacheKey() {
            String url = toStringUrl();
            if (url.contains("?")) {
                return url.substring(0, url.lastIndexOf("?"));

            } else
                return url;
        }
    }
}
