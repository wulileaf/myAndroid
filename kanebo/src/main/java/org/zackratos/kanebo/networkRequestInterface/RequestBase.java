package org.zackratos.kanebo.networkRequestInterface;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.basemode.mvp.BaseNetworkDetection;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Retrofit请求的简单封装
 *
 * @author leaf
 * @Date 2020/5/9
 */
public class RequestBase {

    //    private JSONObject object;
    JSONObject json;

    /**
     * @note 网络请求简单封装
     * @author leaf
     * @function 获取InterRetrofit对象
     * @version 1.0
     */
    public InterRetrofit getInterRetrofit(Context context, String baseUrl) {

        // 进行登录请求之前查看手机的网络环境
        Boolean yrn = BaseNetworkDetection.isNetworkAvailable(context);
        if (yrn) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            InterRetrofit inter = retrofit.create(InterRetrofit.class);
            return inter;
        } else {
            Toast.makeText(context, "当前手机未连接网络，请开启网络后重试！", Toast.LENGTH_LONG).show();
        }
        return null;
    }


    /**
     * @note 进行网络请求
     * @author leaf
     * @function 获取getRequest结果
     * @version 1.0
     */
    // Call<ResponseBody> datajs = inter.getUserLogin("18600000001", "123456");
    public JSONObject getRequest(Call<ResponseBody> datajs) {

        datajs.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    json = new JSONObject(response.body().string());
                    Log.i("wx", "----------" + json + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                json = null;
            }
        });
        return json;
    }
}
