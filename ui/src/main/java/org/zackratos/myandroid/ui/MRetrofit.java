//package org.zackratos.myandroid.ui;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.View;
//
//import org.zackratos.basemode.mvp.BaseActivity;
//import org.zackratos.basemode.mvp.BaseUrl;
//import org.zackratos.basemode.mvp.IPresenter;
//import org.zackratos.myandroid.R;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import butterknife.OnClick;
//import me.leefeng.promptlibrary.PromptDialog;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
///**
// * Created by leaf on 2018/8/23
// */
//
//public class MRetrofit extends BaseActivity {
//
////    @BindView(R.id.btn_qq)// 点击进行网络请求
////            Button btn_qq;
//
//    //    private String userid = "aa", pwd = "1";
////    private String url = "/storage/emulated/0/截屏/666.png";
//
//    private PromptDialog promptDialog;
//
//    @Override
//    protected void initData() {
//        promptDialog = new PromptDialog(this);
//    }
//
//    @Override
//    protected int initView() {
//        return R.layout.activity_retrofit;
//    }
//
//    @Override
//    protected IPresenter getPresenter() {
//        return null;
//    }
//
//    @Override
//    protected Intent mainIntent(Context context) {
//        return null;
//    }
//
//    // 集中点击事件
//    // 测试阶段
//    // 测试结果 @GET返回正常 @POST返回为null code=500
//    @OnClick({R.id.btn_qq})
//    public void oncilk(View view) {
//        switch (view.getId()) {
//            case R.id.btn_qq:
//                promptDialog.showLoading("正在登录");
//
//
//                // 用于苏州app测试
////                Retrofit aretrofit = new Retrofit.Builder().baseUrl(BaseUrl.BASEURL).build();
//////                interRetrofit interRetrofit = retrofit.create(interRetrofit.class);
////                Call<ResponseBody> call = aretrofit.create(interRetrofit.class).getUserLogin("aa", "1");
////                Log.i("TAAG", call + "+++++++++++++++++++");
////                call.enqueue(new Callback<ResponseBody>() {
////                    @Override
////                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
////                        int a = 0;
////                        if (response.isSuccessful()) {
////                            try {
////                                Log.i("TAAG", response.body().string() + "===============");
////                            } catch (IOException e) {
////                                e.printStackTrace();
////                            }
////                        } else {
////                            Log.i("TAAG", "SFail------------------");
////                            Log.i("TAAG", response.body() + "===============");
////                        }
////                    }
////                    @Override
////                    public void onFailure(Call<ResponseBody> call, Throwable t) {
////                        Log.i("TAAG", "Fail------------------");
////                    }
////                });
//
//
//
//                // 用于测试导购通
//                // 测试提示框架成功
//                Retrofit aretrofit = new Retrofit.Builder().baseUrl(BaseUrl.BASEURL).build();// 固定写法
//                Call<ResponseBody> call = aretrofit.create(InterRetrofit.class).getUserLogin("18600000001", "123456");
//                Log.i("TAAG", call + "+++++++++++++++++++");
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        int a = 0;
//                        if (response.isSuccessful()) {
//                            try {
//                                Log.i("TAAG", response.body().string() + "===============");
//                                promptDialog.showSuccess("登录成功");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            Log.i("TAAG", "SFail------------------");
//                            Log.i("TAAG", response.body() + "===============");
//                            promptDialog.showError("登录异常");
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.i("TAAG", "Fail------------------");
//                        promptDialog.showError("登录失败");
//                    }
//                });
//
//
//
//                // 上传图片
////                String path = "/storage/emulated/0/截屏/777.png";
////                File file = new File(path);
////                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
////                MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
////                Map<String,String> params = new HashMap<>();
////                params.put("userId", "2126");
////                params.put("sex", "1");
////                params.put("age", "13");
////                params.put("idCard", "");
////                params.put("wx", "");
////                params.put("job", "");
////                params.put("email", "");
////                Retrofit aretrofit = new Retrofit.Builder().baseUrl(BaseUrl.BASEURL).build();
////                Call<ResponseBody> call = aretrofit.create(InterRetrofit.class).getUserLogin(params, body);
////                Log.i("TAAG", call + "+++++++++++++++++++");
////                call.enqueue(new Callback<ResponseBody>() {
////                    @Override
////                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
////                        int a = 0;
////                        if (response.isSuccessful()) {
////                            try {
////                                Log.i("TAAG", response.body().string() + "===============");
////                            } catch (IOException e) {
////                                e.printStackTrace();
////                            }
////                        } else {
////                            Log.i("TAAG", "SFail------------------");
////                            Log.i("TAAG", response.body() + "===============");
////                        }
////                    }
////                    @Override
////                    public void onFailure(Call<ResponseBody> call, Throwable t) {
////                        Log.i("TAAG", "Fail------------------");
////                    }
////                });
//                break;
//        }
//
//    }
//}
