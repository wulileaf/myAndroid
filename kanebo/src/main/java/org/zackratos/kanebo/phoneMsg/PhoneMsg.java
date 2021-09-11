package org.zackratos.kanebo.phoneMsg;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Locale;

// 获取设备相关信息
public class PhoneMsg {
    /**
     * 获取设备宽度（px）
     */
    public static int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     */
    public static int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取设备的唯一标识， 需要 “android.permission.READ_Phone_STATE”权限
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "UnKnown";
        } else {
            return deviceId;
        }
    }

    /**
     * 获取厂商名
     **/
    public static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取产品名
     **/
    public static String getDeviceProduct() {
        return android.os.Build.PRODUCT;
    }

    /**
     * 获取手机品牌
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机主板名
     */
    public static String getDeviceBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * 设备名
     **/
    public static String getDeviceDevice() {
        return android.os.Build.DEVICE;
    }

    /**
     * fingerprit 信息
     **/
    public static String getDeviceFubgerprint() {
        return android.os.Build.FINGERPRINT;
    }

    /**
     * 硬件名
     **/
    public static String getDeviceHardware() {
        return android.os.Build.HARDWARE;
    }

    /**
     * 主机
     **/
    public static String getDeviceHost() {
        return android.os.Build.HOST;
    }

    /**
     * 显示ID
     **/
    public static String getDeviceDisplay() {
        return android.os.Build.DISPLAY;
    }

    /**
     * ID
     **/
    public static String getDeviceId() {
        return android.os.Build.ID;
    }

    /**
     * 获取手机用户名
     **/
    public static String getDeviceUser() {
        return android.os.Build.USER;
    }

    /**
     * 获取手机 硬件序列号
     **/
    public static String getDeviceSerial() {
        return android.os.Build.SERIAL;
    }

    /**
     * 获取手机Android 系统SDK
     *
     * @return
     */
    public static int getDeviceSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android 版本
     *
     * @return
     */
    public static String getDeviceAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取当前手机系统语言。
     */
    public static String getDeviceDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     */
    public static String getDeviceSupportLanguage() {
        Log.e("wangjie", "Local:" + Locale.GERMAN);
        Log.e("wangjie", "Local:" + Locale.ENGLISH);
        Log.e("wangjie", "Local:" + Locale.US);
        Log.e("wangjie", "Local:" + Locale.CHINESE);
        Log.e("wangjie", "Local:" + Locale.TAIWAN);
        Log.e("wangjie", "Local:" + Locale.FRANCE);
        Log.e("wangjie", "Local:" + Locale.FRENCH);
        Log.e("wangjie", "Local:" + Locale.GERMANY);
        Log.e("wangjie", "Local:" + Locale.ITALIAN);
        Log.e("wangjie", "Local:" + Locale.JAPAN);
        Log.e("wangjie", "Local:" + Locale.JAPANESE);
        return Locale.getAvailableLocales().toString();
    }

    /**
     * 判断SD是否挂载
     */
    public static boolean isSDCardMount() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机存储 ROM 信息
     * <p>
     * type： 用于区分内置存储于外置存储的方法
     * <p>
     * 内置SD卡 ：INTERNAL_STORAGE = 0;
     * <p>
     * 外置SD卡： EXTERNAL_STORAGE = 1;
     **/
    public static String getStorageInfo(Context context, int type) {

        String path = getStoragePath(context, type);
        /**
         * 无外置SD 卡判断
         * **/
        if (isSDCardMount() == false || TextUtils.isEmpty(path) || path == null) {
            return "无外置SD卡";
        }

        File file = new File(path);
        StatFs statFs = new StatFs(file.getPath());
        String stotageInfo;

        long blockCount = statFs.getBlockCountLong();
        long bloackSize = statFs.getBlockSizeLong();
        long totalSpace = bloackSize * blockCount;

        long availableBlocks = statFs.getAvailableBlocksLong();
        long availableSpace = availableBlocks * bloackSize;

        stotageInfo = "可用/总共："
                + Formatter.formatFileSize(context, availableSpace) + "/"
                + Formatter.formatFileSize(context, totalSpace);

        return stotageInfo;
    }

    /**
     * 使用反射方法 获取手机存储路径
     **/
    public static String getStoragePath(Context context, int type) {

//        StorageManager sm = (StorageManager) context
//                .getSystemService(Context.STORAGE_SERVICE);
//        try {
//            Method getPathsMethod = sm.getClass().getMethod("getVolumePaths",
//                    null);
//            String[] path = (String[]) getPathsMethod.invoke(sm, null);
//
//            switch (type) {
//                case 0:
//                    return path[type];
//                case 1:
//                    if (path.length > 1) {
//                        return path[type];
//                    } else {
//                        return null;
//                    }
//
//                default:
//                    break;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     * 获取 手机 RAM 信息 方法 一
     */
    public static String getTotalRAM(Context context) {
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        size = outInfo.totalMem;

        return Formatter.formatFileSize(context, size);
    }

    /**
     * 手机 RAM 信息 方法 二
     */
    public static String getTotalRAMOther(Context context) {
        String path = "/proc/meminfo";
        String firstLine = null;
        int totalRam = 0;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader, 8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (firstLine != null) {

            totalRam = (int) Math.ceil((new Float(Float.valueOf(firstLine)
                    / (1024 * 1024)).doubleValue()));

            long totalBytes = 0;

        }

        return Formatter.formatFileSize(context, totalRam);
    }

    /**
     * 获取 手机 可用 RAM
     */
    public static String getAvailableRAM(Context context) {
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        size = outInfo.availMem;

        return Formatter.formatFileSize(context, size);
    }


    public static String getDeviceAllInfo(Context context) {

        return "\n\n1. IMEI:\n\t\t" + getIMEI(context)

                + "\n\n2. 设备宽度:\n\t\t" + getDeviceWidth(context)

                + "\n\n3. 设备高度:\n\t\t" + getDeviceHeight(context)

                + "\n\n4. 是否有内置SD卡:\n\t\t" + SDCardUtils.isSDCardMount()

                + "\n\n5. RAM 信息:\n\t\t" + SDCardUtils.getRAMInfo(context)

                + "\n\n6. 内部存储信息\n\t\t" + SDCardUtils.getStorageInfo(context, 0)

                + "\n\n7. SD卡 信息:\n\t\t" + SDCardUtils.getStorageInfo(context, 1)

//                + "\n\n8. 是否联网:\n\t\t" + Utils.isNetworkConnected(context)
//
//                + "\n\n9. 网络类型:\n\t\t" + Utils.GetNetworkType(context)

                + "\n\n10. 系统默认语言:\n\t\t" + getDeviceDefaultLanguage()

                + "\n\n11. 硬件序列号(设备名):\n\t\t" + android.os.Build.SERIAL

                + "\n\n12. 手机型号:\n\t\t" + android.os.Build.MODEL

                + "\n\n13. 生产厂商:\n\t\t" + android.os.Build.MANUFACTURER

                + "\n\n14. 手机Fingerprint标识:\n\t\t" + android.os.Build.FINGERPRINT

                + "\n\n15. Android 版本:\n\t\t" + android.os.Build.VERSION.RELEASE

                + "\n\n16. Android SDK版本:\n\t\t" + android.os.Build.VERSION.SDK_INT

                + "\n\n17. 安全patch 时间:\n\t\t" + android.os.Build.VERSION.SECURITY_PATCH

//                + "\n\n18. 发布时间:\n\t\t" + Utils.Utc2Local(android.os.Build.TIME)

                + "\n\n19. 版本类型:\n\t\t" + android.os.Build.TYPE

                + "\n\n20. 用户名:\n\t\t" + android.os.Build.USER

                + "\n\n21. 产品名:\n\t\t" + android.os.Build.PRODUCT

                + "\n\n22. ID:\n\t\t" + android.os.Build.ID

                + "\n\n23. 显示ID:\n\t\t" + android.os.Build.DISPLAY

                + "\n\n24. 硬件名:\n\t\t" + android.os.Build.HARDWARE

                + "\n\n25. 产品名:\n\t\t" + android.os.Build.DEVICE

                + "\n\n26. Bootloader:\n\t\t" + android.os.Build.BOOTLOADER

                + "\n\n27. 主板名:\n\t\t" + android.os.Build.BOARD

                + "\n\n28. CodeName:\n\t\t" + android.os.Build.VERSION.CODENAME
                + "\n\n29. 语言支持:\n\t\t" + getDeviceSupportLanguage();

    }
}
