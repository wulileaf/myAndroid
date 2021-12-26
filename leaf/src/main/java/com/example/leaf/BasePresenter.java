package com.example.leaf;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class BasePresenter {
    private final String str = "你好";
    public String name = "";
    public static final String OPPLELTURL = "http://demo1.acsalpower.com:8888/DataWebService.asmx/";// opple流通服务器

    ViewInterface viewInterface;

    public BasePresenter(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    // 关闭了这个在主页面就不会显示数据
    // 这里相当于放置数据的地方
    public void loadData() {

    }

    // 登录接口
    public void getLogin() {
        InterRetrofit interRetrofit = new LeafRequest().getXml(OPPLELTURL);
        Call<XmlLogin> repLogin = interRetrofit.login(LoginParam.getLoginParam("test301", "123456", "A00000AE42C25A", "android", "huawei"));
        repLogin.enqueue(new Callback<XmlLogin>() {
            @Override
            public void onResponse(Call<XmlLogin> call, Response<XmlLogin> response) {
                int success = response.body().success;
                if (success == 1) {
                    viewInterface.onSuccess("1", response.body().username, response.body().list);
                } else {
                    viewInterface.onFaliure("请求失败");
                }
            }

            @Override
            public void onFailure(Call<XmlLogin> call, Throwable t) {
                viewInterface.onError("请求异常" + t);
            }
        });
    }
}
