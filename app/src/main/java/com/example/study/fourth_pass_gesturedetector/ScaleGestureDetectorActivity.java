package com.example.study.fourth_pass_gesturedetector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.study.R;

/**
 * Created by swallow on 2019/1/3.
 */

public class ScaleGestureDetectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesturedetector);
        final ScaleGestureDetector detector
                = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                //缩放被触发，如果返回true则表示当前缩放事件已经被处理
                // 检测器会重新积累缩放因子，返回false则会继续积累缩放因子
                Log.e("tag", "focusX = " + detector.getFocusX());       // 缩放中心，x坐标
                Log.e("tag", "focusY = " + detector.getFocusY());       // 缩放中心y坐标
                Log.e("tag", "scale = " + detector.getScaleFactor());   // 缩放因子
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                //缩放手势开始，当两个手指放在屏幕上时会调用此方法（只调用一次）
                // 若返回false则表示不使用当前这次手势缩放
                Log.e("tag", "======onScaleBegin=======");
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                //缩放手势结束
                Log.e("tag", "======onScaleEnd=======");
            }
        });
        findViewById(R.id.tv_gesturedetector).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
    }
}
