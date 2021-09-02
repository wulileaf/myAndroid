package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.basemode.adapter.PopupwindowList;
import org.zackratos.basemode.bean.PopupwindowBean;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.CustomPopupWindow;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

// 门店详情
public class DayVisitDetails extends BaseActivity {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.xxValue)
    TextView xxValue;



    @BindView(R.id.spCity)
    Spinner city;
    @BindView(R.id.rg_sex)
    RadioGroup rg;
    @BindView(R.id.rb_Male)
    RadioButton rb_Male;
    @BindView(R.id.rb_FeMale)
    RadioButton rb_FeMale;
    @BindView(R.id.check_box_bj)
    CheckBox mCheckBoxBJ;
    @BindView(R.id.check_box_sh)
    CheckBox mCheckBoxSH;
    @BindView(R.id.check_box_gz)
    CheckBox mCheckBoxGZ;



    private CustomPopupWindow mpop;
    private SpinnerAdapter adapter = null;

    @Override
    protected int initView() {
        return R.layout.atc_day_visit_details;
    }

    @Override
    protected void initData() {
        Log.i("778899", "============111111");
        initTitle();
        getIntentData();
        initPopupWindow();
        xxValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置PopupWindow中的位置
                mpop.showAtLocation(DayVisitDetails.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });


        // 测试Spinner
        ArrayList<String> list = new ArrayList<String>();
        list.add("合肥");
        list.add("铜陵");
        list.add("芜湖");
        list.add("马鞍山");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 测试OK
        //注意是给RadioGroup绑定监视器
        rg.setOnCheckedChangeListener(new MyRadioButtonListener());

        // checkBox
        mCheckBoxBJ.setOnCheckedChangeListener(new checkBoxChangeListenerImpl());
        mCheckBoxGZ.setOnCheckedChangeListener(new checkBoxChangeListenerImpl());
        mCheckBoxSH.setOnCheckedChangeListener(new checkBoxChangeListenerImpl());
    }

    // RadioButton
    class MyRadioButtonListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.rb_FeMale:
                    // 当用户选择女性时
                    Log.i("sex", "当前用户选择" + rb_FeMale.getText().toString());
                    break;
                case R.id.rb_Male:
                    // 当用户选择男性时
                    Log.i("sex", "当前用户选择" + rb_Male.getText().toString());
                    break;
            }
        }
    }

    // CheckBox
    // 目前缺少灵活添加和修改数据的功能？
//    android怎么把checkbox状态设置为选中状态
//    android:checked="true"
//    通过checkbox.isChecked();是判断后是否选中，
//    checkbox.setChecked(true|false)是赋某个checkbox选中。
    class checkBoxChangeListenerImpl implements CompoundButton.OnCheckedChangeListener {
        private String mString;

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Log.i("sex", compoundButton.getId() + "------------" + b);
            switch (compoundButton.getId()) {
                case R.id.check_box_bj:
                    mString = "Bei Jing";
                    mCheckBoxBJ.setChecked(b);
                    break;
                case R.id.check_box_gz:
                    mString = new String("Guang Zhou");
                    break;
                case R.id.check_box_sh:
                    mString = new String("Shang Hai");
                    break;
                default:
                    break;
            }
            if (b) {
                Toast.makeText(getApplicationContext(), mString, Toast.LENGTH_LONG).show();
            }
        }
    }

    // 初始化底部弹出框
    private void initPopupWindow() {
        mpop = new CustomPopupWindow(DayVisitDetails.this);
//        mpop.setOnItemClickListener(this);
        mpop.popupwindowList.setOnItemClickListener(new PopupwindowList.OnItemClickListener() {
            @Override
            public void onClick(int position, PopupwindowBean popupwindowBean) {
                showToast("点击了" + position);
                Log.i("778899", "============" + position);
                mpop.dismiss();
            }
        });
    }

    private void getIntentData() {
        String intent = getIntent().getStringExtra("StoreMsg");
        try {
            JSONObject jsonObject = new JSONObject(intent);
//            Log.i("StoreMsg", jsonObject.get("StoreName") + "--------------");
        } catch (JSONException e) {
            e.printStackTrace();
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

    private void initTitle() {
        titleContent.setText("门店详情");
        titleRight.setVisibility(View.VISIBLE);
    }

    // 底部弹出框的点击监听
//    @Override
//    public void setOnItemClick(View v) {
//        Log.i("bsweizhi", v.getId() + "----------------");
//        switch (v.getId()) {
//
//        }
//    }
}
