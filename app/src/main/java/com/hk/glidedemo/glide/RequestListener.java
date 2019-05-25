package com.hk.glidedemo.glide;

import android.graphics.Bitmap;

/**
 * Created by hk on 2019/5/25.
 */
public interface RequestListener {

    void onSuccess(Bitmap bitmap);

    void onFailure();
}
