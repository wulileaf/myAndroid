package org.zackratos.kanebo.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONObject;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


// 拍照基于Matisse框架
// 图片展示基于Glide
public class PhotoGraph extends BaseActivity {

    final int REQUEST_CODE_CHOOSE = 001;
    // ps：RxPermissions需要配合rxJava使用
    final RxPermissions rxPermissions = new RxPermissions(PhotoGraph.this);
    private View imgEntryView;
    private AlertDialog dialog;


    // 权限组
    private static final String[] permissionsGroup = new String[]{
            Manifest.permission.CAMERA
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE};
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.btn_img)
    Button btn_img;
    private Uri uri;

    @Override
    protected int initView() {
        return R.layout.atc_photograph;
    }

    // Android6.0
    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT >= 23) {
            initPermission();
        }

        // Glide加载网络图片
        Glide.with(this)
                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fc%2F5472c8f36db64.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616468056&t=0c15ce8a78dde77464dd889f1a89c200")
                .into(img);

        // 点击查看图片
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpoto();
            }
        });


    }

    @OnClick({R.id.btn_img})
    public void check(View view) {
        switch (view.getId()) {
            case R.id.btn_img:
                paizhao();
                break;
            default:
                break;
        }
    }

    // 单个图片点击放大查看
    public void showpoto(){
        LayoutInflater inflater = LayoutInflater.from(PhotoGraph.this);
        imgEntryView = inflater.inflate(R.layout.photo_show, null);
        dialog = new AlertDialog.Builder(PhotoGraph.this).create();
        ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
        // Glide加载网络图片
        Glide.with(PhotoGraph.this)
                .load(uri)
                .into(img);
        dialog.setView(imgEntryView); // 自定义dialog
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        // 点击大图关闭dialog
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                dialog.cancel();
            }
        });
    }


    // 拍照以及选择图片
    public void paizhao() {
        Matisse.from(PhotoGraph.this)
                .choose(MimeType.ofAll())//选择视频和图片
//                .choose(MimeType.ofImage())//选择图片
//                .choose(MimeType.ofVideo())//选择视频
//                .choose(MimeType.of(MimeType.JPEG,MimeType.AVI))//自定义选择选择的类型
                //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
//                .showSingleMediaType(true)
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                // 使用相机，和 captureStrategy 一起使用
                .capture(true)// 是否提供拍照功能
                .captureStrategy(new CaptureStrategy(true, "org.zackratos.kanebo.fileprovider"))// 存储到哪里
                //有序选择图片 123456...
                .countable(true)
                //最大选择数量为9
                .maxSelectable(9)
                //选择方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //界面中缩略图的质量
                .thumbnailScale(0.8f)
                //蓝色主题
                .theme(R.style.Matisse_Zhihu)
                //黑色主题
//                .theme(R.style.Matisse_Dracula)
                //Glide加载方式
                .imageEngine(new GlideEngine())
                //Picasso加载方式
//                .imageEngine(new PicassoEngine())
                //请求码
                .forResult(REQUEST_CODE_CHOOSE);
    }

    // 选择图片后的返回
    // Image的setImageURI里面的参数：本地地址显示正常，
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 001 && resultCode == RESULT_OK) {
            //图片路径 同样视频地址也是这个 根据requestCode
            List<Uri> pathList = Matisse.obtainResult(data);
            for (int i = 0; i < pathList.size(); i++) {
                uri = pathList.get(i);
                Log.i("wx", uri.getPath());
//                img.setImageURI(uri);// 测试OK
                Glide.with(PhotoGraph.this)// Glide加载本地图片OK
                        .load(uri)
                        .into(img);
            }
        }
    }

    // 权限处理
    private void initPermission() {
//        RxPermissions rxPermissions=new RxPermissions(this);
        rxPermissions.request(permissionsGroup)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(PhotoGraph.this, "已获取权限", Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            //只有用户拒绝开启权限，且选了不再提示时，才会走这里，否则会一直请求开启
                            Toast.makeText(PhotoGraph.this, "去设置打开权限", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }


}
