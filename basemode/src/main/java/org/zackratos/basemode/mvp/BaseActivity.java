package org.zackratos.basemode.mvp;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.basemode.R;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.net.ConnectivityManager.TYPE_WIFI;
import static android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;


/**
 * @author leaf
 * @version 1.0
 * @date 2017/8/15
 * @note 基础Activity类
 */

// Android生命周期：https://www.jianshu.com/p/8de5562b65fe
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity {

    protected P mPresenter;
    private Dialog dialog;// 添加提示框
    private Unbinder mUnbinder;// 注解
    private boolean useEventBus = false;// EventBus
    public TextView tv_titleContent, tv_titleright;// 公共头部
    public ImageView tv_titleback;
    private Bundle state = null;
    private int netType = 0;
    private LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = savedInstanceState;
        setContentView(initView());

//        if (isDebug()) {           // 这两行必须写在init之前,否则这些配置在init过程中将无效
//            ARouter.openLog();     // 打印日志
//            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行,必须开启调试模式线上版本需要关闭,否则有安全风险)
//        }
//        ARouter.init(getApplication()); // 尽可能早,推荐在Application中初始化

        BaseActivityCollector.getInstance().addActivity(this);// 初始化Activity管理类,方便销毁
        mUnbinder = ButterKnife.bind(this);// 绑定Activity
        mPresenter = getPresenter();
        initData();
        if (useEventBus) {
            EventBus.getDefault().register(this);// 注册EventBus
        }
        getLocation();
    }

    // 初始化布局
    protected abstract int initView();

    // 初始化数据
    protected abstract void initData();

    // 是否除去手机状态栏
    public Bundle savedInstanceState() {
        return state;
    }

    // 显示ProgressDialog
    public void showProgressDialog(String message, Boolean yrn) {
        dialog = CustomProgressDialog.createLoadingDialog(this, message);
        dialog.setCancelable(yrn);
        dialog.show();
    }

    // 关闭ProgressDialog
    public void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    // 吐司提示
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // 吐司提示显示位置在页面的中间位置
    public void showMidToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    // 吐司提示显示图片加文字的提示方式    mipmap图片大小(>70*70) 后面的参数是标识吐司提示在页面的位置
    public void showPhotoToast(String content, int color, int mipmap, int dt) {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(dt);
        toast.setGravity(Gravity.CENTER, 0, 0);

//==========================================1==============================================================
//        LinearLayout toastView = (LinearLayout) toast.getView();
//        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//        v.setTextColor(Color.WHITE);
//        v.setTextSize(18);
//        ImageView imageCodeProject = new ImageView(getApplicationContext());
//        imageCodeProject.setImageResource(R.mipmap.s);
//        toastView.addView(imageCodeProject, 0);
//        toast.show();
//==========================================1==============================================================

//==========================================2==============================================================
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_custom, null);
        TextView text1 = (TextView) view.findViewById(R.id.ez);
        text1.setText(content);
        text1.setTextColor(getResources().getColor(color));
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        imageView.setImageResource(mipmap);// mipmap图片大小(>70*70)
        toast.setView(view);
        toast.show();
//==========================================2==============================================================
    }

    // 弹出式提示框
    public void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", null).show();
    }

    // onDestroy方法执行的操作-解除注解 关闭这个Activity管理类 释放资源 解除注册EventBus
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivityCollector.removeActivity(this);
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        if (useEventBus) {
            EventBus.getDefault().unregister(this);
        }
        this.mUnbinder = null;
    }

    // 是否使用EventBus，默认为使用(false)
    protected void useEventBus(boolean useEventBus) {
        this.useEventBus = useEventBus;
    }

    // 公共的头部
    public void initTitleBar() {
        tv_titleContent = (TextView) findViewById(R.id.title_content);
        tv_titleright = (TextView) findViewById(R.id.title_right);
        tv_titleback = (ImageView) findViewById(R.id.title_back);
        tv_titleback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    protected abstract P getPresenter();

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = getPresenter();
        }
    }

    // 布局切换
    protected void replace(@IdRes int fragmentId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .commit();
    }

    protected void replaceWithStack(@IdRes int fragmentId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .addToBackStack(null)
                .commit();
    }

    // 跳转
    protected abstract Intent mainIntent(Context context);

    // 监听网络变化
    public class NetworkChangReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                int network = new BaseSp(context).getInt("NetWork");// 先获取
                new BaseSp(context).saveNetWork(networkInfo.getType());// 在保存本次获取到的
                if (network != networkInfo.getType()) {
                    switch (networkInfo.getType()) {
                        case TYPE_MOBILE:
                            Toast.makeText(context, "正在使用移动网络", Toast.LENGTH_SHORT).show();
                            // 在由网络到有网络的时候
                            // 发送广播
                            break;
                        case TYPE_WIFI:
                            Toast.makeText(context, "正在使用wifi网络", Toast.LENGTH_SHORT).show();
                            // 在网络由网络到有网络的时候
                            // 发送广播
                            break;
                        default:
                            break;
                    }
                }
            } else {
                new BaseSp(context).saveNetWork(0);// 在保存本次获取到的
                Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 获取手机当前网络环境
    public int getNetWork() {
        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            netType = networkInfo.getType();
        } else {
            showToast("当前无网络连接");
        }
        return netType;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    // 获取定位信息
    public LocationManager getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度，中精度高精度获取不到location。
        criteria.setAltitudeRequired(false);//不要求海拔
        criteria.setBearingRequired(false);//不要求方位
        criteria.setCostAllowed(true);//允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

        String locationProvider = locationManager.getBestProvider(criteria, true);

        return locationManager;
    }

    // 获取定位接口
//    protected abstract void upLocationData(LocationManager locationManager);

}
