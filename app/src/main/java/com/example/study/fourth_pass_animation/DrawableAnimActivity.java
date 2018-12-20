package com.example.study.fourth_pass_animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.study.R;

/**
 * Created by swallow on 2018/12/20.
 */

public class DrawableAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_anim);
        ImageView imageView = findViewById(R.id.iv_drawable_anim);
//        imageView.setImageResource(R.drawable.drawable_anim);
//        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
//        animationDrawable.start();

        AnimationDrawable animationDrawable1=new AnimationDrawable();
        animationDrawable1.addFrame(getResources().getDrawable(R.drawable.bao1),50);
        animationDrawable1.addFrame(getResources().getDrawable(R.drawable.bao2),50);
        animationDrawable1.addFrame(getResources().getDrawable(R.drawable.bao3),50);
        animationDrawable1.addFrame(getResources().getDrawable(R.drawable.bao4),50);
        animationDrawable1.addFrame(getResources().getDrawable(R.drawable.bao5),50);
        animationDrawable1.addFrame(getResources().getDrawable(R.drawable.bao6),50);
        animationDrawable1.addFrame(getResources().getDrawable(R.drawable.bao7),50);
        animationDrawable1.addFrame(getResources().getDrawable(R.drawable.bao8),50);
        animationDrawable1.setOneShot(false);
        imageView.setImageDrawable(animationDrawable1);
        animationDrawable1.start();
    }
}
