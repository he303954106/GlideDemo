package com.hk.glidedemo.glide;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

/**
 * Created by hk on 2019/5/25.
 */
public class BitmapRequest {

    private String url;

    private Context context;

    private SoftReference<ImageView> imageView;

    private int resId;

    private RequestListener requestListener;

    private String urlMd5;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest load(String url) {
        this.url = url;
        this.urlMd5 = MD5Utils.toMD5(url);
        return this;
    }

    public BitmapRequest loading(int resId) {
        this.resId = resId;
        return this;
    }

    public BitmapRequest listener(RequestListener listener) {
        this.requestListener = listener;
        return this;
    }

    public void into(ImageView imageView) {
        imageView.setTag(this.urlMd5);
        this.imageView = new SoftReference<>(imageView);
        RequestManager.getInstance().addBitmapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public int getResId() {
        return resId;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public String getUrlMd5() {
        return urlMd5;
    }
}
