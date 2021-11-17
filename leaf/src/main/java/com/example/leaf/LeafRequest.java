package com.example.leaf;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

// 传方法名怎么处理？？？

/**
 * @author leaf
 * @Notes 网络请求基本类
 * @ps 本接口请求给予retrofit框架
 */
public class LeafRequest {

    // JSON数据返回处理
    public InterRetrofit getJson(String BaseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        InterRetrofit intera = retrofit.create(InterRetrofit.class);
        return intera;
    }

    // XML数据返回处理
    public InterRetrofit getXml(String BaseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        InterRetrofit intera = retrofit.create(InterRetrofit.class);
        return intera;
    }

}
