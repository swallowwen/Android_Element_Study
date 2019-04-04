package com.example.study.eleven_pass_okhttp_retrofit;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<DataBean> top250 = apiService.getTop250(1, 10);
        //同步请求
        try {
            Response<DataBean> execute = top250.execute();
            if (execute.isSuccessful()) {
                DataBean result = execute.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //异步请求
        top250.enqueue(new Callback<DataBean>() {
            @Override
            public void onResponse(Call<DataBean> call, Response<DataBean> response) {
                //方法在主线程中回调
                DataBean result = response.body();
            }

            @Override
            public void onFailure(Call<DataBean> call, Throwable t) {
                //方法在主线程中回调
            }
        });
    }
}
