package com.example.h5.hybridTool;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class DB {

    // private static final String TAG = "parrow";

    // final static String DATABASE_NAME = "/sdcard/sfa//sfa.db";

    // private static final String SERVERID = "ServerId";
    // public static final String SECRET_KEY="123456";

    private SQLiteDatabase db;
//	private ExDataBaseHelper dbHelper;

    private static final int TRANCOUNT = 50;
    private Context context;

    public DB(Context context) {
        try {
            init(context);
        } finally {
            if (db != null)
                db.close();
        }
    }

    private void init(Context context) {
        initContext(context);

        initDB();

        // setUpDb();
    }

    @SuppressWarnings("deprecation")
    private void initDB() {
        // SQLiteDatabase.loadLibs(context);
        // dbHelper = new ExDataBaseHelper(context);
        // db = dbHelper.getWritableDatabase(ExDataBaseHelper.SECRET_KEY);

        db = openOrCreateDatabase();
        db.setLocale(Locale.CHINESE);//设置本地化
        // S sqlite3_key(db,"123456",6);
        // db.setLockingEnabled(true);SSSSS
        // closeDatabase();
    }

    public synchronized String addDate(String date, String days) {
        Cursor c = null;
        try {
            openDatabase();
            c = db.rawQuery("SELECT date('" + date + "','" + days
                    + " days') as date", null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    date = getString(c, "date");
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
            closeDatabase();
        }
        return date;
    }

    private SQLiteDatabase openOrCreateDatabase() {
        return context.openOrCreateDatabase(FileTool.DB_NAME,
                Context.MODE_PRIVATE, null);
    }

    private synchronized void openDatabase() {
        if (!isOpen()) {
            // db=SQLiteDatabase.openOrCreateDatabase(FileTool.DB_NAME,
            // SECRET_KEY, null);

            // db = dbHelper.getWritableDatabase(ExDataBaseHelper.SECRET_KEY);
            // db.setLockingEnabled(true);
            db = openOrCreateDatabase();
        }
    }

    private synchronized void closeDatabase() {
        if (isOpen()) {
            db.close();
        }
    }

    private synchronized void beginTransaction() {
        try {
            if (inTransaction())
                endTransaction();
            db.beginTransaction();
        } catch (IllegalStateException e) {
        }
    }

    private boolean isOpen() {
        if (db != null)
            return db.isOpen();
        else {
            return false;
        }
    }

    private boolean inTransaction() {
        return db.inTransaction();
    }

    private synchronized void setTransactionSuccessful() {
        if (inTransaction())
            db.setTransactionSuccessful();
    }

    private synchronized void endTransaction() {
        if (inTransaction())
            db.endTransaction();
    }

    public synchronized String CreatRpt(JSONObject jsonObject) throws Exception {
        Cursor c = null;
        String rowId = "";
        try {
            execSqlList(jsonObject.getJSONArray("delSql"));
            openDatabase();
            beginTransaction();
            db.execSQL(jsonObject.getString("mainSql"));
            c = db.rawQuery("SELECT  last_insert_rowid() as rowid", null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    rowId = getString(c, "rowid");
                } while (c.moveToNext());
            }
            c.close();
            JSONArray detailList = jsonObject.getJSONArray("detailSql");
            for (int i = 0; i < detailList.length(); i++) {
                String detail = detailList.getString(i);
                detail = detail.replace("$@$", rowId);
                db.execSQL(detail);
            }
            setTransactionSuccessful();
        } finally {
            if (c != null)
                c.close();
            endTransaction();
            closeDatabase();
        }
        return rowId;
    }

    public synchronized void execSqlList(JSONArray sqlList) throws Exception {
        try {
            openDatabase();// 打开数据库
            if (sqlList.length() > TRANCOUNT)
                beginTransaction();
            String sql;
            for (int i = 0; i < sqlList.length(); i++) {
                sql = sqlList.getString(i);
                execSql(sql);
            }
            if (sqlList.length() > TRANCOUNT)
                setTransactionSuccessful();
        } finally {
            if (sqlList.length() > TRANCOUNT)
                endTransaction();
            closeDatabase();
        }
    }

    public synchronized JSONArray getDataListBySql(String sql) {
        JSONArray list = new JSONArray();
        if (sql == null || sql.equals(""))
            return list;
        Cursor c = null;
        try {
            openDatabase();
            c = db.rawQuery(sql, null);
            if (c != null && c.getCount() > 0) {
                JsonData jsonData;
                c.moveToFirst();
                do {
                    jsonData = JsonData.getNewInstance();
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        String value = c.getString(i);
                        // HashMap map = new HashMap();
                        // map.put(c.getColumnName(i).toLowerCase(), value);
                        // JSONObject.quote(data)g

                        jsonData.put(c.getColumnName(i).toLowerCase(), value);

                    }
                    list.put(jsonData.getJsonData());
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
            closeDatabase();
        }
        return list;
    }

    static String string2Json(String s) {
        if (s == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public synchronized JSONObject Template(JSONObject jsonData) {
        JSONObject template = new JSONObject();
        if (jsonData == null)
            return template;
        Cursor c = null;
        try {
            String sql = jsonData.getString("sql");
            if (sql == null || sql.equals(""))
                return template;

            openDatabase();
            c = db.rawQuery(sql, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        template.put(c.getColumnName(i).toLowerCase(),
                                c.getString(i));
                    }
                } while (c.moveToNext());
            }
            c.close();

            TempItemGroupList(jsonData, template);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
            closeDatabase();
        }

        return template;
    }

    public synchronized void TempItemGroupList(JSONObject jsonData,
                                               JSONObject template) {
        JSONArray templateItemGroupList = new JSONArray();
        String sql4 = "";
        if (jsonData == null)
            return;
        Cursor c = null;
        try {
            String sql = jsonData.getString("sql2");
            if (sql == null || sql.equals(""))
                return;
            JSONArray itemGroupList = null;
            JSONObject tempItemGroup;
            String parentid = "", oldparentid = "";
            openDatabase();
            c = db.rawQuery(sql, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    String type = getString(c, "type");
                    parentid = getString(c, "parentid");
                    if (type.equals("0")) {
                        tempItemGroup = new JSONObject();
                        for (int i = 0; i < c.getColumnCount(); i++) {
                            tempItemGroup.put(c.getColumnName(i).toLowerCase(),
                                    c.getString(i));
                        }
                        templateItemGroupList.put(tempItemGroup);
                    } else {
                        if (!oldparentid.equals(parentid)) {
                            if (!oldparentid.equals("")) {
                                for (int i = 0; i < templateItemGroupList
                                        .length(); i++) {
                                    JSONObject object = templateItemGroupList
                                            .getJSONObject(i);
                                    if (object.getString("templateitemgroupid")
                                            .equals(oldparentid)) {
                                        object.put("templateitemgroup",
                                                itemGroupList);
                                        break;
                                    }
                                }
                            }
                            itemGroupList = new JSONArray();
                        }
                        tempItemGroup = new JSONObject();
                        for (int i = 0; i < c.getColumnCount(); i++) {
                            tempItemGroup.put(c.getColumnName(i).toLowerCase(),
                                    c.getString(i));
                        }
                        itemGroupList.put(tempItemGroup);
                        oldparentid = parentid;
                    }

                } while (c.moveToNext());
                template.put("templateitemgrouplist", templateItemGroupList);
                if (!oldparentid.equals("") && itemGroupList.length() > 0) {
                    for (int i = 0; i < templateItemGroupList.length(); i++) {
                        JSONObject object = templateItemGroupList
                                .getJSONObject(i);
                        if (object.getString("templateitemgroupid").equals(
                                oldparentid)) {
                            object.put("templateitemgroup", itemGroupList);
                            break;
                        }
                    }
                }
            }
            c.close();
            Item(jsonData.getString("sql3"), templateItemGroupList);
            sql4 = jsonData.getString("sql4");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
            closeDatabase();
        }

        DataList(sql4, templateItemGroupList);

    }

    public synchronized void DataList(String sql,
                                      JSONArray templateItemGroupList) {
        try {
            JSONArray dataList = getDataListBySql(sql);

            for (int i = 0; i < templateItemGroupList.length(); i++) {
                JSONObject object = templateItemGroupList.getJSONObject(i);
                for (int k = 0; k < dataList.length(); k++) {
                    JSONObject object1 = dataList.getJSONObject(k);
                    if (object1.getString("templateitemgroupid").equals(
                            object.getString("templateitemgroupid"))) {
                        object.put("datalist", object1);
                        break;
                    }

                }
            }
        } catch (JSONException e) {
            // TODO: handle exception
        }

    }

    public synchronized void Item(String sql, JSONArray templateItemGroupList) {
        if (sql == null || sql.equals(""))
            return;
        JSONArray tempItemList = null;
        JSONObject templateItem;
        String temitemgroupid = "", oldtemitemgroupid = "";
        Cursor c = null;
        try {
            c = db.rawQuery(sql, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    temitemgroupid = getString(c, "templateitemgroupid");

                    if (!temitemgroupid.equals(oldtemitemgroupid)) {
                        if (!oldtemitemgroupid.equals("")) {
                            for (int i = 0; i < templateItemGroupList.length(); i++) {
                                JSONObject object = templateItemGroupList
                                        .getJSONObject(i);
                                JSONArray array = object
                                        .getJSONArray("templateitemgroup");
                                for (int k = 0; k < array.length(); k++) {
                                    JSONObject object1 = array.getJSONObject(k);
                                    if (object1
                                            .getString("templateitemgroupid")
                                            .equals(oldtemitemgroupid)) {
                                        object1.put("templateitem",
                                                tempItemList);
                                        break;
                                    }

                                }
                            }
                        }
                        tempItemList = new JSONArray();
                    }
                    templateItem = new JSONObject();
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        templateItem.put(c.getColumnName(i).toLowerCase(),
                                c.getString(i));
                    }
                    tempItemList.put(templateItem);
                    oldtemitemgroupid = temitemgroupid;

                } while (c.moveToNext());

                if (!oldtemitemgroupid.equals("") && tempItemList.length() > 0) {
                    for (int i = 0; i < templateItemGroupList.length(); i++) {
                        JSONObject object = templateItemGroupList
                                .getJSONObject(i);
                        JSONArray array = object
                                .getJSONArray("templateitemgroup");
                        for (int k = 0; k < array.length(); k++) {
                            JSONObject object1 = array.getJSONObject(k);
                            if (object1.getString("templateitemgroupid")
                                    .equals(oldtemitemgroupid)) {
                                object1.put("templateitem", tempItemList);
                                break;
                            }

                        }
                    }
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
    }

    public synchronized JSONArray TempGroupList(JSONObject jsonData) {
        JSONArray tempGroupList = new JSONArray();
        if (jsonData == null)
            return tempGroupList;
        Cursor c = null;
        try {
            String sql = jsonData.getString("sql");
            if (sql == null || sql.equals(""))
                return tempGroupList;
            JSONArray tempList = null;
            JSONObject temGroup, template;
            String parentid = "", oldparentid = "";
            openDatabase();
            c = db.rawQuery(sql, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    String level = getString(c, "level");
                    parentid = getString(c, "parentid");
                    if (level.equals("1")) {
                        temGroup = new JSONObject();
                        for (int i = 0; i < c.getColumnCount(); i++) {
                            temGroup.put(c.getColumnName(i).toLowerCase(),
                                    c.getString(i));
                        }
                        tempGroupList.put(temGroup);
                    } else if (level.equals("2")) {
                        if (!oldparentid.equals(parentid)) {
                            if (!oldparentid.equals("")) {
                                for (int i = 0; i < tempGroupList.length(); i++) {
                                    JSONObject object = tempGroupList
                                            .getJSONObject(i);
                                    if (object.getString("templategroupid")
                                            .equals(oldparentid)) {
                                        object.put("templategroup", tempList);
                                        break;
                                    }
                                }
                            }
                            tempList = new JSONArray();
                        }
                        template = new JSONObject();
                        for (int i = 0; i < c.getColumnCount(); i++) {
                            template.put(c.getColumnName(i).toLowerCase(),
                                    c.getString(i));
                        }
                        tempList.put(template);
                        oldparentid = parentid;
                    }

                } while (c.moveToNext());

                if (!oldparentid.equals("") && tempList.length() > 0) {
                    for (int i = 0; i < tempGroupList.length(); i++) {
                        JSONObject object = tempGroupList.getJSONObject(i);
                        if (object.getString("templategroupid").equals(
                                oldparentid)) {
                            object.put("templategroup", tempList);
                            break;
                        }
                    }
                }
            }
            c.close();
            Template(jsonData.getString("sql2"), tempGroupList);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
            closeDatabase();
        }

        return tempGroupList;
    }

    public synchronized void Template(String sql, JSONArray tempGroupList) {
        if (sql == null || sql.equals(""))
            return;
        JSONArray tempList = null;
        JSONObject template;
        String temgroupid = "", oldtemgroupid = "";
        Cursor c = null;
        try {
            c = db.rawQuery(sql, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    temgroupid = getString(c, "templategroupid");

                    if (!temgroupid.equals(oldtemgroupid)) {
                        if (!oldtemgroupid.equals("")) {
                            for (int i = 0; i < tempGroupList.length(); i++) {
                                JSONObject object = tempGroupList
                                        .getJSONObject(i);
                                JSONArray array = object
                                        .getJSONArray("templategroup");
                                for (int k = 0; k < array.length(); k++) {
                                    JSONObject object1 = array.getJSONObject(k);
                                    if (object1.getString("templategroupid")
                                            .equals(oldtemgroupid)) {
                                        object1.put("template", tempList);
                                        break;
                                    }

                                }
                            }
                        }
                        tempList = new JSONArray();
                    }
                    template = new JSONObject();
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        template.put(c.getColumnName(i).toLowerCase(),
                                c.getString(i));
                    }
                    tempList.put(template);
                    oldtemgroupid = temgroupid;

                } while (c.moveToNext());

                if (!oldtemgroupid.equals("") && tempList.length() > 0) {
                    for (int i = 0; i < tempGroupList.length(); i++) {
                        JSONObject object = tempGroupList.getJSONObject(i);
                        JSONArray array = object.getJSONArray("templategroup");
                        for (int k = 0; k < array.length(); k++) {
                            JSONObject object1 = array.getJSONObject(k);
                            if (object1.getString("templategroupid").equals(
                                    oldtemgroupid)) {
                                object1.put("template", tempList);
                                break;
                            }
                        }
                    }
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
    }

    public synchronized String getDate(String str) {
        String date = DateTool.CurrDate();
        Cursor c = null;
        try {
            openDatabase();
            c = db.rawQuery(str, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    date = getString(c, "date");
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
            closeDatabase();
        }
        return date;
    }

    // 删除，更新，添加数据的操作方法
    private synchronized void execSql(String sql) {
        db.execSQL(sql);
    }

    public synchronized void execSingleSql(String sql) {
        try {
            openDatabase();
            execSql(sql);
        } finally {
            closeDatabase();
        }
    }

    private String getString(Cursor c, String columnName) {
        int index = -1;
        try {
            index = c.getColumnIndex((columnName.toLowerCase(Locale
                    .getDefault())));
        } catch (Exception ex) {
            index = -1;
        }
        if (index == -1)
            return "";
        else
            return getString(c, index);
    }

    private String getString(Cursor c, int index) {
        return c.getString(index) == null ? "" : c.getString(index);
    }

    public Context getContext() {
        return context;
    }

    private void initContext(Context context) {
        this.context = context;
    }
}
