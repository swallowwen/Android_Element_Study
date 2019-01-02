package com.example.study.fourth_pass_animation;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.study.R;

/**
 * 属性动画
 * Created by swallow on 2018/12/24.
 */

public class ValueAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_view);
        ImageView iv = findViewById(R.id.iv_property_anim);
//        AnimatorSet animatorSet =
//                (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.property_anim);
//        animatorSet.setTarget(iv);
//        animatorSet.start();

//        ObjectAnimator objectAnimator =
//                ObjectAnimator.ofInt(iv, "x", 0, 200).setDuration(1000);
//        objectAnimator.start();

//        ViewWrapper wrapper = new ViewWrapper(iv);
//        ObjectAnimator objectAnimator =
//                ObjectAnimator.ofInt(wrapper, "width", 500).setDuration(1000);
//        objectAnimator.start();

        performAnimate(iv, iv.getWidth(), 500);
    }

    private static class ViewWrapper {
        private View view;

        public ViewWrapper(View view) {
            this.view = view;
        }

        public int getWidth() {
            return view.getLayoutParams().width;
        }

        public void setWidth(int width) {
            view.getLayoutParams().width = width;
            view.requestLayout();
        }
    }

    private void performAnimate(final View target, final int start, final int end) {
        //持有一个IntEvaluator对象，方便下面估值使用
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator intEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获得当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = animation.getAnimatedFraction();
                //直接调用整型估值器，通过比例计算出宽度，然后设置给Button
                target.getLayoutParams().width = intEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(5000).start();
    }
}
