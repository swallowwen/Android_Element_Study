package com.example.study.fourth_pass_animation;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.study.R;

/**
 * Created by swallow on 2019/1/2.
 */

public class PropertySpecialUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_property_special_usage);
        final LinearLayout llImage = findViewById(R.id.ll_image);
        LayoutTransition transition = new LayoutTransition();
        //当多个子View要执行同一个类型的动画时，就可以通过该方法来设置子View之间执行动画的间隙默认为0毫秒。
        transition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        //为指定类型的过渡动画设置执行动画的周期，默认为300毫秒。
        transition.setDuration(LayoutTransition.CHANGE_APPEARING, transition.getDuration(LayoutTransition.CHANGE_APPEARING));
        //为指定类型的过渡动画设置延迟执行的时间，默认与过渡动画的类型相关
        transition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0);
        ObjectAnimator appearingAnimator = ObjectAnimator.ofPropertyValuesHolder((Object) null,
                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
        transition.setAnimator(LayoutTransition.APPEARING, appearingAnimator);
        transition.setDuration(LayoutTransition.APPEARING, transition.getDuration(LayoutTransition.APPEARING));
        transition.setStartDelay(LayoutTransition.APPEARING, transition.getDuration(LayoutTransition.CHANGE_APPEARING));

        ObjectAnimator disappearingAnimator =  ObjectAnimator.ofPropertyValuesHolder((Object) null,
                PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f),
                PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.0f),
                PropertyValuesHolder.ofFloat("alpha", 1.0f, 0));
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearingAnimator);
        transition.setDuration(LayoutTransition.DISAPPEARING, transition.getDuration(LayoutTransition.DISAPPEARING));
        transition.setStartDelay(LayoutTransition.DISAPPEARING, 0);

        transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        transition.setDuration(LayoutTransition.CHANGE_DISAPPEARING, transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        transition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, transition.getDuration(LayoutTransition.DISAPPEARING));
        //为ViewGroup设置过渡动画。
        llImage.setLayoutTransition(transition);
        findViewById(R.id.btn_add_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = new ImageView(PropertySpecialUseActivity.this);
                imageView.setImageResource(R.mipmap.ic_launcher);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
                llImage.addView(imageView, 0, layoutParams);
            }
        });
        findViewById(R.id.btn_remove_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = llImage.getChildCount();
                if (count > 0) {
                    llImage.removeViewAt(0);
                }
            }
        });
    }
}
