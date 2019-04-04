package com.example.study.fourteen_pass_annotation;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

import com.example.study.R;
import com.example.study.third_pass_customer_view.CircleView;

import java.lang.reflect.Field;

public class AnnotationTestActivity extends AppCompatActivity {
    @GetViewTo(R.id.btn_1)
    private Button btnOne;
    @GetViewTo(R.id.btn_2)
    private Button btnTwo;
    @GetViewTo(R.id.cl_view)
    private CircleView clView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAllAnnotationView();
    }

    /**
     * 解析注解，通过反射将View注入到控件中
     */
    private void getAllAnnotationView() {
        //获取成员变量
        Field[] fields = getClass().getDeclaredFields();
        for (Field f : fields) {
            //确定注解
            if (f.isAnnotationPresent(GetViewTo.class)) {
                //运行修改反射属性
                f.setAccessible(true);
                GetViewTo getViewTo = f.getAnnotation(GetViewTo.class);
                try {
                    //通过findViewById找到注解id的View注入成员变量中
                    f.set(this, findViewById(getViewTo.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
