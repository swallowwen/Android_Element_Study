package com.example.study.fourth_pass_gesturedetector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.study.R;

/**
 * Created by swallow on 2019/1/3.
 */

public class GestureDetectorActivity extends AppCompatActivity {
    private GestureDetector.SimpleOnGestureListener gestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.e("tag", "========双击事件=========");
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    //点击事件发生300ms后触发
                    Log.e("tag", "========单击确认=========");
                    return super.onSingleTapConfirmed(e);
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    //双击事件确定发生时对第二次按下产生的MotionEvent信息进行回调
                    Log.e("tag", "========双击事件回调=========");
                    return super.onDoubleTapEvent(e);
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    Log.e("tag", "========按下=========");
                    return super.onDown(e);
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    //e1表示手指按下时的Event，e2表示手指抬起时的Event
                    // velocityX表示x上的运动速度（像素/s），velocityY表示y方向上的运动速度（像素/s）
                    Log.e("tag", "========一扔=========");
                    return super.onFling(e1, e2, velocityX, velocityY);
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    //e1表示手指按下时的Event，e2表示手指抬起时的Event
                    // distanceX表示x轴上划过的距离，distanceY表示y轴上划过的距离
                    Log.e("tag", "========滚动=========");
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Log.e("tag", "========长按=========");
                    super.onLongPress(e);
                }

                @Override
                public void onShowPress(MotionEvent e) {
                    //用户按下时的一种回调，用户手指按下后立即抬起或者事件立即被拦截，时间没有超过 180 ms的话，
                    // 这条消息会被 remove 掉，不会触发这个回调
                    Log.e("tag", "========触摸反馈=========");
                    super.onShowPress(e);
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.e("tag", "========单击抬起时=========");
                    return super.onSingleTapUp(e);
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesturedetector);
        final GestureDetector detector = new GestureDetector(this, gestureListener);
        findViewById(R.id.tv_gesturedetector).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
    }
}
