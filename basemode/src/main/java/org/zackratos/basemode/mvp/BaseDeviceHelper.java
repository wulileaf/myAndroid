package org.zackratos.basemode.mvp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


/**
 * @Date 2016/4/15
 * @author xingzy
 * @version 1.0
 * @Note 获取设备信息的类
 */
public class BaseDeviceHelper {
    /**
     * 检查网络连接，移动网络 or WIFI
     *
     * @return
     */
    public static boolean isOpenNetwork() {
        return (isMobileNetworkConnected() || isWifiConnected());
    }


    /**
     * 检查WIFI是否连接
     *
     * @return 如果连接了返回true，否则返回false
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null == wifiInfo) {
            return false;
        }
        return wifiInfo.isConnected();
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     *
     * @return 如果连接了返回true，否则返回false
     */
    public static boolean isMobileNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null == mobileNetworkInfo) {
            return false;
        }
        return mobileNetworkInfo.isConnected();
    }

    /**
     * 判断SD卡是否存在
     *
     * @return true表示SD卡存在, false表示不存在
     */
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取设备的电话号码
     *
     * @return 设备的电话号码
     */
    public static String getTelNO() {
        TelephonyManager telephoneManager = (TelephonyManager) getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String telNo = telephoneManager.getLine1Number();
        return telNo;
    }

    /**
     * 获取设备IMEI
     *
     * @return 设备IMEI号
     */
    public static String getMchId() {
        TelephonyManager telephoneManager = (TelephonyManager) getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String mchId = telephoneManager.getDeviceId();
        return mchId;
    }

    /**
     * 获得设备的分辨率,返回值为宽度和高度拼成的point类型值
     *
     * @param activity
     * @return Point(width, height)
     */
    public static Point getResolution(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Point currentDpi = new Point(dm.widthPixels, dm.heightPixels);
        return currentDpi;
    }

    /**
     * 获得程序的上下文
     *
     * @return Context 程序的上下文
     */
    private static Context getContext() {
//        return ApplicationHelper.getInstance().getApplicationContext();
        return BaseApplication.getContext().getApplicationContext();
    }

    /**
     * 获得系统的版本号
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机唯一id
     *
     * @return
     */
    public static String getPhoneSerial() {
        return Build.SERIAL;
    }

}
