package org.zackratos.kanebo.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.adapter.act_Main;
import org.zackratos.kanebo.adapter.atc_load_dwon;
import org.zackratos.kanebo.bean.b_act_main;
import org.zackratos.kanebo.bean.b_atc_load_dwon;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

// 下拉刷新
// 测试RecyclerView里面加载item图片能否根据Glide框架去获取
public class LoadDwon extends BaseActivity {

    private String[] name = {"门店1", "门店2",};
    private String[] image = {"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2717159859,8851985&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2501580659,2062657415&fm=26&gp=0.jpg",};
    private View imgEntryView;
    private AlertDialog dialog;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rec_Message)
    RecyclerView rec_Message;


    @Override
    protected int initView() {
        return R.layout.atc_load_down;
    }

    @Override
    protected void initData() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


        List<b_atc_load_dwon> list = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            b_atc_load_dwon bActMain = new b_atc_load_dwon();
            bActMain.setName(name[i]);
            bActMain.setImg(image[i]);
            list.add(bActMain);
        }
        atc_load_dwon actMain = new atc_load_dwon(this, list, R.layout.item_atc_load_dwon);
        // new LinearLayoutManager(this) 正常的item
        // new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)) 左右可以拖动
        // new GridLayoutManager(this, 3) 瀑布流
        rec_Message.setLayoutManager(new LinearLayoutManager(this));// RecyclerView需要配置，ListView不需要
        rec_Message.setAdapter(actMain);


        /**
         * 点击item中的某个区域
         * res:测试OK
         * ps:次点击事件，优先于短按点击事件响应
         */
        actMain.setOnRegionClikListener(new act_Main.OnRegionClikListener() {
            @Override
            public void onClick(int position) {
//                Toast.makeText(LoadDwon.this, "region click " + position, Toast.LENGTH_SHORT).show();
                showpoto(position);
            }
        });
    }

    // 单个图片点击放大查看
    public void showpoto(int lag) {
        LayoutInflater inflater = LayoutInflater.from(LoadDwon.this);
        imgEntryView = inflater.inflate(R.layout.photo_show, null);
        dialog = new AlertDialog.Builder(LoadDwon.this).create();
        ImageView img = imgEntryView.findViewById(R.id.large_image);
        // Glide加载网络图片
        Glide.with(LoadDwon.this)
                .load(image[lag])
                .into(img);
        dialog.setView(imgEntryView); // 自定义dialog
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        // 点击大图关闭dialog,不添加这行代码，只能点击图片以外的区域关闭图片查看
//        imgEntryView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View paramView) {
//                dialog.cancel();
//            }
//        });

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
