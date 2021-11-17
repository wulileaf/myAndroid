package com.example.h5.hybridTool;

import android.annotation.SuppressLint;

public class StringTool {

    public static boolean isEmpty(String content) {
        if (content == null || content.trim().equals("")) {
            return true;
        }
        return false;
    }

    @SuppressLint("DefaultLocale")
    public static String getLowerCase(String content) {
        if (!isEmpty(content)) {
            return content.toLowerCase();
        }
        return "";
    }
}
