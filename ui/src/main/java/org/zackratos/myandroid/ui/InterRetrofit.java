//package org.zackratos.myandroid.ui;
//
//import java.util.Map;
//
//import okhttp3.MultipartBody;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.POST;
//import retrofit2.http.Part;
//
///**
// * Created by leaf on 2018/8/23
// * Retrofit进行网络请求接口集中处理的地方
// */
//
//public interface InterRetrofit {
//
//    // 测试登录接口
//    // 测试结果 @GET返回正常 @POST返回为null code=500
//    // @Query 只能用于GET
//    // @Body 只能用于POST
////    @GET("UserLogin")
////    Call<ResponseBody> getUserLogin(@Query("userid") String userid, @Query("pwd") String pwd);
//
//    // 注意@Query不能用于POST请求中
//    // 用于测试苏州App的登录接口
//    // 测试OK
////    @FormUrlEncoded
////    @POST("UserLogin")
////    Call<ResponseBody> getUserLogin(@Field("userid") String userid, @Field("pwd") String pwd);
//
//    // 用于测试导购通的登录接口
//    // 测试OK
//    @FormUrlEncoded
//    @POST("Login")
//    Call<ResponseBody> getUserLogin(@Field("userName") String userid, @Field("password") String pwd);
//
//
//    // 测试图片上传
////    @Multipart
//    @POST("UserInfoEdit")
//    Call<ResponseBody> getUserLogin(@Body Map<String, String> params, @Part MultipartBody.Part file);
//
//
//    // 2018/11/21
//    // 测试百事登录Api
//    @POST("WxUserLogin")
//    @FormUrlEncoded
//    Call<ResponseBody> login(@Field("userName") String userName, @Field("passWord") String passWord);
//
//
//    // 佳丽宝获取当日拜访数据接口
//    @POST("WxUserGetPlanOut")
//    @FormUrlEncoded
//    Call<ResponseBody> getPlanOut(@Field("EmpId") String empId);
//
//    // 纽大人脸门禁测试地址
////    @POST("AndroidAPI/ListUser")
////    @FormUrlEncoded
////    Call<String[]> getUserData(@Field("name") String name);
//
//    // 纽大人脸门禁测试地址
//    @POST("AndroidAPI/ListUser")
//    @FormUrlEncoded
//    Call<ResponseBody> getUserData(@Field("name") String name);
//
//}
