package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.tools.DiffuseView;

import butterknife.BindView;
import butterknife.OnClick;

// 自定义View
public class CustomView extends BaseActivity {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_back)
    ImageView titleBack;
    private Button button;
    private Button button2;
    private DiffuseView diffuseView;

    @Override
    protected int initView() {
        return R.layout.atc_custom_view;
    }

    @Override
    protected void initData() {
        initTitle();
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        diffuseView = findViewById(R.id.diffuseView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diffuseView.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diffuseView.stop();
            }
        });
    }


    private void initTitle() {
        titleContent.setText("自定义view");
        titleRight.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.title_back, R.id.title_right})
    public void focusClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                showToast("点击了提交");
                break;
            default:
                break;
        }
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }
}
