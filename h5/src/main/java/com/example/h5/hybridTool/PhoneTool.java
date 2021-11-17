package com.example.h5.hybridTool;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

public class PhoneTool {
    public static boolean IsConnect(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static String getImei(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imeiSIM1 = telephonyManager.getDeviceId();
        String imeiSIM2 = null;
        try {
            imeiSIM1 = getOperatorBySlot(mContext, "getDeviceIdGemini", 0);
            imeiSIM2 = getOperatorBySlot(mContext, "getDeviceIdGemini", 1);
        } catch (GeminiMethodNotFoundException e) {
            e.printStackTrace();
            try {
                imeiSIM1 = getOperatorBySlot(mContext, "getDeviceId", 0);
                imeiSIM2 = getOperatorBySlot(mContext, "getDeviceId", 1);
            } catch (GeminiMethodNotFoundException e1) {
                // Call here for next manufacturer's predicted method name if
                // you wish
                e1.printStackTrace();
            }
        }

        if (imeiSIM1 != null && !imeiSIM1.equals("")) {
            return imeiSIM1;
        } else if (imeiSIM2 != null && !imeiSIM2.equals("")) {
            return imeiSIM2;
        }
        return "";
    }

    private static String getOperatorBySlot(Context context,
                                            String predictedMethodName, int slotID)
            throws GeminiMethodNotFoundException {
        String inumeric = null;
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> telephonyClass = Class.forName(telephony.getClass()
                    .getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName,
                    parameter);
            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephony, obParameter);
            if (ob_phone != null) {
                inumeric = ob_phone.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeminiMethodNotFoundException(predictedMethodName);
        }
        return inumeric;
    }

    private static class GeminiMethodNotFoundException extends Exception {
        private static final long serialVersionUID = -3241033488141442594L;

        public GeminiMethodNotFoundException(String info) {
            super(info);
        }
    }

    // public static boolean is(Context context)
    // {
    //
    //
    // PackageManager pm = context.getPackageManager();
    // if (PackageManager.PERMISSION_GRANTED ==
    // ContextCompat.checkSelfPermission(context,
    // android.Manifest.permission.CAMERA)) {
    //
    // Camera.startCameraUrl(context, filename, CAMERA);
    // }else{
    // //提示用户开户权限
    // String[] perms = {"android.permission.CAMERA"};
    // ActivityCompat.requestPermissions(Broadcast_publish.this,perms,
    // RESULT_CODE_STARTCAMERA);
    // }
    //
    // }

    // public static boolean getAirplaneMode(Context context) {
    // String isAirplaneMode="0";
    // // Boolean airplaneMode = true;
    // try {
    // // isAirplaneMode = Settings.System.getInt(
    // // context.getContentResolver(),
    // // Global.AIRPLANE_MODE_ON, 0);
    // ContentResolver cr = context.getContentResolver();
    //
    // isAirplaneMode =
    // Settings.System.getString(cr,Settings.Global.AIRPLANE_MODE_ON);
    // // Settings.Global.putInt(context.getContentResolver(),
    // // Global.AIRPLANE_MODE_ON, mode ? 1 : 0);
    // } catch (Exception e) {
    // // TODO: handle exception
    // String ss = e.getMessage();
    // CToast.showMsg(context, ss);
    // }
    //
    // return isAirplaneMode.equals("0")?false:true;
    // }

    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); // 针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    public static boolean getMobileDataState(Context pContext, Object[] arg) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            Class ownerClass = mConnectivityManager.getClass();
            Class[] argsClass = null;
            if (arg != null) {
                argsClass = new Class[1];
                argsClass[0] = arg.getClass();
            }
            Method method = ownerClass.getMethod("getMobileDataEnabled",
                    argsClass);
            Boolean isOpen = (Boolean) method.invoke(mConnectivityManager, arg);
            return isOpen;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("得到移动数据状态出错");
            return false;
        }
    }

    public static boolean OpenGPSSettings(Context context) {
        LocationManager alm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            // showToastMsg(context, "GPS已经打开", AlertType.INFO);
            return true;
        } else {
//            CToast.showMsg(context, "GPS未打开,请打开GPS定位");
            Intent myIntent = new Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(myIntent);
            return false;
        }
    }

    // 设备使用的android系统版本
    public static String Version() {
        return Build.VERSION.RELEASE; // 设备的系统版本
    }

    // 设备型号
    public static String Model() {
//		String sss=android.os.Build.BRAND;
        return Build.MODEL; // 设备的系统版本
    }

    // 获取品牌
    public static String Brand() {
//		String sss=android.os.Build.BRAND;
        return Build.BRAND; // 设备的系统版本
    }

}
