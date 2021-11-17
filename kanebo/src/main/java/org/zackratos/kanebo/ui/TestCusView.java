package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.tools.CustomDialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class TestCusView extends AppCompatActivity {

    TextView titleContent;
    TextView titleRight;
    ImageView titleBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atc_test_cus_view);
        titleContent = findViewById(R.id.title_content);
        titleRight = findViewById(R.id.title_right);
        titleBack = findViewById(R.id.title_back);
        initTitle();

        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 点击显示
        titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog dialog = new CustomDialog(TestCusView.this);
                dialog.setMessage("这是一个自定义Dialog")
                        .setImageResId(R.mipmap.ic_launcher)
                        .setTitle("提示")
                        .setSingle(false)
                        .setOnClickBottomListener(new CustomDialog.OnClickBottomListener() {
                            @Override
                            public void onPositiveClick() {
                                dialog.dismiss();
                                Log.i("DIALOG", "onPositiveClick--------点击了OK");
                            }

                            @Override
                            public void onNegtiveClick() {
                                dialog.dismiss();
                                Log.i("DIALOG", "onNegtiveClick-------点击了NO");
                            }
                        }).show();
            }
        });
    }

    public void initTitle() {
        titleContent.setText("测试自定View");
        titleRight.setVisibility(View.VISIBLE);
    }
}
