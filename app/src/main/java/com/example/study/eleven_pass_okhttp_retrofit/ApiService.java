package com.example.study.eleven_pass_okhttp_retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //使用GET方法请求数据,配合@Query和@QueryMap使用
    @GET("top250")
    Call<DataBean> getTop250(@Query("start") int start, @Query("count") int count);

    //使用POST方法发送form-encoded请求数据，@FormUrlEncoded可以配合@Filed和@FieldMap使用
    @POST("top250")
    @FormUrlEncoded
    Call<DataBean> getTop250ByPost(@Field("start") int start, @Field("count") int count);

    //使用POST方法发送自定义数据类型请求数据
    @POST("top250")
    Call<DataBean> getTop250ByPostBody(@Body DataBean data);
}
