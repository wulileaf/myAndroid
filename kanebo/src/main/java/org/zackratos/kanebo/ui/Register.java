package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date 2021/2/20
 * @author leaf
 * @version 1.0
 * @Note 注册
 */
public class Register extends BaseActivity {


    @BindView(R.id.iv_get_password)
    TextView iv_get_password;// 获取验证码
    @BindView(R.id.btn_register)
    Button btn_register;// 注册
    @BindView(R.id.title_content)
    TextView title_content;
    private Register.TimeCount time;

    @Override
    protected int initView() {
        return R.layout.act_login_forget_password;
    }

    @Override
    protected void initData() {
        title_content.setText("注册");
        time = new Register.TimeCount(60000, 1000);
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

    // 发送短信记时
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            iv_get_password.setClickable(false);
            iv_get_password.setText("(" + millisUntilFinished / 1000 + "s)后可重发");
            btn_register.setClickable(false);
        }

        @Override
        public void onFinish() {
            iv_get_password.setClickable(true);
            iv_get_password.setText("重新获取");
            btn_register.setClickable(true);

        }
    }

    // 点击事件集中处理
    @OnClick({R.id.iv_get_password, R.id.btn_register, R.id.title_back})
    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_get_password:
                time.start();
                break;
            case R.id.btn_register:
                showToast("点击了注册");
                break;
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.cancel();// 防止退出此页面时，导致iv_get_password为空，进而导致应用奔溃的问题
    }
}
