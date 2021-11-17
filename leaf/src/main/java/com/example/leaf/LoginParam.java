package com.example.leaf;

import java.util.HashMap;
import java.util.Map;

public class LoginParam {

    // 登录接口请求参数
    public static Map<String, String> getLoginParam(String account, String password, String imei, String platform, String mobileVersion) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", account);
        params.put("pwd", password);
        params.put("imei", imei);// "A00000AE42C25A"
        params.put("appVersion", "android3.8");
        params.put("appH5Version", "4.44_lt");
        params.put("deviceType", platform);
        params.put("freeSpace", "1");
        params.put("mobileVersion", mobileVersion);
        params.put("channeltype", "1");
        params.put("prand", "1");
        params.put("phoneModel", "1");
        params.put("operatingSystem", "1");
        params.put("runmemory", "1");
        params.put("appid", "1");
        return params;
    }

    // 下载数据库表单
    public static Map<String, String> getDBParam(String userid, String lastTime) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userid);
        params.put("isAll", "1");
        params.put("lastTime", lastTime);
        params.put("startRow", "1");
        params.put("channeltype", "1");
        return params;
    }

}
