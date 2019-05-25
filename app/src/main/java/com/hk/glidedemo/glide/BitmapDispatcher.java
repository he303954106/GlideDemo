package com.hk.glidedemo.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hk on 2019/5/25.
 */
public class BitmapDispatcher extends Thread {

    private Handler handler = new Handler(Looper.getMainLooper());

    private LinkedBlockingQueue<BitmapRequest> requestQueue;

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                BitmapRequest br = requestQueue.take();
                showLoadingImg(br);
                Bitmap bitmap = findBitmap(br);
                showImageView(br, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showLoadingImg(BitmapRequest br) {
        if (br.getResId() > 0 && br.getImageView() != null) {
            final int resId = br.getResId();
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });
        }
    }

    private Bitmap findBitmap(BitmapRequest br) {
        Bitmap bitmap = null;
        bitmap = downloadImage(br.getUrl());
        return bitmap;
    }

    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    private Bitmap downloadImage(String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void showImageView(final BitmapRequest br, final Bitmap bitmap) {
        if (bitmap != null && br.getImageView() != null
                && br.getUrlMd5().equals(br.getImageView().getTag())) {
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (br.getRequestListener() != null) {
                        RequestListener listener = br.getRequestListener();
                        listener.onSuccess(bitmap);
                    } else {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }
}
