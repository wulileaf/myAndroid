package com.example.h5.hybridTool;

public class Constants {
    public static final String APP_ID = "wx066c051d879c9f82";
    public static final String MCH_ID = "1261400001";
    public static final String API_KEY = "E21260F9832C40A0867B1E0AA5E2FF96";

    public static final String APPPACKAGE = "one-sfa.apk";


    public interface WX {
        String ZH_CH = "GMT+08:00";
        String STA = "GMT+00:00";
    }


    public interface H5MsgType {
        final static int UPDATEHTML = -96;
        final static int STATUSBAR = -97;

        final static int APPEXIT = -98;
        final static int DOWNLOADIMG = -99;
        final static int TURNPAGE = -100;
        final static int OPENURL = -101;
        final static int WECHATPAY = -102;
        final static int ALIPAY = -103;
        final static int TEL = -104;
        final static int LOGINWECHAT = -105;
        final static int UPDATEPACKAGE = -106;
        final static int SHAREWECHAT = -107;

        final static int SAVEDATA = -108;
        final static int CREATDB = -109;
        final static int RUNSQL = -110;
        final static int TAKEPHOTO = -111;
        final static int GETDATA = -112;

        final static int GETDATABYSQL = -113;
        final static int TakeLocation = -114;
        final static int GetWeekData = -115;

        final static int ACTION = -50;
        final static int ACTIONLIST = -51;


    }

    public interface TimeZone {
        String ZH_CH = "GMT+08:00";
        String STA = "GMT+00:00";
    }
}
