//package org.zackratos.myandroid.ui;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//import android.widget.CompoundButton;
//import android.widget.RelativeLayout;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import org.zackratos.basemode.mvp.BaseActivity;
//import org.zackratos.basemode.mvp.IPresenter;
//import org.zackratos.myandroid.R;
//
////import butterknife.BindView;
////import butterknife.OnClick;
//
//
//// 主页面
//public class Main extends BaseActivity {
//
//    @BindView(R.id.title_content)
//    TextView title_content;// 名称
//    @BindView(R.id.switch_chose)
//    Switch switch_chose;
//
//    @Override
//    protected int initView() {
//        return R.layout.act_main;
//    }
//
//    @Override
//    protected void initData() {
//        title_content.setText("首页");
//    }
//
//    @Override
//    protected IPresenter getPresenter() {
//        return null;
//    }
//
//    @Override
//    protected Intent mainIntent(Context context) {
//        return null;
//    }
//
//    // 点击事件集中处理
//    @OnClick({R.id.title_back, R.id.item_2019, R.id.switch_chose})
//    public void click(View view) {
//        int id = view.getId();
//        switch (id) {
//            case R.id.title_back:
//                finish();
//                break;
//            case R.id.item_2019:
//                showToast("点击了2019");
//                break;
//            case R.id.switch_chose:
//                switch_chose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        //控制开关字体颜色
//                        if (b) {
//                            switch_chose.setSwitchTextAppearance(Main.this, R.style.s_true);
//                        } else {
//                            switch_chose.setSwitchTextAppearance(Main.this, R.style.s_false);
//                        }
//                    }
//                });
//                break;
//            default:
//                break;
//        }
//    }
//
//
//}
