package com.example.study.eleven_pass_okhttp_retrofit;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建OkHttpClient对象
        OkHttpClient httpClient = new OkHttpClient();
        //创建Request对象
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .addHeader("key", "value")
                .get()
                .build();
        //get方法同步请求
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //get方法异步请求
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                String result = response.body().string();
            }
        });

        //post请求
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String data = "{key:value}";
        RequestBody requestBody = RequestBody.create(mediaType, data);
        Request request1 = new Request.Builder()
                .url("http://www.baidu.com")
                .post(requestBody)
                .build();
        //post同步请求
        try {
            Response response = httpClient.newCall(request1).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //post异步请求
        httpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                String result = response.body().string();
            }
        });

        //创建拦截器
        Interceptor logInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request2 = chain.request();
                Logger logger = Logger.getGlobal();
                long t1 = System.nanoTime();
                logger.info(String.format("Sending request %s on %s%n%s"
                        , request2.url(), chain.connection(), request2.headers()));
                Response response = chain.proceed(request2);
                long t2 = System.nanoTime();
                logger.info(String.format("Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                return response;
            }
        };
        //为OkHttpClient添加拦截器、超时、缓存控制
        Cache cache = new Cache(new File(Environment.getDataDirectory(), "cache"), 10 * 10 * 1024);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//连接超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//写操作超时时间
                .readTimeout(10, TimeUnit.SECONDS)//读操作超时时间
                .cache(cache)//添加缓存
                .addInterceptor(logInterceptor)
                .build();
    }
}
