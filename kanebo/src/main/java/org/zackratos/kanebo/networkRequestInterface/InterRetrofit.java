package org.zackratos.kanebo.networkRequestInterface;

import org.zackratos.kanebo.RetJson;
import org.zackratos.kanebo.xml.XmlDownData;
import org.zackratos.kanebo.xml.XmlGetOutPlanHardwareStoreList;
import org.zackratos.kanebo.xml.XmlLogin;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by leaf on 2018/8/23
 * Retrofit进行网络请求接口集中处理的地方
 */


// Android Retrofit文件参考地址：https://www.jianshu.com/p/865e9ae667a0
public interface InterRetrofit {

    // 测试登录接口
    // 测试结果 @GET返回正常 @POST返回为null code=500
    // @Query 只能用于GET
    // @Body 只能用于POST
//    @GET("UserLogin")
//    Call<ResponseBody> getUserLogin(@Query("userid") String userid, @Query("pwd") String pwd);

    // 注意@Query不能用于POST请求中
    // 用于测试苏州App的登录接口
    // 测试OK
//    @FormUrlEncoded
//    @POST("UserLogin")
//    Call<ResponseBody> getUserLogin(@Field("userid") String userid, @Field("pwd") String pwd);

    // 用于测试导购通的登录接口
    // 测试OK
//    @FormUrlEncoded
//    @POST("Login")
//    Call<ResponseBody> getUserLogin(@Field("userName") String userid, @Field("password") String pwd);

    // 测试图片上传
//    @Multipart
//    @POST("UserInfoEdit")
//    Call<ResponseBody> getUserLogin(@Body Map<String, String> params, @Part MultipartBody.Part file);

    // 2018/11/21
    // 测试百事登录Api
//    @POST("WxUserLogin")
//    @FormUrlEncoded
//    Call<ResponseBody> login(@Field("userName") String userName, @Field("passWord") String passWord);

    // 佳丽宝获取当日拜访数据接口
    @POST("WxUserGetPlanOut")
    @FormUrlEncoded
    Call<ResponseBody> getPlanOut(@Field("EmpId") String empId);

    // 纽大人脸门禁测试地址
//    @POST("AndroidAPI/ListUser")
//    @FormUrlEncoded
//    Call<String[]> getUserData(@Field("name") String name);

    // 纽大人脸门禁测试地址
    @POST("AndroidAPI/ListUser")
    @FormUrlEncoded
    Call<ResponseBody> getUserData(@Field("name") String name);


    // 通过code获取access_token
    @POST("sns/oauth2/access_token")
    @FormUrlEncoded
    Call<ResponseBody> getaccesstoken(@Field("appid") String appid,
                                      @Field("secret") String secret,
                                      @Field("code") String code,
                                      @Field("grant_type") String grant_type);

    // 获取微信用户基本信息userinfo
    @POST("/sns/userinfo")
    @FormUrlEncoded
    Call<ResponseBody> getuserinfo(@Field("access_token") String access_token,
                                   @Field("openid") String openid);


    // opple流通正式环境接口

    // 登录接口
    // 欧普照明用户登录
    // UserLogin
//    @FormUrlEncoded
//    @POST("UserLogin")
//    Call<XmlLogin> getUserLogin(@Field("userName") String userid,
//                                @Field("pwd") String pwd,
//                                @Field("imei") String imei,
//                                @Field("appVersion") String appVersion,
//                                @Field("appH5Version") String appH5Version,
//                                @Field("deviceType") String deviceType,
//                                @Field("freeSpace") String freeSpace,
//                                @Field("mobileVersion") String mobileVersion,
//                                @Field("channeltype") String channeltype,
//                                @Field("prand") String prand,
//                                @Field("phoneModel") String phoneModel,
//                                @Field("operatingSystem") String operatingSystem,
//                                @Field("runmemory") String runmemory,
//                                @Field("appid") String appid);

    // 登录
    @FormUrlEncoded
    @POST("UserLogin")
    Call<XmlLogin> login(@FieldMap Map<String, String> params);

    // 下载数据
    @FormUrlEncoded
    @POST("GetDictionaryList")
    Call<XmlDownData> XmlDownData(@FieldMap Map<String, String> params);

    // 获取计划外五金网点数据
    @FormUrlEncoded
    @POST("GetOutPlanHardwareStoreList")
    Call<XmlGetOutPlanHardwareStoreList> storeList(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("WxUserLogin")
    Call<ResponseBody> wxlogin(@FieldMap Map<String, String> params);
}
