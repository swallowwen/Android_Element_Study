package com.example.study.thirteen_pass_glide;

import android.content.Context;
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
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.study.R;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class GlideTestActivity extends AppCompatActivity {
    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        iv = findViewById(R.id.iv_image);
        final String imgUrl = "http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87%20%E7%BE%8E%E5%A5%B3%20%E7%9F%AD%E8%A3%99%20%E7%9C%9F%E7%A9%BA&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=&latest=&copyright=&cs=1036725056,1311775701&os=3375421162,631467612&simid=0,0&pn=1&rn=1&di=58300&ln=860&fr=&fmq=1553156063710_R&fm=result&ic=&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fwww.vpbao.com%2Fcontent%2Fuploadfile%2F201811%2F436e2dcd1562bad22117d338af9caa5d20181102101005.jpg&rpstart=0&rpnum=0&adpicid=0&force=undefined&ctd=1553156066889^3_1903X920%1";
//        final String imgUrl = "http://guolin.tech/test.gif";
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
        Glide.with(this).load(imgUrl).into(target);
        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        e.printStackTrace();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.e("tag", "加载完成");
                        return false;
                    }
                }).into(iv);
    }
}
