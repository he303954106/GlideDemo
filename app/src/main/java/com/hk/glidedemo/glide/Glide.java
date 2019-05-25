package com.hk.glidedemo.glide;

import android.content.Context;

/**
 * Created by hk on 2019/5/25.
 */
public class Glide {

    public static BitmapRequest with(Context context) {
        return new BitmapRequest(context);
    }
}
