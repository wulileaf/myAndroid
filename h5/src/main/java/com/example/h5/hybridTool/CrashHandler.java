package com.example.h5.hybridTool;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    public static final String TAG = "CrashHandler";

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    private String str, account = null;
    private String system = "android";

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 保存日志文件
        str = saveCrashInfo2File(ex);
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();


        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    // 在这里进行接口上传
    public void collectDeviceInfo(Context ctx) {

// 测试请求OK
        // 登录账号 LoginAccount
        // 登录人名称 LoginPerson
//        Log.i("666666", Rms.getString(mContext, "username"));
//        Log.i("666666", Rms.getString(mContext, "username"));
        // 测试用 --------------------------------------------------------
        String baseInfo = Rms.getString(mContext, "user");// 1,登录账号 OK
//        // 2,登录人员名称由后台根据登录账号去查询 OK
//        // 3,闪退日期 OK
//        // 4,当前时间 OK
//        Log.i("666666", "当前时间--time--" + DateTool.CurrDateTime());
//        // 5，操作系统 Android OK
//        // 6，手机型号 OK
//        Log.i("666666", "手机设备型号--model--" + PhoneTool.Model());
//        // 7，手机运行内存 OK
//        Log.i("666666", "运存----" + getAvailMemory(mContext));
//        // 8，手机操作系统 OK
//        Log.i("666666", "手机系统版本号--systemversion--" + PhoneTool.Version());

        if (baseInfo != null && !baseInfo.equals("")) {
            JSONObject base = null;
            try {
                base = new JSONObject(baseInfo);
                account = base.getString("userName");
                Log.i("666666", String.valueOf(base));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Log.i("666666", "为空");
            Toast.makeText(mContext, "获取人员为空!", Toast.LENGTH_LONG).show();
        }

        try {
            URL url = new URL("http://demo1.acsalpower.com:8888/DataWebService.asmx/SaveFlashBackLog?account=" + account + "&flashReason=" + str
                    + "&time=" + DateTool.CurrDateTime() + "&opeSystemType=" + system + "&model=" + PhoneTool.Model() + "&runMemory=" + getAvailMemory(mContext) + "&opeSystemVersion=" + PhoneTool.Version() + "&appid=" + new Tool().getPhoneSign(mContext));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30 * 1000);//设置超时时长，单位ms
            connection.setRequestMethod("GET");//设置请求格式
//            connection.setRequestProperty("Content-Type", "Application/json");//期望返回的数据格式
//            connection.setRequestProperty("CharSet", "UTF-8");//设置字符集
//            connection.setRequestProperty("Accept-CharSet", "UTF-8");//请求的字符集
            connection.connect();//发送请求

            int responseCode = connection.getResponseCode();//获取返回码
            String responseMessage = connection.getResponseMessage();//获取返回信息
            Log.i("666666", responseCode + "");
            if (responseCode == HttpURLConnection.HTTP_OK)//请求成功操作
            {
                //TODO
                String result = listStreamToString(connection.getInputStream());
                Toast.makeText(mContext, "上传成功!", Toast.LENGTH_LONG).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
//
//        StringBuffer sb = new StringBuffer();
//        for (Map.Entry<String, String> entry : infos.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            sb.append(key + "=" + value + "\n");
//        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
//        Log.i("666666", result);
//        sb.append(result);
//        try {
//            long timestamp = System.currentTimeMillis();
//            String time = formatter.format(new Date());
//            String fileName = "crash-" + time + "-" + timestamp + ".log";
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                String path = "/sdcard/jianke/crash/";
//                File dir = new File(path);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                FileOutputStream fos = new FileOutputStream(path + fileName);
//                fos.write(sb.toString().getBytes());
//                fos.close();
//            }
//            return fileName;
//        } catch (Exception e) {
//            Log.e(TAG, "an error occured while writing file...", e);
//        }
        return result;
    }


    public static String listStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while ((len = bufferedReader.readLine()) != null) {
                stringBuilder.append(len);
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     *   * 获取android当前可用运行内存大小
     *   * @param context
     *   *
     */
    public static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
// mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }

    /**
     *   * 获取android总运行内存大小
     *   * @param context
     *   *
     */
    public static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            // 获得系统总内存，单位是KB
            int i = Integer.valueOf(arrayOfString[1]).intValue();
            //int值乘以1024转换为long类型
            initial_memory = new Long((long) i * 1024);
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }
}
