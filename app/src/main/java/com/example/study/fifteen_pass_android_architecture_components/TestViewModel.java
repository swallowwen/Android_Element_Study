package com.example.study.fifteen_pass_android_architecture_components;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData;

    public MutableLiveData<User> getUser() {
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
            User user = new User();
            user.setName("Jone");
            user.setAge(20);
            user.setSex("male");
            userLiveData.setValue(user);
        }
        return userLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
