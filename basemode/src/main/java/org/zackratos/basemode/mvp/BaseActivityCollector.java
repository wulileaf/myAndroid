package org.zackratos.basemode.mvp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date
 * @author leaf
 * @version 1.0
 * @Note Activity管理器,用于退出本次账号登录时使用
 */
public class BaseActivityCollector {

    static List<Activity> activities = new ArrayList<>();
    private static BaseActivityCollector instance;

    synchronized static BaseActivityCollector getInstance() {
        if (null == instance) {
            instance = new BaseActivityCollector();
        }
        return instance;
    }

    // 添加Activity
    static void addActivity(Activity activity) {
        activities.add(activity);
    }

    // 移除Activity
    static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    // 销毁Activity
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

}
