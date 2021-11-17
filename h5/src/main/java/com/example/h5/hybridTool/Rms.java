package com.example.h5.hybridTool;

import android.content.Context;
import android.content.SharedPreferences;

public class Rms {
    private static final String RMS = "rms";

    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String USER_PWD = "pwd";
    public static final String DATA = "data";
    public static final String GONGGAO = "gonggao";
    public static final String OPEN = "open";
    /**
     * 1表示更新完成
     * 0表示需要更新
     */
    public static final String UPDATE_HTML = "update_html";

    public static void cleanSPInfo(Context context, String configName) {
        SharedPreferences sp = getRms(context);
        sp.edit().clear().commit();
    }

    // 获取SharedPreferences对象
    private static SharedPreferences getRms(Context context) {
        SharedPreferences rms = context.getSharedPreferences(RMS, Context.MODE_PRIVATE);
        return rms;
    }

    // 保存数据
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = getRms(context);
        sp.edit().putString(StringTool.getLowerCase(key), value).commit();// 参数1：存储数据的key;参数2：存储数据的值;
    }

    // 获取数据
    public static String getString(Context context, String key) {
        SharedPreferences sp = getRms(context);
        return sp.getString(StringTool.getLowerCase(key), "");// 参数1：要获取数据的key;参数2：默认获取的值;
    }

//	public static void putBoolean(Context context, String key, boolean value) {
//		SharedPreferences sp = getRms(context);
//		sp.edit().putBoolean(StringTool.getLowerCase(key), value).commit();
//	}
//
//	public static boolean getBoolean(Context context, String key) {
//		SharedPreferences sp = getRms(context);
//		return sp.getString(StringTool.getLowerCase(key), "");
//	}
}
