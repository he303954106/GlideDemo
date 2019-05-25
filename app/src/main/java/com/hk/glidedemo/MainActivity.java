package com.hk.glidedemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hk.glidedemo.glide.Glide;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private String mUrls[] = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558765015992&di=40293a6794f9cc8920f329e120e4b296&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsports%2Ftransform%2F214%2Fw650h364%2F20190221%2FOgOF-htfpvzc0074840.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558765017967&di=653f551e8b7e43e2a5a1afa8c0368f81&imgtype=0&src=http%3A%2F%2Fi1.letvimg.com%2Flc06_crawler%2F201712%2F07%2F22%2F17%2F151265626206002-0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558765018986&di=14d344dc05b556db05144bb2c05fb2f2&imgtype=0&src=http%3A%2F%2F09imgmini.eastday.com%2Fmobile%2F20190214%2F20190214071055_64630101afc59aa304cebb0d84cfffb5_1.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558765022665&di=4a826f1ae2d851cea240f7cbaec5d344&imgtype=0&src=http%3A%2F%2F03.imgmini.eastday.com%2Fmobile%2F20161222%2F20161222081734_0e90e7817cc59eae58fedd98aed1d8ca_2.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558765025674&di=697256c4e4d77796a8baabbd3470cfef&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fsports%2Fpics%2Fhv1%2F94%2F227%2F1765%2F114827104.jpg"};
    private static final int READ_AND_WRITE = 1;
    private Button mBtLoad;
    private LinearLayout mLlContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtLoad = findViewById(R.id.bt_load);
        mLlContainer = findViewById(R.id.ll_container);
        mBtLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodRequiresTwoPermission();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(READ_AND_WRITE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            loadMore();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "请允许读写权限",
                    READ_AND_WRITE, perms);
        }
    }

    private void loadMore() {
        for (int i = 0; i < mUrls.length; i++) {
            final ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            mLlContainer.addView(imageView);
            String url = mUrls[i];
            //into方法将之前拼接的BitmapRequest封装好加入RequestManager中的阻塞队列中
            //再由BitmapDispatcher从队列中取出下载并加载图片
            Glide.with(this).loading(R.mipmap.ic_launcher).load(url).into(imageView);
            //            Glide.with(this).loading(R.mipmap.ic_launcher).load(url).listener(new RequestListener() {
            //                @Override
            //                public void onSuccess(Bitmap bitmap) {
            //                    imageView.setImageBitmap(bitmap);
            //                }
            //
            //                @Override
            //                public void onFailure() {
            //
            //                }
            //            })
            //            .into(imageView);
        }
    }
}
