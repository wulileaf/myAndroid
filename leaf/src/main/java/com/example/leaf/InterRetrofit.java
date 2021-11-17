package com.example.leaf;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface InterRetrofit {

    // 登录接口
    @FormUrlEncoded
    @POST("UserLogin")
    Call<XmlLogin> login(@FieldMap Map<String, String> params);
}
