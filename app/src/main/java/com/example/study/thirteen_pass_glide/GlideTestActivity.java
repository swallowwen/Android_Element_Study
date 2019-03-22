package com.example.study.thirteen_pass_glide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.study.R;

import java.io.File;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

public class GlideTestActivity extends AppCompatActivity {
    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        iv = findViewById(R.id.iv_image);
//        final String imgUrl = "http://guolin.tech/book.png";
        final String imgUrl = "http://hbimg.b0.upaiyun.com/7fd0a980609075496dd3f8b12f5cc18b6be33c5618c50-Xph1iZ_fw658";
        final RequestOptions options = new RequestOptions()
                .circleCrop()//圆形化
                .skipMemoryCache(true)//禁用内存缓存
                .override(180, 180)//自定义图片尺寸
                .placeholder(R.mipmap.ic_launcher)//加载时的占位图
                .error(R.drawable.img_error);//加载异常之后显示的占位图
        SimpleTarget<Drawable> target = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                iv.setImageDrawable(resource);
            }
        };
//        Glide.with(this).load(imgUrl).into(target);
        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                transform();
                loadImage(imgUrl);
//                Glide.with(GlideTestActivity.this).asBitmap().load(imgUrl).apply(options).into(iv);
            }
        });

    }

    private void downloadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Context context = getApplicationContext();
                FutureTarget<File> submit = Glide
                        .with(context)
                        .asFile()
                        .load("http://guolin.tech/test.gif")
                        .submit();
                try {
                    final File file = submit.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GlideTestActivity.this, file.getPath(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void loadImage(String url) {
        Glide.with(this).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("tag", e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.e("tag", "加载完成");
                        return false;
                    }
                }).into(iv);
    }

    private void transform() {
        RequestOptions options = new RequestOptions()
                .transforms(new ColorFilterTransformation(Color.argb(80, 255, 0, 0)), new BlurTransformation());
        Glide.with(this).load("http://guolin.tech/book.png").apply(options).into(iv);
    }
}
