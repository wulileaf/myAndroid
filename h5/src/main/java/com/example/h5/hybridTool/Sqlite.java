package com.example.h5.hybridTool;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

public class Sqlite {
    public static DB mDB;

    private static DB getDB(Context context) {
        if (mDB == null)
            mDB = new DB(context);
        return mDB;
    }

    public synchronized static void execSqlList(Context context, JSONArray SqlList) throws Exception {
        getDB(context).execSqlList(SqlList);
    }

    // public synchronized static void setUpDb(Context context, JSONArray
    // jsonArray) {
    // try {
    // getDB(context).setUpDb(jsonArray);
    // } catch (Exception ex) {
    // }
    // }

    public synchronized static JSONArray getDataListBySql(Context context,String sql) {
        try {
            return getDB(context).getDataListBySql(sql);
        } catch (Exception ex) {
        }
        return null;
    }

    public static String CreatRpt(Context context, JSONObject sql) {
        String rowId = "";
        try {
            rowId = getDB(context).CreatRpt(sql);
        } catch (Exception ex) {
        }
        return rowId;
    }

    public static JSONObject Template(Context context, JSONObject jsonData) {
        JSONObject template = new JSONObject();
        try {
            template = getDB(context).Template(jsonData);
        } catch (Exception ex) {
        }
        return template;
    }

    public static JSONArray TempGroupList(Context context, JSONObject jsonData) {
        JSONArray tempGroupList = new JSONArray();
        try {
            tempGroupList = getDB(context).TempGroupList(jsonData);
        } catch (Exception ex) {
        }
        return tempGroupList;
    }

    public static void execSingleSql(Context context, String sql) {
        try {
            getDB(context).execSingleSql(sql);
        } catch (Exception ex) {
        }
    }

    public synchronized static String getDate(Context context, String where) {
        String data;
        try {
            data = getDB(context).getDate(where);
        } catch (Exception ex) {
            return DateTool.CurrDate();
        }
        return data;
    }

    public synchronized static String addDate(Context context, String date,String days) {
        String data;
        try {
            data = getDB(context).addDate(date, days);
        } catch (Exception ex) {
            return DateTool.CurrDate();
        }
        return data;
    }
}
