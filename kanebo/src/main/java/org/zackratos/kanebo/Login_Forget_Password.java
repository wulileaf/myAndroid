package org.zackratos.kanebo;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author leaf
 * @version 1.0
 * @Note 忘记密码
 */
public class Login_Forget_Password extends BaseActivity {

    @BindView(R.id.title_content)
    TextView title_content;

    @Override
    protected int initView() {
        return R.layout.atc_register;
    }

    @Override
    protected void initData() {
        title_content.setText("忘记密码");
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

    // 点击事件集中处理
    @OnClick({R.id.title_back})
    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }
}
