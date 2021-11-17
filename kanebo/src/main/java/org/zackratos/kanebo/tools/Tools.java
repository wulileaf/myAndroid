package org.zackratos.kanebo.tools;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.util.TypeKey;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.basemode.mvp.BaseSp;
import org.zackratos.kanebo.Login;

import java.lang.reflect.Type;
import java.util.Calendar;


// 各种实用工具
public abstract class Tools {

    // 本地存储
    public static BaseSp getBaseSp(Context context) {
        return new BaseSp(context);
    }

    // 本地存储用户信息对象
    public static void saveJSONUserIfon(Context context, String userid, String username, String isba, String postname, String leadername, String empcode, String orgname,
                                        String date) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
            jsonObject.put("username", username);
            jsonObject.put("isba", isba);
            jsonObject.put("postname", postname);
            jsonObject.put("leadername", leadername);
            jsonObject.put("empcode", empcode);
            jsonObject.put("orgname", orgname);
            jsonObject.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String userIfon = gson.toJson(jsonObject);
        getBaseSp(context).saveUserInfo(userIfon);
    }

    // 获取用户登录信息
    public static JSONObject getUserLoginMsg(Context context) {
        JSONObject json = null;
        Gson gson = new Gson();
        String userInfo = new BaseSp(context).getUserInfo();
        if (userInfo != null) {
            Type type = new TypeToken<JSONObject>() {
            }.getType();
            json = gson.fromJson(userInfo, type);
        }
        return json;
    }

    // 获取用户登录标识
    public static Integer getUserLoginIden(Context context) {
        Integer bs = 0;
        try {
            JSONObject date = getUserLoginMsg(context);
            if (date != null) {
                bs = getBaseSp(context).getLoginDate(date.getString("date"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bs;
    }

    // 获取当日日期
    // 格式：yyyy-MM-dd
    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String date = year + "-" + (month + 1) + "-" + day;
        return date;
    }


    /**
     * 地球半径，单位：公里/千米
     */
    private static final double EARTH_RADIUS = 6378.137;

    /**
     * 经纬度转化成弧度
     *
     * @param d 经度/纬度
     * @return 经纬度转化成的弧度
     * 测试OK
     */
    private static double radian(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 返回两个地理坐标之间的距离
     *
     * @param firsLongitude   第一个坐标的经度
     * @param firstLatitude   第一个坐标的纬度
     * @param secondLongitude 第二个坐标的经度
     * @param secondLatitude  第二个坐标的纬度
     * @return 两个坐标之间的距离，单位：公里/千米
     * 测试OK
     */
    public static double distance(double firsLongitude, double firstLatitude,
                                  double secondLongitude, double secondLatitude) {
        double firstRadianLongitude = radian(firsLongitude);
        double firstRadianLatitude = radian(firstLatitude);
        double secondRadianLongitude = radian(secondLongitude);
        double secondRadianLatitude = radian(secondLatitude);

        double a = firstRadianLatitude - secondRadianLatitude;
        double b = firstRadianLongitude - secondRadianLongitude;
        double cal = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(firstRadianLatitude) * Math.cos(secondRadianLatitude)
                * Math.pow(Math.sin(b / 2), 2)));
        cal = cal * EARTH_RADIUS;

        return Math.round(cal * 10000d) / 10000d;
    }
}
