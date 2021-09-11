package org.zackratos.basemode.mvp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;

/**
 * @author 丁志祥
 * @version 1.0
 * @Date 2017/9/14
 * @Note 表单
 */
public class BaseSp {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    // SPUtils构造函数
    public BaseSp(Context context) {
        // 参数1:即是表单的名称,参数2:为操作模式目前就只有这一种
        sp = context.getSharedPreferences(BaseConstant.PROJECTNAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.apply();
    }

    public void put(String key, @Nullable String value) {
        editor.putString(key, value).apply();
    }

    // SP中读取String
    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void put(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void put(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public long getLong(String key) {
        return getLong(key, -1L);
    }

    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void put(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public float getFloat(String key) {
        return getFloat(key, -1f);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void put(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void put(String key, @Nullable Set<String> values) {
        editor.putStringSet(key, values).apply();
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    public Set<String> getStringSet(String key, @Nullable Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public void remove(String key) {
        editor.remove(key).apply();
    }

    public boolean contains(String key) {
        return sp.contains(key);
    }

    public void clear() {
        editor.clear().apply();
    }

    // 手机当前网络环境
    public void saveNetWork(int network) {
        editor.putInt("NetWork", network).apply();
    }

    // 保存用户信息
    public void saveUserInfo(String userInfo) {
        editor.putString("USERINFO", userInfo).apply();
    }

    // 获取用户信息
    public String getUserInfo() {
        return sp.getString("USERINFO", null);
    }

    // 保存用户登录标识
    public void saveLoginDate(String type, Integer logindate) {
        editor.putInt(type, logindate).apply();
    }

    // 获取用户登录标识
    public Integer getLoginDate(String type) {
        return sp.getInt(type, 0);
    }

    // 保存上一次登录人员的账号
    public void saveLoginAccount(String account) {
        editor.putString("userAccount", account).apply();
    }

    // 获取上一次登录人员的账号
    public String getLoginAccount() {
        return sp.getString("userAccount", "");
    }

}
