package com.example.leaf;

import java.util.List;

interface ViewInterface {
    void onSuccess(String code, String userName, List<TypeList> typeList);
    void onFaliure(String failMsg);
    void onError(String errMsg);
}
