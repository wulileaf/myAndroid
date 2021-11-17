package org.zackratos.kanebo.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonData {

    private JSONObject m_JSONObject;

    public static final int STATUS_OK = 1;
    public static final int STATUS_UPDATE = -2;

    public static final int STATUS_DONE = 1;

    public static JsonData mInstance = null;

    public static JsonData getInstance(String josnString) {
        if (mInstance == null) {
            mInstance = new JsonData(josnString);
        }
        return mInstance;
    }

    public static JsonData getInstance() {
        if (mInstance == null) {
            mInstance = new JsonData();
        }
        return mInstance;
    }

    // 将字符串转化为了一个JsonData对象，返回这个对象
    public static JsonData getNewInstance(String josnString) {
        mInstance = new JsonData(josnString);
        return mInstance;
    }

    public static JsonData getNewInstance() {

        mInstance = new JsonData();
        return mInstance;
    }

    public JsonData() {
        m_JSONObject = new JSONObject();
    }

    public JsonData(String josnString) {
        try {
            m_JSONObject = new JSONObject(josnString);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String jsonString() {
        try {
            return m_JSONObject.toString();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public void setJsonObject(JSONObject jsonObject) {
        m_JSONObject = jsonObject;
    }

    public String getString(String key) {
        try {
            return m_JSONObject.getString(key);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public Object get(String key) {
        try {
            return m_JSONObject.get(key);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public JSONObject getJsonData() {
        return m_JSONObject;
    }

    public void put(String key, Object value) {
        try {
            m_JSONObject.put(key, value);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public JSONArray getArray(String key) {
        try {
            return m_JSONObject.getJSONArray(key);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public JSONObject getJsonObject(String key) {
        try {
            return m_JSONObject.getJSONObject(key);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public String getObjectString(String dataName, String key) {
        try {
            return m_JSONObject.getJSONObject(dataName).getString(key);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

}
