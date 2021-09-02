package org.zackratos.basemode.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
//import android.support.annotation.IdRes;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.zackratos.basemode.R;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @Date 2017/8/15
 * @author leaf
 * @version 1.0
 * @Note 基础Fragment类
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment {

    protected View mRootView;
    protected P mPresenter;
    private Unbinder mUnbinder;
    private boolean useEventBus = false;
    private Dialog dialog;// 添加提示框
    public Activity mActivity;
    public TextView tv_titleContent, tv_titleback, tv_titleright;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView(inflater, container);
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (useEventBus) {
//            EventBus.getDefault().register(this);//注册eventbus
        }
        initData();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = getPresenter();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();//解绑butterknife
        }
        if (useEventBus) {
//            EventBus.getDefault().unregister(this);//解除注册eventbus
        }
        this.mPresenter = null;
        this.mRootView = null;
        this.mUnbinder = null;
    }

    /**
     * 是否使用eventBus,默认为使用(false)，
     *
     * @return
     */
    protected void useEventBus(boolean useEventBus) {
        this.useEventBus = useEventBus;
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initData();

    protected abstract P getPresenter();

    /**
     * 显示ProgressDialog
     */
    public void showProgressDialog(String message, Boolean yrn) {
        dialog = CustomProgressDialog.createLoadingDialog(mActivity, message);
        dialog.setCancelable(yrn);
        dialog.show();
    }

    /**
     * 关闭ProgressDialog
     */
    public void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 吐司提示
     */
    public void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐司提示
     * 显示位置在页面的底部
     */
    public void showBottomToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐司提示
     * 显示位置在页面的中间位置
     */
    public void showMidToast(String msg) {
        Toast toast = Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 吐司提示
     * 显示图片加文字的提示方式
     * content:要显示的文字
     * color：要显示文字的颜色
     * mipmap：图片
     * dt: 图示显示的时间长短
     */
    public void showPhotoToast(String content, int color, int mipmap, int dt) {
        Toast toast = new Toast(mActivity);
        toast.setDuration(dt);
        toast.setGravity(Gravity.CENTER, 0, 0);// 后面的参数是标识吐司提示在页面的位置

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
        LayoutInflater inflater = mActivity.getLayoutInflater();
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

    /**
     * 布局切换  ok
     */
    protected void replace(@IdRes int fragmentId, Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .commit();
    }


    protected void replaceWithStack(@IdRes int fragmentId, Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * 公共的头部
     */
    public void initTitleBar() {
        tv_titleContent = (TextView) getActivity().findViewById(R.id.title_content);
        tv_titleright = (TextView) getActivity().findViewById(R.id.title_right);
        tv_titleback = (TextView) getActivity().findViewById(R.id.title_back);
        tv_titleback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getActivity().finish();
            }
        });
    }
}
