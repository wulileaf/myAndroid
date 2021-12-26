package org.zackratos.kanebo.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class Rotation extends BaseActivity implements OnBannerListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.banner)
    Banner banner;
    List<?> images = new ArrayList<>();


    @Override
    protected int initView() {
        return R.layout.atc_rotation;
    }

    @Override
    protected void initData() {
        initTitleBar();
//        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
//                .setAdapter(new BannerExampleAdapter(DataBean.getTestData()))
//                .setIndicator(new CircleIndicator(this));


//        banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData3()) {
//            @Override
//            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
//                //图片加载自己实现
//                Glide.with(holder.itemView)
//                        .load(data.imageUrl)
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                        .into(holder.imageView);
//            }
//        })
//                .addBannerLifecycleObserver(this)//添加生命周期观察者
//                .setIndicator(new CircleIndicator(this));

        //2.1.0以前jcenter的依赖
//         轮播
//         获取网络图片OK
        String[] urls = getResources().getStringArray(R.array.url);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);
        banner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(Rotation.this)
                .start();
        banner.setBannerAnimation(BackgroundToForegroundTransformer.class);

    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
