package com.example.study;

import android.app.Application;

import com.example.study.seven_pass_sqlite.DBHelper;
import com.example.study.seven_pass_sqlite.DataBaseHelper;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataBaseHelper.initializeInstance(new DBHelper(this));
    }
}
