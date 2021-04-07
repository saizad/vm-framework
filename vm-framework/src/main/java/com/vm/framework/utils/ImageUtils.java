package com.vm.framework.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.vm.framework.R;

import rx.functions.Action1;

public class ImageUtils {

    static GlideUrl getUrlWithHeaders(String url, String token) {
        return new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .build());
    }

    public static void setAvatarImage(ImageView imageView, @Nullable String url) {
        setAvatarImage(imageView, url, 0, 0, null);
    }

    public static void setAvatarImageWithPlaceHolder(ImageView imageView, @Nullable String url, @Nullable String token) {
        setAvatarImage(imageView, url, R.drawable.ic_person_black_24dp, token);
    }

    public static void setAvatarImage(ImageView imageView, @Nullable String url, @DrawableRes int placeHolderDrawable, @Nullable String token) {
        setAvatarImage(imageView, url, placeHolderDrawable, 0, token);
    }

    public static void setAvatarImage(ImageView imageView, @Nullable String url, @DrawableRes int placeHolderDrawable, @DrawableRes int errorDrawable, @Nullable String token) {
        Object loadUrl = url;
        if(token != null)
            loadUrl = getUrlWithHeaders(url, token);
        Glide.with(imageView.getContext())
                .load(loadUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(placeHolderDrawable)
                .error(errorDrawable)
                .into(imageView);
    }

    public static void displayImage(ImageView imageView, @Nullable String url) {
        displayImage(imageView, url, null, null);
    }

    public static void displayImage(ImageView imageView, @Nullable String url, @Nullable String token) {
        displayImage(imageView, url, token, null);
    }

    public static void displayImage(ImageView imageView, @Nullable String url, @Nullable String token, @Nullable RequestListener<Drawable> listener) {
        Glide.with(imageView.getContext())
                .load(getUrlWithHeaders(url, token))
                .listener(listener)
                .into(imageView);
    }

    public static void displayImageWithResponse(ImageView imageView, @Nullable String url, @Nullable String token, Action1<Boolean> listener) {
        displayImage(imageView, url, token, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listener.call(false);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listener.call(true);
                return false;
            }
        });
    }

    public static void displayRoundedImage(ImageView imageView, @Nullable String url) {
        displayRoundedImage(imageView, url, R.drawable.ic_person_black_24dp);
    }

    public static void displayRoundedImage(ImageView imageView, @Nullable String url, @DrawableRes int drawable) {
        displayRoundedImage(imageView, url, drawable, null);
    }

    public static void displayRoundedImage(ImageView imageView, @Nullable String url, @DrawableRes int drawable, @Nullable String token) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(16));
        Glide.with(imageView.getContext())
                .load(getUrlWithHeaders(url, token))
                .apply(requestOptions)
                .placeholder(drawable)
                .into(imageView);
    }
}
