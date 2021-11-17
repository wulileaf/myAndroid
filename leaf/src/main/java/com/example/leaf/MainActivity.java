package com.example.leaf;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends BaseActivity implements ViewInterface {
    TextView textView;

    @Override
    protected void createPresenter() {
        basePresenter = new BasePresenter(this);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basePresenter.getLogin();
            }
        });
        super.netWorkChange();
    }

    // 重写
    public void netWorkChange() {

    }

    // 测试回调成功
    @Override
    public void onSuccess(String code, String userName, List<TypeList> typeList) {
        List<Msg> msgList = typeList.get(0).getList();
        for (int a = 1; a < msgList.size(); a++) {
            Log.i("Login", msgList.get(a).msgInfo + "--" + msgList.get(a).msgType);
        }
    }

    @Override
    public void onFaliure(String failMsg) {
        Log.i("Login", failMsg);
    }

    @Override
    public void onError(String errMsg) {
        Log.i("Login", errMsg);
    }
}