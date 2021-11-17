package com.example.h5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.example.h5.hybridTool.Constants;
import com.example.h5.hybridTool.CrashHandler;
import com.example.h5.hybridTool.DateTool;
import com.example.h5.hybridTool.FileTool;
import com.example.h5.hybridTool.JsonData;
import com.example.h5.hybridTool.PhoneTool;
import com.example.h5.hybridTool.PhotoTool;
import com.example.h5.hybridTool.Rms;
import com.example.h5.hybridTool.Sqlite;
import com.example.h5.hybridTool.StringTool;
import com.example.h5.hybridTool.Tool;
import com.example.h5.hybridTool.ZIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class JavaScriptinterface {

    private Handler mHandler;
    private Context mContext;
    public static final String RUNSQL = "runsql";
    public static final String GETDATABYSQL = "getdatabysql";
    public static final String GETWEEKDATA = "getweekdata";
    public static final String TODAOHANG = "todaohang";
    public static final String TEL = "tel";
    public static final String TAKEPHOTO = "takephoto";
    public static final String SELECTPHOTO = "selectphoto";
    public static final String OPENSINGLEPAGE = "opensinglepage";
    public static final String CLOSESINGLEPAGE = "closesinglepage";
    public static final String TAKESCAN = "takescan";
    public static final String OPENWINDOW = "openwindow";
    public static final String TAKELOCATION = "takelocation";
    public static final String GETTEMPLATEGROUP = "gettemplategroup";
    public static final String GETTEMPLATE = "gettemplate";
    public static final String GETDATE = "getdate";
    public static final String CREATRPT = "creatrpt";
    public static final String GETMOBILEINFO = "getmobileinfo";
    public static final String SAVEBASEINFO = "savebaseinfo";
    public static final String GETBASEINFO = "getbaseinfo";
    public static final String APPEXIT = "appexit";
    public static final String SETSERVERTIME = "setservertime";
    public static final String UPDATEAPP = "updateapp";
    public static final String UPDATEHTML = "updatehtml";
    public static final String DOWNLOADZILIAO = "downloadziliao";
    public static final String SETPHOTOSHUIYIN = "setphotoshuiyin";
    public static final String SHOWZILIAO = "showziliao";
    public static final String GETDATABASE = "getdatabase";
    public static final String TOSERVICE = "toservice";
    public static final String CHECKGONGGAO = "checkgonggao";
    public static final String CLEARGONGGAO = "cleargonggao";
    public static final String CHECKPHOTOHEAD = "checkphotohead";
    private int m_Battery;

    // public static final String SAVEBASEINFO="savebaseinfo";

    /**
     * Instantiate the interface and set the context
     */
    // 初始化handler
    public JavaScriptinterface(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
    }

    // 这个方法一直在被调用
    // 走Frm_MainMenu里面的Action((String) msg.obj);线程方法
    @JavascriptInterface
    public void Action(String jsString) {
        Log.i("wxx", "JS----------Action");
        try {
            // 将特殊字符进行转码
            jsString = URLDecoder.decode(jsString, "UTF-8");// 设置编码格式ww'ww'w'w'w'w'w'w'w'w'w'w'w'w'w'w'w
            mHandler.sendMessage(mHandler.obtainMessage(Constants.H5MsgType.ACTION, jsString));// 发送线程消息
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // 这个方法一直在被调用
    @JavascriptInterface
    public String ActionList(String jsString) {
        Log.i("wxx", "JS----------ActionList" + jsString);

        // mHandler.sendMessage(mHandler.obtainMessage(
        // Constants.H5MsgType.ACTIONLIST, jsString));
        return ActionLists(jsString);// 这里的返回，返回到哪里去了？？？？？
    }

    private String ActionLists(String jsString) {
        Log.i("wxx", "JavaScriptinterface----------ActionLists-------------" + jsString);
        JSONArray arrResult = new JSONArray();
        try {
            // 解码
            jsString = URLDecoder.decode(jsString, "UTF-8");// 使用指定的编码机制对application/x-www-form-urlencoded字符串解码
            Log.i("wxx", "JavaScriptinterface----------jsString-----------" + jsString);
            // jsString 打印结果为 {"data":[{"type":"getbaseinfo","data":{"key":"user"}}]}
            JsonData jsonData = JsonData.getNewInstance(jsString);// 转化成json
            if (jsonData != null) {
                JSONArray jsonArray = jsonData.getArray("data");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {// 循环
                        Log.i("wxx", "------单个--------" + jsonArray.getJSONObject(i).toString());
                        // jsonArray.getJSONObject(i)获取单个jsonObject
                        Actions(jsonArray.getJSONObject(i), arrResult);// 这里的自动返回接受可以学习下
                    }
                }
            }
        } catch (JSONException e) {
            // String sssString = e.getLocalizedMessage();
        } catch (Exception e) {
            // String sssString = e.getLocalizedMessage();
        }
        // 获取结果
        String result = arrResult.toString();
        Log.i("wxx", "---------------result--------3" + result);
        // [{"type":"getbaseinfo","data":{"data":{"userName":"test1001","pwd":"123456"}},"success":"1"}]
        // [{"type":"getbaseinfo","data":{"data":{"dbverison":"20"}},"success":"1"}]
        // [{"type":"runsql","data":{},"success":"1"}]
        return result;
    }

    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            // LogD("toURLEncoded error:"+paramString);
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
            // LogE("toURLEncoded error:"+paramString, localException);
        }

        return "";
    }

    // 退出程序
    private void Exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    // Actions方法
    private JSONArray Actions(JSONObject input, JSONArray arrResult) {
        try {
            String type = input.getString("type").toLowerCase();
            Log.i("wxx", "JavaScriptinterface-----Actions-----" + type);
            if (type.equalsIgnoreCase(JavaScriptinterface.APPEXIT)) {
                Exit();// App退出
            } else if (type.equalsIgnoreCase(JavaScriptinterface.RUNSQL)) {
                arrResult.put(RunSql(input));// 运行Sql
            } else if (type.equalsIgnoreCase(JavaScriptinterface.GETDATABYSQL)) {
                arrResult.put(GetDataBySql(input));
            } else if (type.equalsIgnoreCase(JavaScriptinterface.CREATRPT)) {
                arrResult.put(CreatRpt(input));
            } else if (type.equalsIgnoreCase(JavaScriptinterface.TODAOHANG)) {
                arrResult.put(toDaoHang(input));
            } else if (type.equalsIgnoreCase(JavaScriptinterface.GETWEEKDATA)) {
                arrResult.put(WeekDay(input));
            } else if (type.equalsIgnoreCase(JavaScriptinterface.TEL)) {
                sendCall(input);
            }
            // else if (type
            // .equalsIgnoreCase(JavaScriptinterface.GETTEMPLATEGROUP)) {
            // arrResult.put(TempGroupList(input));
            // } else if
            // (type.equalsIgnoreCase(JavaScriptinterface.GETTEMPLATE)) {
            // arrResult.put(Template(input));
            // } else

            if (type.equalsIgnoreCase(JavaScriptinterface.GETDATABASE)) {
                arrResult.put(GetDataBase(input));
            }
            if (type.equalsIgnoreCase(JavaScriptinterface.GETDATE)) {
                arrResult.put(CurrDate(input));
            } else if (type.equalsIgnoreCase(JavaScriptinterface.GETMOBILEINFO)) {
                arrResult.put(MobileInfo(input));// 获取设备信息
            } else if (type.equalsIgnoreCase(JavaScriptinterface.SAVEBASEINFO)) {
                arrResult.put(SaveBaseInfo(input));// 保存基础信息
            } else if (type.equalsIgnoreCase(JavaScriptinterface.GETBASEINFO)) {
                arrResult.put(BaseInfo(input));// 获取基础信息
            } else if (type.equalsIgnoreCase(JavaScriptinterface.SETSERVERTIME)) {
                arrResult.put(SetServiceTime(input));
            } else if (type.equalsIgnoreCase(JavaScriptinterface.SETPHOTOSHUIYIN)) {
                arrResult.put(SetPhotoShuiyin(input));
            }
        } catch (JSONException e) {
            arrResult.put(ErrResult(input));
        } catch (Exception e) {
            arrResult.put(ErrResult(input));
        }
        return arrResult;
    }

    // 导航
    // 打开百度地图App
    private JSONObject toDaoHang(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        JSONObject inputData = input.getJSONObject("data");
        int type = inputData.getInt("type");
        if (!Tool.isInstalled("com.baidu.BaiduMap", mContext)) {
            result.put("success", "0");
//            CToast.showMsg(mContext, "未安装百度地图");
        } else {
            toBaidu(inputData);
            result.put("success", "1");
        }
        return result;
    }

    // 拉起百度App
    private void toBaidu(JSONObject input) throws Exception {
        String title = input.getString("title");
        String lat = input.getString("lat");
        String lng = input.getString("long");
        int type = input.getInt("type");
        Intent intent = new Intent();
        String url = "";
        url = "baidumap://map/direction?origin=我的位置&destination=latlng:" + lat
                + "," + lng + "|name:" + title
                + "&mode=walking&coord_type=bd09ll";
        intent.setData(Uri.parse(url));
        mContext.startActivity(intent);
    }

    // 打电话
    public void sendCall(JSONObject input) {
        try {
            JSONObject inputData = input.getJSONObject("data");
            String strNumber = inputData.getString("number");
            if (!StringTool.isEmpty(strNumber)) {
                Uri uri = Uri.parse("tel:" + strNumber);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                mContext.startActivity(intent);
            } else {
                // CToast.showMsg(getContext(), "电话号码不能为空");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // 设置照片水印
    private JSONObject SetPhotoShuiyin(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        JSONObject inputData = input.getJSONObject("data");
        Bitmap bm = PhotoTool.getBitmap(Base64.decode(
                inputData.getString("photo"), Base64.NO_WRAP));
        if (bm != null) {
            // if (inputData.getString("rewrite").equals("1")) {
            // bm = Bitmap.createBitmap(bm, 0, Tool.dip2px(mContext, 65),
            // bm.getWidth(),
            // bm.getHeight() - Tool.dip2px(mContext, 65));
            // }
            bm = PhotoTool.generatorContactCountIcon(bm, inputData, mContext);
            // ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            // // Toast.makeText(getContext(), "照片大小:"+baos.toByteArray(),
            // Toast.LENGTH_LONG);
            // String base64 = Base64.encodeToString(
            // baos.toByteArray(),
            // Base64.NO_WRAP);
            int size = 200;
            try {
                if (inputData != null) {
                    size = inputData.getInt("size");
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            String base64 = Base64.encodeToString(
                    PhotoTool.Bitmap2Bytes(bm, mContext, size), Base64.NO_WRAP);
            // data.put("image", base64);
            JSONObject data1 = new JSONObject();
            data1.put("image", base64);
            data.put("data", data1);
            result.put("success", "1");
            bm.recycle();
            bm = null;
        } else {
            result.put("success", "0");
        }
        return result;
    }

    // 构造报告
    private JSONObject CreatRpt(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        JSONObject inputData = input.getJSONObject("data");
        String rowId = Sqlite.CreatRpt(mContext, inputData);
        if (!rowId.equals("")) {
            data.put("data", rowId);
            result.put("success", "1");
        } else {
            result.put("success", "0");
        }
        return result;
    }

    //
    private JSONObject WeekDay(JSONObject input) throws Exception {

        String date = DateTool.CurrDate();
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        JSONObject inputData = input.getJSONObject("data");
        String type = inputData.getString("type");
        String curDate = inputData.getString("date");
        if (!StringTool.isEmpty(type) && !StringTool.isEmpty(curDate)) {
            if (type.equals("lastweek")) {
                date = Sqlite.addDate(mContext, curDate, "-7");
            } else if (type.equals("nextweek")) {
                date = Sqlite.addDate(mContext, curDate, "+7");
            }
        } else if (StringTool.isEmpty(type) && !StringTool.isEmpty(curDate)) {
            date = curDate;
        }
        result.put("success", "1");
        data.put("date", date);
        data.put("data", WeekDays(date));
        return result;
    }

    // private JSONObject WeekDay(JSONObject input) throws Exception {
    //
    // String date = DateTool.CurrDate();
    // JSONObject data = new JSONObject();
    // JSONObject result = ResultObject(input, data);
    // JSONObject inputData = input.getJSONObject("data");
    // String type = "";
    // String curDate = "";
    // try {
    // type = inputData.getString("type");
    // curDate = inputData.getString("date");
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    //
    // if (!StringTool.isEmpty(type) && !StringTool.isEmpty(curDate)) {
    // if (type.equals("lastweek")) {
    // date = Sqlite.addDate(mContext, curDate, "-7");
    // } else if (type.equals("nextweek")) {
    // date = Sqlite.addDate(mContext, curDate, "+7");
    // }
    // } else if (StringTool.isEmpty(type) && !StringTool.isEmpty(curDate)) {
    // date = curDate;
    // }
    // result.put("success", "1");
    // data.put("date", date);
    // JSONObject data1 = new JSONObject();
    // data1.put("date", date);
    // data1.put("days", WeekDays(date));
    // data.put("data", data1);
    // return result;
    // }

    public static JSONArray WeekDays(String date) throws Exception {

        JSONArray array = new JSONArray();
        JsonData jsonData;

        Calendar cal = Calendar.getInstance();
        cal.setTime(string2Date1(date));
        if (cal.get(Calendar.DAY_OF_WEEK) == 1)
            cal.add(Calendar.WEEK_OF_YEAR, -1);
        for (int i = 2; i <= Calendar.SATURDAY; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            jsonData = JsonData.getNewInstance();

            jsonData.put("day", cal.get(Calendar.DATE) + "");
            // jsonData.put("week", getWeekDay(i));
            if (cal.get(Calendar.DATE) == Integer.parseInt(date
                    .substring(8, 10)))
                jsonData.put("select", "1");
            else
                jsonData.put("select", "0");
            jsonData.put("date",
                    DateTool.FormatDate(cal.getTime(), DateTool.DATE));
            array.put(jsonData.getJsonData());
        }
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        jsonData = JsonData.getNewInstance();
        jsonData.put("day", cal.get(Calendar.DATE) + "");
        // jsonData.put("week", getWeekDay(Calendar.SUNDAY));
        if (cal.get(Calendar.DATE) == Integer.parseInt(date.substring(8, 10)))
            jsonData.put("select", "1");
        else
            jsonData.put("select", "0");
        jsonData.put("date", DateTool.FormatDate(cal.getTime(), DateTool.DATE));
        array.put(jsonData.getJsonData());

        return array;
    }

    public static Date string2Date1(String date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTool.DATE);
        dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TimeZone.ZH_CH));
        return dateFormat.parse(date);

    }

    private JSONObject GetDataBySql(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        JSONObject inputData = input.getJSONObject("data");
        JSONArray array = Sqlite.getDataListBySql(mContext,
                inputData.getString("sql"));
        data.put("data", array);
        result.put("success", "1");
        return result;
    }

    private JSONObject GetDataBase(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        // JSONObject inputData = input.getJSONObject("data");
        String db = getDBString();
        JSONObject dict = new JSONObject();
        dict.put("filebase64", db);

        data.put("data", dict);
        result.put("success", "1");
        return result;
    }

    public String getDBString() throws Exception {
        ZIP.ZipFolder(FileTool.DB_NAME, FileTool.APP_PATH + "db.zip");
        File file = new File(FileTool.APP_PATH + "db.zip");
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.NO_WRAP);
        // return new BASE64Encoder().encode(buffer);
    }

    private JSONObject SetServiceTime(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        JSONObject inputData = input.getJSONObject("data");
        DateTool.setSystemUpTime(inputData.getString("date"));
        result.put("success", "1");
        return result;
    }

    // 获取基础信息
    // {"type":"getbaseinfo","data":{"key":"user"}}
    // input格式 {"data":[{"type":"getbaseinfo","data":{"key":"user"}}]}
    private JSONObject BaseInfo(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        JSONObject inputData = input.getJSONObject("data");// 这里才是真正的去查询数据
        String baseInfo = Rms.getString(mContext, inputData.getString("key"));
        if (baseInfo != null && !baseInfo.equals("")) {
            JSONObject base = new JSONObject(baseInfo);
            data.put("data", base);
        }
        result.put("success", "1");
        return result;
    }

    // {"type":"savebaseinfo","data":{"key":"user","data":{"userName":"test1001","pwd":"123456"}}}
    private JSONObject SaveBaseInfo(JSONObject input) throws Exception {
        Log.i("wxx", "--------SaveBaseInfo-----------" + input.toString());
        JSONObject data = new JSONObject();
        ResultObject(input, data); // JSONObject result =
        JSONObject inputData = input.getJSONObject("data");
        String key = inputData.getString("key");
        String dataString = inputData.getJSONObject("data").toString();
        Rms.putString(mContext, key, dataString);
        data.put("success", "1");
        return data;
    }

    // private JSONObject SaveBaseInfo(JSONObject input) throws Exception {
    // JSONObject data = new JSONObject();
    // JSONObject result = ResultObject(input, data);
    // JSONObject inputData = input.getJSONObject("data");
    // Rms.putString(mContext, Rms.DATA, inputData.toString());
    // result.put("success", "1");
    // return result;
    // }

    private JSONObject MobileInfo(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        JSONObject content = new JSONObject();
        data.put("data", content);
        content.put("globallyuniquestring", Tool.UUID());
        content.put("identifier", PhoneTool.getImei(mContext));
        content.put("isphone", "1");
        content.put("isconnect", PhoneTool.IsConnect(mContext));
        content.put("systemversion", PhoneTool.Version());
        content.put("model", PhoneTool.Model());
        content.put("brand", PhoneTool.Brand());// 获取手机品牌  PhoneTool.Brand()
        content.put("batterystate", Battery());
        content.put("devicetype", "android");
        content.put("appversion", FrmActivity.APPVERSION);
        content.put("time", DateTool.CurrDateTime());
        content.put("runmemory", CrashHandler.getAvailMemory(mContext));// 手机内存
        content.put("appid", new Tool().getPhoneSign(mContext));// 唯一标识码是appid
//        String rid = JPushInterface.getRegistrationID(mContext);
//        content.put("registrationid", rid);
        result.put("success", "1");
        Log.i("wxx", "++++++++++++++++++++" + result.toString());
        return result;
    }

    // 动态注册广播监听
    public int Battery() {
        BatteryReceiver receiver = new BatteryReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(receiver, filter);
        return m_Battery;
    }

    // 接收广播
    private class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int current = intent.getExtras().getInt("level");// 获得当前电量
            int total = intent.getExtras().getInt("scale");// 获得总电量
            m_Battery = current * 100 / total;
        }
    }

    // 这是执行sql语音的地方
    // input参考值格式：{"type":"runsql","data":["CREATE TABLE IF NOT EXISTS t_product( key_i ......)]}
    private JSONObject RunSql(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        Log.i("wxx", "-----result----1-----" + result.toString());// {"type":"runsql","data":{}}
        // JSONObject inputData = input.getJSONObject("data");
        JSONArray jsonArray = input.getJSONArray("data");
        Sqlite.execSqlList(mContext, jsonArray);// 执行sql的地方
        result.put("success", "1");
        Log.i("wxx", "-----result----2-----" + result.toString());
        return result;// {"type":"runsql","data":{},"success":"1"}
    }

    private JSONObject ErrResult(JSONObject input) {
        try {
            JSONObject data = new JSONObject();
            JSONObject result = ResultObject(input, data);
            result.put("success", "0");
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    private JSONObject CurrDate(JSONObject input) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject result = ResultObject(input, data);
        data.put("date", DateTool.CurrDateTime());
        result.put("success", "1");

        return result;
    }

    // 这里对数据进行拆解 sql例子：{"type":"runsql","data":["CREATE TABLE IF NOT EXISTS t_product( key_i ......)]}
    private JSONObject ResultObject(JSONObject input, JSONObject data)
            throws Exception {
        JSONObject result = new JSONObject();
        result.put("type", input.getString("type"));
        // result.put("code", input.get("code"));
        result.put("data", data);// {}
        return result;
    }

    //
    // public void CreatDB(String dbconfig) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.CREATDB, dbconfig));
    // }
    //
    // public void GetDataBySql(String sql) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.GETDATABYSQL, sql));
    // }
    //
    // public void GetWeekData(String sql) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.GetWeekData, sql));
    //
    // }
    //
    // public void RunSql(String sql) {
    // mHandler.sendMessage(mHandler.obtainMessage(Constants.H5MsgType.RUNSQL,
    // sql));GetHomeDecorationStoreListByPlan
    // }
    //
    // public void TakeLocation(String local) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.TakeLocation, local));
    // }
    //
    // public void TakePhoto(String phtoto) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.TAKEPHOTO, phtoto));
    // }
    //
    // public void SaveData(String data) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.SAVEDATA, data));
    // }
    //
    // public void GetData(String key) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.GETDATA, key));
    // }
    //
    // public void turnPage(String page) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.TURNPAGE, page));
    // }
    //
    public void openUrl(String page) {
        mHandler.sendMessage(mHandler.obtainMessage(
                Constants.H5MsgType.OPENURL, page));
    }
    //
    // public void wechatPay(String orderid) {
    // mHandler.sendMessage(mHa'ndler.obtainMessage(
    // Constants.H5MsgType.WECHATPAY, orderid));
    // }
    //
    // public void aliPay(String order) {
    // mHandler.sendMessage(mHandler.obtainMessage(Constants.H5MsgType.ALIPAY,
    // order));
    // }
    //
    // public void tel(String num) {
    // mHandler.sendMessage(mHandler.obtainMessage(Constants.H5MsgType.TEL,
    // num));
    // }
    //
    // public void loginWechat() {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.LOGINWECHAT, ""));
    // }
    //
    // public void shareWechat(String shareData) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.SHAREWECHAT, shareData));
    // }
    //
    // public void UpdatePackage(String version) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.UPDATEPACKAGE, version));
    // }
    //
    // public void AppExit() {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.APPEXIT, ""));
    // }
    //
    // public void StatusBar(String data) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.STATUSBAR, data));
    // }
    //
    // public void UpdateHtml(String data) {
    // mHandler.sendMessage(mHandler.obtainMessage(
    // Constants.H5MsgType.UPDATEHTML, data));
    // }

}
