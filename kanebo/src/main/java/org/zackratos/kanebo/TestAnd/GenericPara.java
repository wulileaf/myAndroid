package org.zackratos.kanebo.TestAnd;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.zackratos.kanebo.R;

// 泛型
public class GenericPara extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tesand_generic_para);
        init();
        TesAnd();
        TesAndOne();
    }


    private void TesAndOne() {
        GenericPara_Tes_One<String> tes_one = new GenericPara_Tes_One<>();
        tes_one.setA("你好呀");
        Log.i("Show", tes_one.getA());
    }

    // 测试方法
    // 这里的泛型可以除去
    private void TesAnd() {
        GenericPara_Tes.<String>GP("你好");
        GenericPara_Tes.GP("你好");
        GenericPara_Tes genericPara_tes = new GenericPara_Tes();
        genericPara_tes.GPO("你好");
        genericPara_tes.<String>GPO("你好");
    }

    // 初始化
    private void init() {

    }


}
