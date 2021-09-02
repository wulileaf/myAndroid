//package org.zackratos.myandroid.ui;
//
//import android.content.Context;
//import android.content.Intent;
//import android.text.TextUtils;
//import android.text.method.HideReturnsTransformationMethod;
//import android.text.method.PasswordTransformationMethod;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.zackratos.basemode.mvp.BaseActivity;
//import org.zackratos.basemode.mvp.BaseId;
//import org.zackratos.basemode.mvp.BaseNetworkDetection;
//import org.zackratos.basemode.mvp.BaseSp;
//import org.zackratos.basemode.mvp.IPresenter;
//import org.zackratos.myandroid.R;
//import org.zackratos.myandroid.act.TestTCK.DialogUIUtils;
//
//import java.io.IOException;
//
//import butterknife.BindView;
//import butterknife.OnCheckedChanged;
//import butterknife.OnClick;
//import me.leefeng.promptlibrary.PromptDialog;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.jackson.JacksonConverterFactory;
//
//import static org.zackratos.basemode.mvp.BaseUrl.BASEURL;
//import static org.zackratos.basemode.mvp.BaseUrl.NYUURL;
//
///**
// * @author leaf
// * @version 1.0
// * @Note 登录页面
// */
//
//public class Login extends BaseActivity {
//
//    private PromptDialog promptDialog;
//    @BindView(R.id.et_account)
//    EditText et_account;// 登录账号
//
//    @BindView(R.id.et_password)
//    EditText et_password;// 密码
//
//    @BindView(R.id.btn_login)
//    Button btn_login;// 登录按钮
//
//    @BindView(R.id.tex_no_password)
//    TextView tex_no_password;// 忘记密码
//
//    @BindView(R.id.tex_register)
//    TextView tex_register;// 立即注册
//
////    @BindView(R.id.checkBox_password)
////    CheckBox checkBox_password;// 记住密码
//
//    @BindView(R.id.iv_see_password)
//    ImageView iv_see_password;// 查看密码
//    private int see = 0;// 对是否查看密码进行的标识 0是隐藏,1是显示 ps:默认是隐藏状态
//
//    private boolean r_password;
//
////    @OnCheckedChanged(R.id.checkBox_password)
////    void onRememberChecked(boolean checked) {
////        r_password = checked;
////    }
//
//    private InputMethodManager manager;// 获取整个屏幕
//
//    private BaseSp baseSp;
//
//    @Override
//    protected void initData() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 除去手机状态栏
//        et_account.setCursorVisible(false);
//        et_password.setCursorVisible(false);
//        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏密码
//        baseSp = new BaseSp(this);
//        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
////        r_password = baseSp.getBoolean(BaseId.REMEMBER_KEY, false);// 查看密码是否设置了记住的状态
//        String account = baseSp.getString(BaseId.USERCODE, "");// 默认记住上一次登录后的账号
//        et_account.setText(account);
////        checkBox_password.setChecked(r_password);
////        if (r_password) {
////            String password = baseSp.getString(BaseId.PASSWORD_KEY, null);
////            et_password.setText(password);
////        }
//
//        touchAnalysis();
//    }
//
//    @Override
//    protected int initView() {
//        // 除去手机标题栏
//        // PS：requestWindowFeature(Window.FEATURE_NO_TITLE);这一句一定要放在R.layout.activity_login_page之前位置执行
//        // 否则会报错requestFeature() must be called before adding content
//        // setTheme(android.R.style.Theme_Black_NoTitleBar);
//        // requestWindowFeature(Window.FEATURE_NO_TITLE);
//        return R.layout.act_login;
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
//
//    /**
//     * 提示在按一次退出
//     */
//    long temptime = 0;
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {  // 2s内再次选择back键有效
//            if (System.currentTimeMillis() - temptime > 2000) {
//                showToast("再按一次退出应用");
//                temptime = System.currentTimeMillis();
//            } else {
//                finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    /**
//     * 在android中点击EditText的时候会弹出软键盘，这时候如果想隐藏软键盘或者填完内容后点其他的地方直接隐藏软键盘，可以按一下方法处理。
//     * 首先获得软键盘Manager
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
//                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                et_account.setCursorVisible(false);
//                et_password.setCursorVisible(false);
//            }
//        }
//        return super.onTouchEvent(event);
//    }
//
//    /**
//     * 触碰处理
//     * 点击显示光标
//     */
//    private void touchAnalysis() {
//        et_account.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
//                    et_account.setCursorVisible(true);// 再次点击显示光标
//                    et_account.setSelection(et_account.getText().toString().length());
//                }
//                return false;
//            }
//        });
//
//        et_password.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
//                    et_password.setCursorVisible(true);// 再次点击显示光标
//                    et_password.setSelection(et_password.getText().toString().length());
//                }
//                return false;
//            }
//        });
//    }
//
//    /**
//     * 记住密码
//     * ps:测试
//     */
////    @OnCheckedChanged(R.id.checkBox_password)
////    public void pass(CompoundButton view, boolean ischanged) {
////    }
//
//    /**
//     * 查看密码
//     */
//    @OnClick(R.id.iv_see_password)
//    public void seepassword() {
//        String password = et_password.getText().toString();
//        if (!password.isEmpty()) {
//            if (see == 0) {
//                // 显示密码
//                iv_see_password.setImageResource(R.drawable.image_password_bg);
//                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                see = 1;
//            } else if (see == 1) {
//                // 隐藏密码
//                iv_see_password.setImageResource(R.mipmap.noout);
//                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                see = 0;
//            }
//        } else {
//            showMidToast("请先输入密码在进行查看！");
//        }
//    }
//
//    /**
//     * 点击登录按钮
//     */
//    @OnClick(R.id.btn_login)
//    public void login() {
//        String account = et_account.getText().toString();
//        String password = et_password.getText().toString();
//
//        // 进行登录请求之前查看手机的网络环境
//        Boolean yrn = BaseNetworkDetection.isNetworkAvailable(Login.this);
//        if (yrn) {
//            // Android-PromptDialog
//            promptDialog = new PromptDialog(this);
//            promptDialog.showLoading("正在登录");
////            new Thread(new Runnable() {
////                @Override
////                public void run() {
////                    try {
////                        Thread.sleep(7000);
////                        Thread.interrupted();
////                        promptDialog.dismiss();
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }).start();
//
//
//            // 修改后
////            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
////                showToast("账号或密码输入为空！");
////            } else {
////                showProgressDialog("正在登录，请稍后...", false);
////                // 请求登录接口
////                showToast(account + password);
//
//
//            // jjdxm-dialogui框架
////                DialogUIUtils.showMdLoading(this, "加载中...", true, true, true, true).show();
////            }
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(BASEURL)
//                    .addConverterFactory(JacksonConverterFactory.create())
//                    .build();
//            InterRetrofit inter = retrofit.create(InterRetrofit.class);
//            Call<ResponseBody> datajs = inter.getUserLogin("18600000001", "123456");
//            datajs.enqueue(new retrofit2.Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    try {
//                        JSONObject json = null;
//                        try {
//                            json = new JSONObject(response.body().string());
//                            Log.i("Mess", json + "");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        int statue = Integer.parseInt(json.getString("Status"));
//                        String mess = json.getString("Message");
//                        if (statue == 1) {
//                            promptDialog.dismiss();
////                            showToast("" + mess);
//                            startActivity(new Intent(Login.this, Main.class));
//                        } else if (statue == -1) {
//                            promptDialog.dismiss();
//                            showToast("" + mess);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    promptDialog.dismiss();
//                    showToast("登录失败,请检查网络是否正常" + t);
//                }
//            });
//        } else {
//            showToast("当前手机未连接网络，请开启网络后重试！");
//        }
//
//    }
//
//    @OnClick({R.id.tex_no_password, R.id.tex_register})
//    public void click(View view) {
//        int id = view.getId();
//        switch (id) {
//            case R.id.tex_no_password:
//                showToast("点击了忘记密码");
//                break;
//            case R.id.tex_register:
////                showToast("点击了立即注册");
//                startActivity(new Intent(this, Login_Forget_Password.class));
//                break;
//            default:
//                break;
//        }
//
//    }
//
//
//}
