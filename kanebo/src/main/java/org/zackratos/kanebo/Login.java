package org.zackratos.kanebo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.BaseId;
import org.zackratos.basemode.mvp.BaseNetworkDetection;
import org.zackratos.basemode.mvp.BaseSp;
import org.zackratos.basemode.mvp.BaseTimeFormat;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.greendao.DaoSession;
import org.zackratos.kanebo.greendao.Dictionary;
import org.zackratos.kanebo.greendao.DictionaryDao;
import org.zackratos.kanebo.networkRequestInterface.InterRetrofit;
import org.zackratos.kanebo.networkRequestInterface.RequestBase;
import org.zackratos.kanebo.request.LeafRequest;
import org.zackratos.kanebo.request.LoginParam;
import org.zackratos.kanebo.tools.tools;
import org.zackratos.kanebo.ui.Main;
import org.zackratos.kanebo.ui.Register;
import org.zackratos.kanebo.xml.Msg;
import org.zackratos.kanebo.xml.TypeList;
import org.zackratos.kanebo.xml.XmlDownData;
import org.zackratos.kanebo.xml.XmlLogin;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.zackratos.basemode.mvp.BaseUrl.OPPLELTURL;

/**
 * @author leaf
 * @version 1.0
 * @Note 登录页面
 */

public class Login extends BaseActivity {

    @BindView(R.id.et_account)
    EditText et_account;// 登录账号

    @BindView(R.id.et_password)
    EditText et_password;// 密码

    @BindView(R.id.btn_login)
    Button btn_login;// 登录按钮

    @BindView(R.id.tex_no_password)
    TextView tex_no_password;// 忘记密码

    @BindView(R.id.tex_register)
    TextView tex_register;// 立即注册

//    @BindView(R.id.checkBox_password)
//    CheckBox checkBox_password;// 记住密码

    @BindView(R.id.iv_see_password)
    ImageView iv_see_password;// 查看密码

    @BindView(R.id.login_relat)
    RelativeLayout login_relat;

    @BindView(R.id.load_name)
    TextView load_name;

    @BindView(R.id.login_line)
    LinearLayout login_line;

    @BindView(R.id.login_line_bottom)
    RelativeLayout login_line_bottom;

    private InputMethodManager manager;// 获取整个屏幕
    private BaseSp baseSp;
    private RequestBase requestBase = new RequestBase();
    private int iden = 0;
    private int see = 0;// 对是否查看密码进行的标识 0是隐藏,1是显示 ps:默认是隐藏状态
    private boolean r_password;

    // 数据库的一个Bean类对应一个Dao
    Query<Dictionary> dictionaryQuery;// Bean类
    DictionaryDao dictionaryDao;// Dao类

    @Override
    protected void initData() {

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 除去手机状态栏
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        dictionaryDao = daoSession.getDictionaryDao();
        dictionaryQuery = dictionaryDao.queryBuilder().orderAsc(DictionaryDao.Properties.Id).build();
        et_account.setCursorVisible(false);
        et_password.setCursorVisible(false);
        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏密码
        baseSp = new BaseSp(this);

        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        r_password = baseSp.getBoolean(BaseId.REMEMBER_KEY, false);// 查看密码是否设置了记住的状态
//        String account = baseSp.getString(BaseId.USERCODE, "");// 默认记住上一次登录后的账号
        et_account.setText(baseSp.getLoginAccount());

//        checkBox_password.setChecked(r_password);
//        if (r_password) {
//            String password = baseSp.getString(BaseId.PASSWORD_KEY, null);
//            et_password.setText(password);
//        }

        touchAnalysis();
//        tools.getBaseSp(Login.this).saveNetWork(getNetWork());// 保存手机当前网络环境

        // 获取上一次登录的用户信息
        iden = tools.getUserLoginIden(Login.this);
        if (iden == 0) {
            // 不是当天登录
            // 1.删除对应的数据库数据
            // 2.开启登录后下载新数据存入数据库的操作
            // 3.保存初次登录的用户信息数据
            // 4.保存初次登录的用户登录信息
            dictionaryDao.deleteAll();
        } else if (iden == 1) {
            // 是当天登录
        }

    }

    @Override
    protected int initView() {
        // 除去手机标题栏
        // PS：requestWindowFeature(Window.FEATURE_NO_TITLE);这一句一定要放在R.layout.activity_login_page之前位置执行
        // 否则会报错requestFeature() must be called before adding content
        // setTheme(android.R.style.Theme_Black_NoTitleBar);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_login;
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }


