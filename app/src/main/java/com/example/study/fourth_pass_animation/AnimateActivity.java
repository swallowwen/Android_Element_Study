package com.example.study.fourth_pass_animation;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.study.R;

/**
 * View动画
 * Created by swallow on 2018/12/17.
 */

public class AnimateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_view);
        ImageView imageView = findViewById(R.id.iv_animate_view);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_anim);
        imageView.setAnimation(animation);
        animation.start();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimateActivity.this, LayoutAnimationActivity.class));
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right);
            }
        });

//        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
//        alphaAnimation.setFillAfter(true);
//        alphaAnimation.setDuration(1000);
//        imageView.setAnimation(alphaAnimation);
//        alphaAnimation.start();
    }
}
