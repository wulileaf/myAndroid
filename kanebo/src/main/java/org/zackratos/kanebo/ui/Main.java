package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.adapter.act_Main;
import org.zackratos.kanebo.bean.b_act_main;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


// 主页面
public class Main extends BaseActivity {

    private String[] shwoTextPage = {"当日拜访", "门店拍照", "计划设定", "导购考勤", "数据库", "个人中心", "下拉刷新", "高德地图"};
    private int[] shwoImgPage = {R.mipmap.inmd, R.mipmap.jppz, R.mipmap.plan, R.mipmap.bgimg, R.mipmap.database, R.mipmap.my, R.mipmap.dropdown, R.mipmap.map};

    @BindView(R.id.rec_Message)
    RecyclerView rec_Message;

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initAdapter();
    }


    private void initAdapter() {

        List<b_act_main> list = new ArrayList<>();
        for (int i = 0; i < shwoTextPage.length; i++) {
            b_act_main bActMain = new b_act_main();
            bActMain.setName(shwoTextPage[i]);
            bActMain.setImg(shwoImgPage[i]);
            list.add(bActMain);
        }
        act_Main actMain = new act_Main(this, list, R.layout.item_act_main);
        rec_Message.setLayoutManager(new GridLayoutManager(this, 3));// RecyclerView需要配置，ListView不需要
        rec_Message.setAdapter(actMain);

        /**
         * 短按点击事件
         * res:测试OK
         * ps:次点击事件，滞后于短按点击事件响应
         */
        actMain.setOnItemClickListener(new act_Main.OnItemClickListener() {
            @Override
            public void onClick(int position) {

//                Toast.makeText(Main.this, "click " + position, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:// 当日拜访
//                        showMidToast(shwoTextPage[position] + "");
                        startActivity(new Intent(Main.this, DayVisit.class));
                        break;
                    case 1:// 门店拍照
                        startActivity(new Intent(Main.this, PhotoGraph.class));
                        break;
                    case 2:// 计划设定

                        break;
                    case 3:// 导购考勤

                        break;
                    case 4:// 数据库操作
                        startActivity(new Intent(Main.this, DB.class));
                        break;
                    case 5:// 个人中心
                        startActivity(new Intent(Main.this, PersonalCenter.class));
                        break;
                    case 6:// 下拉刷新
                        startActivity(new Intent(Main.this, LoadDwon.class));
                        break;
                    case 7:// 高德地图
                        startActivity(new Intent(Main.this, GaoDeMap.class));
                        break;
                    default:
                        break;
                }
            }
        });

        /**
         * 长按点击事件
         * res:测试OK
         */
        actMain.setOnItemLongClickListener(new act_Main.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(Main.this, "long click " + position, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 点击item中的某个区域
         * res:测试OK
         * ps:次点击事件，优先于短按点击事件响应
         */
//        actMain.setOnRegionClikListener(new act_Main.OnRegionClikListener() {
//            @Override
//            public void onClick(int position) {
//                Toast.makeText(Main.this, "region click " + position, Toast.LENGTH_SHORT).show();
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
