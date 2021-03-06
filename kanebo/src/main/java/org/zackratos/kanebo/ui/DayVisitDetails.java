package org.zackratos.kanebo.ui;

import android.annotation.SuppressLint;
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

// ????????????
public class DayVisitDetails extends BaseActivity {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.xxValue)
    TextView xxValue;// ?????????
    @BindView(R.id.xxCityValue)
    TextView xxCityValue;// ??????
    @BindView(R.id.storeLevelValue)
    TextView storeLevelValue;// ????????????


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
    //????????????
    CityPickerView mPicker = new CityPickerView();
    Query<Dictionary> dictQuery;// Bean???
    DictionaryDao dictDao;// Dao???

    @Override
    protected int initView() {
        return R.layout.atc_day_visit_details;
    }

    @Override
    protected void initData() {
        initTitle();
        getIntentData();
        //???????????????iOS???????????????????????????
        mPicker.init(this);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        dictDao = daoSession.getDictionaryDao();
        dictQuery = dictDao.queryBuilder().orderDesc(DictionaryDao.Properties.Id).build();

        // ?????????
        List<PopupwindowBean> list = new ArrayList<>();
        PopupwindowBean bean = new PopupwindowBean();
        bean.setName("???????????????");
        list.add(bean);
        PopupwindowBean bean1 = new PopupwindowBean();
        bean1.setName("???????????????");
        list.add(bean1);
        initPopupWindow(list);
        xxValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //??????PopupWindow????????????
                mpop.showAtLocation(DayVisitDetails.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        // ??????
        xxCityValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //?????????????????????????????????????????????????????????????????????????????????????????????????????????demo
                CityConfig cityConfig = new CityConfig.Builder().build();
                mPicker.setConfig(cityConfig);
                //???????????????????????????????????????
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //??????province
                        //??????city
                        //??????district
                        Log.i("city", province + "--" + city + "--" + district);
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showLongToast(DayVisitDetails.this, "?????????");
                    }
                });
                //??????
                mPicker.showCityPicker();
            }
        });

        // ????????????
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
                //??????PopupWindow????????????
                mpopCity.showAtLocation(DayVisitDetails.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });


        // ??????Spinner
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("??????");
//        list.add("??????");
//        list.add("??????");
//        list.add("?????????");
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

        // ??????OK
        //????????????RadioGroup???????????????
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
        // ????????????list?
        Log.i("DBMsg", new Gson().toJson(list));
        return list;
    }

    // RadioButton
    class MyRadioButtonListener implements RadioGroup.OnCheckedChangeListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.rb_FeMale:
                    // ????????????????????????
                    Log.i("sex", "??????????????????" + rb_FeMale.getText().toString());
                    break;
                case R.id.rb_Male:
                    // ????????????????????????
                    Log.i("sex", "??????????????????" + rb_Male.getText().toString());
                    break;
            }
        }
    }

    // CheckBox
    // ???????????????????????????????????????????????????
//    android?????????checkbox???????????????????????????
//    android:checked="true"
//    ??????checkbox.isChecked();???????????????????????????
//    checkbox.setChecked(true|false)????????????checkbox?????????
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

    // ?????????
    private void initPopupWindow(List<PopupwindowBean> list) {
        mpop = new CustomPopupWindow(DayVisitDetails.this, list);
        mpop.popupwindowList.setOnItemClickListener(new PopupwindowList.OnItemClickListener() {
            @Override
            public void onClick(int position, PopupwindowBean popupwindowBean) {
                showToast("?????????" + position);
                Log.i("778899", "============" + position);
                mpop.dismiss();
            }
        });
    }

    // ?????????
    private void initSLPopupWindow(List<PopupwindowBean> list) {
        mpopCity = new CustomPopupWindow(DayVisitDetails.this, list);
        mpopCity.popupwindowList.setOnItemClickListener(new PopupwindowList.OnItemClickListener() {
            @Override
            public void onClick(int position, PopupwindowBean popupwindowBean) {
                showToast("?????????" + position);
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
                showToast("?????????" + position);
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
                showToast("???????????????");
                break;
            default:
                break;
        }
    }

    private void initTitle() {
        titleContent.setText("????????????");
        titleRight.setVisibility(View.VISIBLE);
    }

    // ??????????????????????????????
//    @Override
//    public void setOnItemClick(View v) {
//        Log.i("bsweizhi", v.getId() + "----------------");
//        switch (v.getId()) {
//
//        }
//    }

}