    // 记住密码
//    @OnCheckedChanged(R.id.checkBox_password)
//    public void pass(CompoundButton view, boolean ischanged) {
//    }

    // 登录按钮
    @OnClick(R.id.btn_login)
    public void login() {
        String account = et_account.getText().toString();
        String password = et_password.getText().toString();
        // 检测手机的网络环境
        Boolean yrn = BaseNetworkDetection.isNetworkAvailable(Login.this);
        if (yrn) {
            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                showToast("账号或密码为空");
            } else {
                showorhide(0, "");
                InterRetrofit interRetrofit = new LeafRequest().getXml(OPPLELTURL);
                Call<XmlLogin> datajsa = interRetrofit.login(LoginParam.getLoginParam(et_account.getText().toString(), et_password.getText().toString(), "A00000AE42C25A", "android", "huawei"));
                datajsa.enqueue(new Callback<XmlLogin>() {
                    @Override
                    public void onResponse(Call<XmlLogin> call, Response<XmlLogin> response) {
                        // xml解析  测试OK
//                    Log.i("MessLJ", response.body().username + "");// 测试获取单个字段数据OK
//                    Log.i("MessLJ", response.body().list + "");// 测试获取组数数据OK
                        List<TypeList> typeList = response.body().list;// 这个长度可以根据标签数来计算
                        List<Msg> msgList = typeList.get(0).getList();

                        if (iden == 0) {
                            // 下载数据库
                            downData(msgList, response.body().userid, BaseTimeFormat.getDate());
                            // 保存用户信息
                            // 保存登录信息
                            tools.saveJSONUserIfon(Login.this,
                                    response.body().userid,
                                    response.body().username,
                                    response.body().isba,
                                    response.body().postname,
                                    response.body().leadername,
                                    response.body().empcode,
                                    response.body().orgname,
                                    BaseTimeFormat.getDate());
                            new BaseSp(Login.this).saveLoginDate(BaseTimeFormat.getDate(), 1);
                            new BaseSp(Login.this).saveLoginAccount(account);// 保存上一次登录人员的账号
                        }
                        showorhide(1, "");
                        if (response.body().success == 1) {
                            startActivity(new Intent(Login.this, Main.class));
                        } else if (response.body().success == -3) {
                            showToast("登录失败：" + response.body().errormsg);
                        } else {
                            showToast("登录失败异常");
                        }
                    }

                    @Override
                    public void onFailure(Call<XmlLogin> call, Throwable t) {
                        showorhide(1, "");
                        showToast("登录失败,请检查网络是否正常" + t);
                    }
                });
            }
        } else {
            showToast("当前手机未连接网络，请开启网络后重试");
        }


        // 处理josn返回数据
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(JLBURL)
//                    .addConverterFactory(JacksonConverterFactory.create())
//                    .build();
//            InterRetrofit intera = retrofit.create(InterRetrofit.class);


//        Map<String, String> params = new HashMap<>();
//        params.put("userName", "test");
//        params.put("passWord", "123456");
//        InterRetrofit interRetrofit = new LeafRequest().getJson(JLBURL);
//        Call<ResponseBody> datajsa = interRetrofit.wxlogin(params);

