//package org.zackratos.myandroid.act.TestTCK;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.support.v7.app.AlertDialog;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import org.zackratos.basemode.mvp.BaseActivity;
//import org.zackratos.basemode.mvp.IPresenter;
//import org.zackratos.myandroid.R;
//import org.zackratos.myandroid.act.TestTCK.adapter.TieAdapter;
//import org.zackratos.myandroid.act.TestTCK.bean.BuildBean;
//import org.zackratos.myandroid.act.TestTCK.bean.PopuBean;
//import org.zackratos.myandroid.act.TestTCK.bean.TieBean;
//import org.zackratos.myandroid.act.TestTCK.listener.DialogUIDateTimeSaveListener;
//import org.zackratos.myandroid.act.TestTCK.listener.DialogUIItemListener;
//import org.zackratos.myandroid.act.TestTCK.listener.DialogUIListener;
//import org.zackratos.myandroid.act.TestTCK.listener.TdataListener;
//import org.zackratos.myandroid.act.TestTCK.widget.DateSelectorWheelView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * Created by Administrator on 2019/5/13.
// *
// * 学习资料
// */
//
//public class MainTestAct extends BaseActivity {
//
//    Activity mActivity;
//    Context mContext;
//    @BindView(R.id.ll_main)
//    LinearLayout llMain;
//    @BindView(R.id.btn_popu)
//    Button btnPopu;
//    String msg = "别总是来日方长，这世上挥手之间的都是人走茶凉。";
//
//    @Override
//    protected int initView() {
//        return R.layout.act_main_test_act;
//    }
//
//    @Override
//    protected void initData() {
//        mActivity = this;
//        mContext = getApplication();
//        DialogUIUtils.init(mContext);
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
//    @OnClick({R.id.btn_custom_alert, R.id.btn_custom_bottom_alert, R.id.btn_system_alert, R.id.btn_loading, R.id.btn_md_loading, R.id.btn_md_alert, R.id.btn_tie_alert,
//            R.id.btn_bottom_sheet_cancel, R.id.btn_center_sheet, R.id.btn_alert_input,
//            R.id.btn_alert_multichoose, R.id.btn_alert_singlechoose, R.id.btn_md_bottom_vertical, R.id.btn_md_bottom_horizontal,
//            R.id.btn_toast_top, R.id.btn_toast_center, R.id.btn_toast,
//            R.id.btn_select_ymd, R.id.btn_select_ymdhm, R.id.btn_select_ymdhms, R.id.btn_popu})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_popu:// 下拉选择框
//                DialogUIUtils.showPopuWindow(mContext, LinearLayout.LayoutParams.MATCH_PARENT, 4, btnPopu, new TdataListener() {
//                    @Override
//                    public void initPupoData(List<PopuBean> lists) {
//                        for (int i = 0; i < 5; i++) {
//                            PopuBean popu = new PopuBean();
//                            popu.setTitle("item" + i);
//                            popu.setId(i);
//                            lists.add(popu);
//                        }
//                    }
//
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int position) {
//
//                    }
//                });
//                break;
//            case R.id.btn_custom_alert:// 自定义弹出框
//                View rootView = View.inflate(mActivity, R.layout.custom_dialog_layout, null);
//                final Dialog dialog = DialogUIUtils.showCustomAlert(this, rootView, Gravity.CENTER, true, false).show();
//                rootView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DialogUIUtils.dismiss(dialog);
//                    }
//                });
//                break;
//            case R.id.btn_custom_bottom_alert:// 自定义底部弹出框
//                View rootViewB = View.inflate(mActivity, R.layout.custom_dialog_bottom_layout, null);
//                DialogUIUtils.showCustomBottomAlert(this, rootViewB).show();
//                break;
//            case R.id.btn_system_alert:// 系统原生的弹出框
////                new AlertDialog
////                        .Builder(mActivity)
////                        .setTitle("标题")
////                        .setMessage("这是内容")
////                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialogInterface, int i) {
////
////                            }
////                        })
////                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialogInterface, int i) {
////
////                            }
////                        })
////                        .create()
////                        .show();
//                break;
//            case R.id.btn_loading:// 水平方向的---------1
//                DialogUIUtils.showLoading(this, "加载中...", false, true, true, true).show();
//                break;
//            case R.id.btn_md_loading:// 垂直方向的--------1
//                DialogUIUtils.showMdLoading(this, "加载中...", true, true, true, true).show();
//                break;
//
//            case R.id.btn_alert_multichoose:// 多选-------1
//                String[] words = new String[]{"1", "2", "3"};
//                boolean[] choseDefault = new boolean[]{false, false, false};
//                DialogUIUtils.showMdMultiChoose(mActivity, "标题", words, choseDefault, new DialogUIListener() {
//                    @Override
//                    public void onPositive() {
//
//                    }
//
//                    @Override
//                    public void onNegative() {
//
//                    }
//                }).show();
//                break;
//            case R.id.btn_alert_singlechoose:// 单选------1
//                String[] words2 = new String[]{"1", "2", "3"};
//                DialogUIUtils.showSingleChoose(mActivity, "单选", 0, words2, new DialogUIItemListener() {
//                    @Override
//                    public void onItemClick(CharSequence text, int position) {
//                        showToast(text + "--" + position);
//                    }
//                }).show();
//                break;
//            case R.id.btn_md_alert:
//                DialogUIUtils.showMdAlert(mActivity, "标题", msg, new DialogUIListener() {
//                    @Override
//                    public void onPositive() {
//                        showToast("onPositive");
//                    }
//
//                    @Override
//                    public void onNegative() {
//                        showToast("onNegative");
//                    }
//
//                }).show();
//                break;
//            case R.id.btn_tie_alert:
//                DialogUIUtils.showAlert(mActivity, "标题", msg, "", "", "确定", "", true, true, true, new DialogUIListener() {
//                    @Override
//                    public void onPositive() {
//                        showToast("onPositive");
//                    }
//
//                    @Override
//                    public void onNegative() {
//                        showToast("onNegative");
//                    }
//
//                }).show();
//                break;
//            case R.id.btn_alert_input:// 弹出输入账号和密码-------1
//                DialogUIUtils.showAlert(mActivity, "登录", "", "请输入用户名", "请输入密码", "登录", "取消", false, true, true, new DialogUIListener() {
//                    @Override
//                    public void onPositive() {
//
//                    }
//
//                    @Override
//                    public void onNegative() {
//
//                    }
//
//                    @Override
//                    public void onGetInput(CharSequence input1, CharSequence input2) {
//                        super.onGetInput(input1, input2);
//                        showToast("input1:" + input1 + "--input2:" + input2);
//                    }
//                }).show();
//                break;
//            case R.id.btn_center_sheet: {
//                List<TieBean> strings = new ArrayList<TieBean>();
//                strings.add(new TieBean("1"));
//                strings.add(new TieBean("2"));
//                strings.add(new TieBean("3"));
//                DialogUIUtils.showSheet(mActivity, strings, "", Gravity.CENTER, true, true, new DialogUIItemListener() {
//                    @Override
//                    public void onItemClick(CharSequence text, int position) {
//                        showToast(text);
//                    }
//                }).show();
//            }
//            break;
//            case R.id.btn_bottom_sheet_cancel: {// 底部弹出
//                List<TieBean> strings = new ArrayList<TieBean>();
//                strings.add(new TieBean("1"));
//                strings.add(new TieBean("2"));
//                strings.add(new TieBean("3"));
//                DialogUIUtils.showSheet(mActivity, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
//                    @Override
//                    public void onItemClick(CharSequence text, int position) {
//                        showToast(text + "---" + position);
//                    }
//
//                    @Override
//                    public void onBottomBtnClick() {
//                        showToast("取消");
//                    }
//                }).show();
//            }
//            break;
//            case R.id.btn_md_bottom_vertical:
//                List<TieBean> datas2 = new ArrayList<TieBean>();
//                datas2.add(new TieBean("1"));
//                datas2.add(new TieBean("2"));
//                datas2.add(new TieBean("3"));
//                datas2.add(new TieBean("4"));
//                datas2.add(new TieBean("5"));
//                datas2.add(new TieBean("6"));
//                TieAdapter adapter = new TieAdapter(mContext, datas2, true);
//                BuildBean buildBean = DialogUIUtils.showMdBottomSheet(mActivity, true, "", datas2, 0, new DialogUIItemListener() {
//                    @Override
//                    public void onItemClick(CharSequence text, int position) {
//                        showToast(text + "---" + position);
//                    }
//                });
//                buildBean.mAdapter = adapter;
//                buildBean.show();
//                break;
//            case R.id.btn_md_bottom_horizontal:
//                List<TieBean> datas3 = new ArrayList<TieBean>();
//                datas3.add(new TieBean("1"));
//                datas3.add(new TieBean("2"));
//                datas3.add(new TieBean("3"));
//                datas3.add(new TieBean("4"));
//                datas3.add(new TieBean("5"));
//                datas3.add(new TieBean("6"));
//                DialogUIUtils.showMdBottomSheet(mActivity, false, "标题", datas3, 4, new DialogUIItemListener() {
//                    @Override
//                    public void onItemClick(CharSequence text, int position) {
//                        showToast(text + "---" + position);
//                    }
//                }).show();
//                break;
//
//            case R.id.btn_toast_top:
//                DialogUIUtils.showToastTop("上部的Toast弹出方式");
//                break;
//            case R.id.btn_toast_center:
//                DialogUIUtils.showToastCenter("中部的Toast弹出方式");
//                break;
//            case R.id.btn_toast:
//                DialogUIUtils.showToast("默认的Toast弹出方式");
//                break;
//
//
////            case R.id.btn_select_ymd: {
////                DialogUIUtils.showDatePick(mActivity, Gravity.CENTER, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDD, 0, new DialogUIDateTimeSaveListener() {
////                    @Override
////                    public void onSaveSelectedDate(int tag, String selectedDate) {
////
////                    }
////                }).show();
////            }
////            break;
////            case R.id.btn_select_ymdhm: {
////                DialogUIUtils.showDatePick(mActivity, Gravity.CENTER, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMM, 0, new DialogUIDateTimeSaveListener() {
////                    @Override
////                    public void onSaveSelectedDate(int tag, String selectedDate) {
////
////                    }
////                }).show();
////            }
////            break;
////            case R.id.btn_select_ymdhms: {
////                DialogUIUtils.showDatePick(mActivity, Gravity.BOTTOM, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDDHHMMSS, 0, new DialogUIDateTimeSaveListener() {
////                    @Override
////                    public void onSaveSelectedDate(int tag, String selectedDate) {
////
////                    }
////                }).show();
////            }
////            break;
//
//
//
//        }
//    }
//
//
//    public void showToast(CharSequence msg) {
//        DialogUIUtils.showToastLong(msg.toString());
//    }
//}
