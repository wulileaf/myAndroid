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

import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.xuexiang.citypicker.CityPicker;
import com.xuexiang.citypicker.adapter.OnLocationListener;
import com.xuexiang.citypicker.adapter.OnPickListener;
import com.xuexiang.citypicker.model.City;
import com.xuexiang.citypicker.model.HotCity;
import com.xuexiang.citypicker.model.LocateState;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.basemode.adapter.PopupwindowList;
import org.zackratos.basemode.bean.PopupwindowBean;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.CustomPopupWindow;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.App;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.greendao.DaoSession;
import org.zackratos.kanebo.greendao.Dictionary;
import org.zackratos.kanebo.greendao.DictionaryDao;
import org.zackratos.kanebo.greendao.TestBean;
import org.zackratos.kanebo.greendao.TestBeanDao;

import java.util.ArrayList;
import java.util.List;

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
    TextView xxValue;// 办事处
    @BindView(R.id.xxCityValue)
    TextView xxCityValue;// 城市
    @BindView(R.id.storeLevelValue)
    TextView storeLevelValue;// 网点级别


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

    private CustomPopupWindow mpop, mpopCity;
    private SpinnerAdapter adapter = null;
    private List<HotCity> mHotCities;
    //申明对象
    CityPickerView mPicker = new CityPickerView();
    Query<Dictionary> dictQuery;// Bean类
    DictionaryDao dictDao;// Dao类

    @Override
    protected int initView() {
        return R.layout.atc_day_visit_details;
    }

    @Override
    protected void initData() {
        initTitle();
        getIntentData();
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        dictDao = daoSession.getDictionaryDao();
        dictQuery = dictDao.queryBuilder().orderDesc(DictionaryDao.Properties.Id).build();

        // 办事处
        List<PopupwindowBean> list = new ArrayList<>();
        PopupwindowBean bean = new PopupwindowBean();
        bean.setName("安徽办事处");
        list.add(bean);
        PopupwindowBean bean1 = new PopupwindowBean();
        bean1.setName("北京办事处");
        list.add(bean1);
        initPopupWindow(list);
        xxValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置PopupWindow中的位置
                mpop.showAtLocation(DayVisitDetails.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        // 城市
        xxCityValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
                CityConfig cityConfig = new CityConfig.Builder().build();
                mPicker.setConfig(cityConfig);
                //监听选择点击事件及返回结果
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //省份province
                        //城市city
                        //地区district
                        Log.i("city", province + "--" + city + "--" + district);
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showLongToast(DayVisitDetails.this, "已取消");
                    }
                });
                //显示
                mPicker.showCityPicker();
            }
        });

        // 网点级别
        List<Dictionary> dictionary = queryByName("LT_StoreLevel");
        List<PopupwindowBean> list1 = new ArrayList<>();
        for (int i = 0; i < dictionary.size(); i++) {
            PopupwindowBean popupwindowBean = new PopupwindowBean();
            popupwindowBean.setName(dictionary.get(i).getDictName());
            popupwindowBean.setValue(dictionary.get(i).getDictId());
            list1.add(popupwindowBean);
        }
        initSLPopupWindow(list1);
        storeLevelValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置PopupWindow中的位置
                mpopCity.showAtLocation(DayVisitDetails.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });


        // 测试Spinner
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("合肥");
//        list.add("铜陵");
//        list.add("芜湖");
//        list.add("马鞍山");
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
//        city.setAdapter(adapter);
//        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        // 测试OK
        //注意是给RadioGroup绑定监视器
        rg.setOnCheckedChangeListener(new MyRadioButtonListener());

        // checkBox
        mCheckBoxBJ.setOnCheckedChangeListener(new checkBoxChangeListenerImpl());
        mCheckBoxGZ.setOnCheckedChangeListener(new checkBoxChangeListenerImpl());
        mCheckBoxSH.setOnCheckedChangeListener(new checkBoxChangeListenerImpl());
    }

    private List<Dictionary> queryByName(String dictName) {
        QueryBuilder<Dictionary> builder = dictDao.queryBuilder();
        Query<Dictionary> query = builder
                .where(DictionaryDao.Properties.DictType.eq(dictName))
                .build();
        List<Dictionary> list = query.list();
        // 如何打印list?
        Log.i("DBMsg", new Gson().toJson(list));
        return list;
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

    // 办事处
    private void initPopupWindow(List<PopupwindowBean> list) {
        mpop = new CustomPopupWindow(DayVisitDetails.this, list);
        mpop.popupwindowList.setOnItemClickListener(new PopupwindowList.OnItemClickListener() {
            @Override
            public void onClick(int position, PopupwindowBean popupwindowBean) {
                showToast("点击了" + position);
                Log.i("778899", "============" + position);
                mpop.dismiss();
            }
        });
    }

    // 办事处
    private void initSLPopupWindow(List<PopupwindowBean> list) {
        mpopCity = new CustomPopupWindow(DayVisitDetails.this, list);
        mpopCity.popupwindowList.setOnItemClickListener(new PopupwindowList.OnItemClickListener() {
            @Override
            public void onClick(int position, PopupwindowBean popupwindowBean) {
                showToast("点击了" + position);
                Log.i("778899", "============" + position);
                mpopCity.dismiss();
            }
        });
    }

    private void initCityPopupWindow(List<PopupwindowBean> list) {
        mpopCity = new CustomPopupWindow(DayVisitDetails.this, list);
//        mpop.setOnItemClickListener(this);
        mpopCity.popupwindowList.setOnItemClickListener(new PopupwindowList.OnItemClickListener() {
            @Override
            public void onClick(int position, PopupwindowBean popupwindowBean) {
                showToast("点击了" + position);
                Log.i("778899", "============" + position);
                mpopCity.dismiss();
            }
        });
    }

    private void getIntentData() {
        String intent = getIntent().getStringExtra("StoreMsg");
        try {
            JSONObject jsonObject = new JSONObject(intent);
            Log.i("StoreMsg", jsonObject + "--------------");
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
