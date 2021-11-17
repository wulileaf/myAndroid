package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import butterknife.BindView;
import butterknife.OnClick;

public class BaseControl extends BaseActivity {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_back)
    ImageView titleBack;

    @Override
    protected int initView() {
        return R.layout.atc_base_control;
    }

    @Override
    protected void initData() {
        initTitle();
    }

    private void initTitle() {
        titleContent.setText("基础控件");
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
