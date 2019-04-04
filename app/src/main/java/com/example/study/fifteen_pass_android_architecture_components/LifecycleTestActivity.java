package com.example.study.fifteen_pass_android_architecture_components;

import android.os.Bundle;
import android.widget.TextView;

import com.example.study.R;
import com.example.study.databinding.ActivityFifteenBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LifecycleTestActivity extends AppCompatActivity implements LifecycleOwner {
    private LifecycleRegistry registry;
    private TextView tvName, tvAge, tvGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFifteenBinding activityFifteenBinding = DataBindingUtil.setContentView(this, R.layout.activity_fifteen);
        registry = new LifecycleRegistry(this);
        registry.addObserver(new LifecycleObserverTest());
//        tvName = findViewById(R.id.tv_name);
//        tvAge = findViewById(R.id.tv_age);
//        tvGender = findViewById(R.id.tv_sex);
        User user=new User();
        user.setName("Jone");
        user.setAge(20);
        user.setSex("male");
        activityFifteenBinding.setUser(user);
//        TestViewModel viewModel = ViewModelProviders.of(this).get(TestViewModel.class);
//        viewModel.getUser().observe(this, user -> {
//            tvName.setText(user.getName());
//            tvAge.setText(String.valueOf(user.getAge()));
//            tvGender.setText(user.getSex());
//        });
        findViewById(R.id.btn_change).setOnClickListener(v -> {
            User user1 = new User();
            user1.setName("Amy");
            user1.setSex("female");
            user1.setAge(18);
//            viewModel.getUser().setValue(user);
            activityFifteenBinding.setUser(user1);
        });
    }

    @Override
    public Lifecycle getLifecycle() {
        return registry;
    }
}
