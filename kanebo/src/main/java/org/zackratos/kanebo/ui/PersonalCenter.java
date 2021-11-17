package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心
 */
public class PersonalCenter extends BaseActivity {

    @BindView(R.id.title_content)
    TextView title_content;
    @BindView(R.id.title_back)
    ImageView title_back;
    @BindView(R.id.perMsg)// 个人信息
    RelativeLayout perMsg;
    @BindView(R.id.mmChange)// 修改密码
    RelativeLayout mmChange;
    @BindView(R.id.gxCheck)// 更新检查
    RelativeLayout gxCheck;
    @BindView(R.id.outLogin)// 退出登录
    RelativeLayout outLogin;

    @Override
    protected int initView() {
        return R.layout.atc_personalcenter;
    }

    @Override
    protected void initData() {
        title_content.setText("个人中心");
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

    @OnClick({R.id.title_back, R.id.perMsg, R.id.mmChange, R.id.gxCheck, R.id.outLogin})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.perMsg:
                break;
            case R.id.mmChange:
                break;
            case R.id.gxCheck:
                break;
            case R.id.outLogin:
                break;
            default:
                break;
        }
    }
}
