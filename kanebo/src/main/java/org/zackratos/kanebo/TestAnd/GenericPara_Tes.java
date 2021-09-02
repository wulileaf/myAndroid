package org.zackratos.kanebo.TestAnd;

import android.util.Log;

// 泛型测试工具类
public class GenericPara_Tes {

    // 静态方法
    public static <T> void GP(T a) {
        Log.i("Show", a.toString());
    }

    // 常规方法
    public <T> void GPO(T a) {
        Log.i("Show", a.toString());
    }


}
