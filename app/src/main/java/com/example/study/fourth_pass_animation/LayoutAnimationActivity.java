package com.example.study.fourth_pass_animation;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.study.R;

/**
 * 使用LayoutAnimation为ViewGroup的子View添加入场动画
 * Created by swallow on 2018/12/18.
 */

public class LayoutAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);
        RecyclerView recyclerView = findViewById(R.id.rlv_layout_anim);
        RecyclerView.LayoutManager manager =
                new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new LayoutAnimAdapter());
        LayoutAnimationController layoutAnimationController =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_slide_from_right);
        recyclerView.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right);
    }
}
