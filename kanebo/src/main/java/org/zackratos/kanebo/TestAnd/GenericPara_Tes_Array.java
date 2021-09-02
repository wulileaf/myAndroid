package org.zackratos.kanebo.TestAnd;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class GenericPara_Tes_Array {


    // 返回List<T>
    // Class<T> obj这个东西就相当于GenericPara_Tes_Array.class
    public static <T> List<T> parseArray(String a, Class<T> obj) {


        List<T> list = new ArrayList<>();
        return list;
    }
}