        // 返回JSON数据格式
//            datajsa.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                    // JSON解析 测试OK
//                        try {
//                            JSONObject json = null;
//                            try {
//                                ResponseBody responseBody = response.body();
//                                String result = responseBody.string();
//                                Log.i("MessLJ", result + "");
//                                json = new JSONObject(result);
////                                Log.i("MessLJ", json + "");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            int statue = Integer.parseInt(json.getString("Status"));
//                            String mess = json.getString("Message");
//                            if (statue == 1) {
//                                showMidToast("登录成功");
//                                startActivity(new Intent(Login.this, Main.class));
//                                showorhide(1,");
//                            } else if (statue == -1) {
////                            promptDialog.dismiss();
//                                showToast("登录异常" + mess);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    showorhide(1,");
//                    showToast("登录失败,请检查网络是否正常" + t);
//                }
//            });
//
//        } else {
//            showToast("当前手机未连接网络，请开启网络后重试");
//        }

    }

    // 有序更新各个表，如何做到有序更新？
    public void downData(List<Msg> msgList, String userid, String date) {
        showorhide(2, msgList.get(0).msgInfo);
        InterRetrofit interRetrofit = new LeafRequest().getXml(OPPLELTURL);
        Call<XmlDownData> ajax = interRetrofit.XmlDownData(LoginParam.getDBParam(userid, date));
        ajax.enqueue(new Callback<XmlDownData>() {
            @Override
            public void onResponse(Call<XmlDownData> call, Response<XmlDownData> response) {

                try {
                    JSONArray jsonArray = new JSONArray(response.body().clientTable);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Log.i("json", jsonObject.getString("ServerId"));
                        Dictionary dic = new Dictionary(null, jsonObject.getString("ServerId"), jsonObject.getString("DictId")
                                , jsonObject.getString("DictType")
                                , jsonObject.getString("DictClass")
                                , jsonObject.getString("DictName")
                                , jsonObject.getString("IsDel")
                                , jsonObject.getString("Remark")
                                , jsonObject.getString("IsLock")
                                , jsonObject.getString("DictValue")
                                , jsonObject.getString("ClientType")
                                , jsonObject.getString("FirstLevel")
                                , jsonObject.getString("INT1"));
                        dictionaryDao.insert(dic);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<XmlDownData> call, Throwable t) {
                showorhide(1, "");
                showToast("登录失败,请检查网络是否正常" + t);
            }
        });
    }

    // 集中点击事件
    @OnClick({R.id.tex_no_password, R.id.tex_register, R.id.weichat, R.id.weibo, R.id.message})
    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tex_no_password:// 忘记密码
                startActivity(new Intent(this, Login_Forget_Password.class));
                break;
            case R.id.tex_register:// 立即注册
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.weichat:// 微信授权登录
//                showToast("微信");
                // 授权测试OK
                // 开始进行微信授权登录操作
//                IWXAPI mApi = WXAPIFactory.createWXAPI(Login.this, WXEntryActivity.WEIXIN_APP_ID, true);
//                mApi.registerApp(WXEntryActivity.WEIXIN_APP_ID);
//
//                if (mApi != null && mApi.isWXAppInstalled()) {
//                    SendAuth.Req req = new SendAuth.Req();
//                    req.scope = "snsapi_userinfo";
//                    req.state = "wechat_sdk_demo_test_neng";
//                    mApi.sendReq(req);
//                } else {
//                    Toast.makeText(Login.this, "用户未安装微信", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.weibo:// 微博授权登录
                showToast("微博");
                break;
            case R.id.message:// 短信验证登录
                showToast("短信");
                break;
            default:
                break;
        }

    }

    /**
     * 集中配置显示与隐藏
     */
    public void showorhide(int code, String name) {
        switch (code) {
            case 0:
                login_relat.setVisibility(View.VISIBLE);
                login_line.setVisibility(View.GONE);
                login_line_bottom.setVisibility(View.GONE);
                break;
            case 1:
                login_relat.setVisibility(View.GONE);
                login_line.setVisibility(View.VISIBLE);
                login_line_bottom.setVisibility(View.VISIBLE);
                break;
            case 2:
//                login_relat.setVisibility(View.VISIBLE);
                load_name.setText("正在" + name);
                login_line.setVisibility(View.GONE);
                login_line_bottom.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    /**
     * 在android中点击EditText的时候会弹出软键盘，这时候如果想隐藏软键盘或者填完内容后点其他的地方直接隐藏软键盘，可以按一下方法处理
     * 首先获得软键盘Manager
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                et_account.setCursorVisible(false);
                et_password.setCursorVisible(false);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 触碰处理
     * 点击显示光标
     */
    private void touchAnalysis() {
        et_account.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    et_account.setCursorVisible(true);// 再次点击显示光标
                    et_account.setSelection(et_account.getText().toString().length());
                }
                return false;
            }
        });

        et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    et_password.setCursorVisible(true);// 再次点击显示光标
                    et_password.setSelection(et_password.getText().toString().length());
                }
                return false;
            }
        });
    }


    /**
     * 查看密码
     */
    @OnClick(R.id.iv_see_password)
    public void seepassword() {
        String password = et_password.getText().toString();
        if (!password.isEmpty()) {
            if (see == 0) {// 显示密码
                iv_see_password.setImageResource(R.drawable.image_password_bg);
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                see = 1;
            } else if (see == 1) {// 隐藏密码
                iv_see_password.setImageResource(R.mipmap.noout);
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                see = 0;
            }
        } else {
            showMidToast("请先输入密码再进行查看");
        }
    }

    /**
     * 提示再按一次退出
     */
    long temptime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {  // 2s内再次选择back键有效
            if (System.currentTimeMillis() - temptime > 2000) {
                showToast("再按一次退出应用");
                temptime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
