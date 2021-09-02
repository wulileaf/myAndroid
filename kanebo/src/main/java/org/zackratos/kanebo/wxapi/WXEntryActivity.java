package org.zackratos.kanebo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.basemode.mvp.BaseUrl;
import org.zackratos.kanebo.networkRequestInterface.InterRetrofit;
import java.io.IOException;
import androidx.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author leaf
 * @version v1.0.0
 * @Note 微信授权回调类
 * @Date 2018/11/20
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    // 这个值是在微信平台的申请的那个app,打包后（非debug包），在打包后的app应用软件里面下载一个app签名工具，在app签名工具里面输入appmanifest里面的包名，包名是package里面的，一定要是这个包名，别把包名弄混了，最后生成一个签名，放在微信开放平台的信息输入框里面）
    private static final String APP_SECRET = "a3212d497a3d77cb7022a546daf9982b";
    private IWXAPI mWeixinAPI;
    public static final String WEIXIN_APP_ID = "wx8c2b541c3cc2eb2a";// 微信开放平台后台查看 https://open.weixin.qq.com/
    private static String uuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeixinAPI = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, true);
        mWeixinAPI.handleIntent(this.getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWeixinAPI.handleIntent(intent, this);//必须调用此句话
    }

    // 微信发送的请求将回调到onReq方法
    @Override
    public void onReq(BaseReq req) {
//        LogUtils.log("onReq");
//        Log.i("TAG", "1111111");
    }

    // 发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
//        LogUtils.log("onResp");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
//                LogUtils.log("ERR_OK");
//                Log.i("TAG", "授权成功");
                // 发送成功
                Toast.makeText(WXEntryActivity.this, "授权成功", Toast.LENGTH_LONG).show();
                SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                if (sendResp != null) {
                    String code = sendResp.code;
                    getAccess_token(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                // 发送取消
                Toast.makeText(WXEntryActivity.this, "发送取消", Toast.LENGTH_LONG).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                LogUtils.log("ERR_AUTH_DENIED");
                // 发送被拒绝
                Toast.makeText(WXEntryActivity.this, "发送被拒绝", Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                // 发送返回
                finish();
                break;
        }
    }

    /**
     * 获取openid accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAccess_token(final String code) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.WXAUTHOR)
                .addConverterFactory(JacksonConverterFactory.create())// 返回的数据以json的形式进行解析
                .build();// 构建基本配置
        InterRetrofit face = retrofit.create(InterRetrofit.class);// 构建自定义接口对象
        Call<ResponseBody> call = face.getaccesstoken(WEIXIN_APP_ID, APP_SECRET, code, "authorization_code");// 放置请求的参数
        call.enqueue(new Callback<ResponseBody>() {// 进行接口请求
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    try {
                        JSONObject json = new JSONObject(result);
                        String openid = json.getString("openid").toString().trim();
                        String access_token = json.getString("access_token").toString().trim();
//                        Log.i("TAG", "" + result);
//                        Toast.makeText(WXEntryActivity.this, "请求成功", Toast.LENGTH_LONG).show();
                        getUserMesg(access_token, openid);// 获取用户个人信息
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("TAG", "连接失败！");
            }
        });
    }

    /**
     * 获取微信的个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.WXAUTHOR)
                .addConverterFactory(JacksonConverterFactory.create())// 返回的数据以json的形式进行解析
                .build();// 构建基本配置
        InterRetrofit face = retrofit.create(InterRetrofit.class);// 构建自定义接口对象
        Call<ResponseBody> call = face.getuserinfo(access_token, openid);// 放置请求的参数
        call.enqueue(new Callback<ResponseBody>() {// 进行接口请求
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    try {
                        JSONObject json = new JSONObject(result);
//                        new BaseSp(WXEntryActivity.this).put("openid", json.getString("openid"));
//                        new BaseSp(WXEntryActivity.this).put("openidTime", BaseTimeFormat.getTime("yyyy-MM-dd"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Log.e("USERINFO", "获取到的用户基本信息" + result);
//                    Toast.makeText(WXEntryActivity.this, "获取到的用户基本信息" + result, Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(WXEntryActivity.this, MainPage.class);
//                    intent.putExtra("userinfo", result);
//                    startActivity(intent);

                    finish();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("TAG", "连接失败！");
            }

//            public String getTime() {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                String time = format.format(new Date(System.currentTimeMillis()));
//                Log.i("USERINFO", time);
//                return time;
//            }
        });
    }


}
