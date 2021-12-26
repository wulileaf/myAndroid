package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Toast;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.adapter.act_Main;
import org.zackratos.kanebo.bean.b_act_main;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;


// 主页面
public class Main extends BaseActivity {

    private String[] shwoTextPage = {"当日拜访", "图片选择", "图片拍照", "基础控件", "数据库", "个人中心", "下拉刷新", "高德地图", "滑轮展示", "百度地图", "扫码展示", "连接蓝牙",
            "身份识别", "表格展示", "视频展示", "文件下载", "自定义View", "测试View", "wifi", "socket", "沉浸式页面"};
    private int[] shwoImgPage = {R.mipmap.inmd, R.mipmap.jppz, R.mipmap.plan, R.mipmap.bgimg, R.mipmap.database, R.mipmap.my, R.mipmap.dropdown, R.mipmap.gaodemap,
            R.mipmap.huadong, R.mipmap.baidumap, R.mipmap.saoma, R.mipmap.lanya, R.mipmap.sfz, R.mipmap.table, R.mipmap.video, R.mipmap.loadingfile, R.mipmap.custom,
            R.mipmap.test, R.mipmap.wifi, R.mipmap.lianjie, R.mipmap.yemain};
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

    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate();
        return true;
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
        // GridLayoutManager网格
        // LinearLayoutManager
        // StaggeredGridLayoutManager瀑布流
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
                    case 1:// 图片选择
                        startActivity(new Intent(Main.this, PhotoGraph.class));
                        break;
                    case 2:// 图片拍摄
                        startActivity(new Intent(Main.this, SelectPicture.class));
                        break;
                    case 3:// 基础控件
                        startActivity(new Intent(Main.this, BaseControl.class));
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
                    case 8:// 滑轮展示
                        startActivity(new Intent(Main.this, Rotation.class));
                        break;
                    case 9:// 百度地图
                        startActivity(new Intent(Main.this, BaiDuMapView.class));
                        break;
                    case 10:// 扫码展示
                        startActivity(new Intent(Main.this, ScanCode.class));
                        break;
                    case 11:// 连接蓝牙
                        startActivity(new Intent(Main.this, BluetoothConnect.class));
                        break;
                    case 12:// 身份识别
                        startActivity(new Intent(Main.this, ORC.class));
                        break;
                    case 13:// 表格展示
                        startActivity(new Intent(Main.this, Table.class));
                        break;
                    case 14:// 视频展示
                        startActivity(new Intent(Main.this, Video.class));
                        break;
                    case 15:// 文件下载
                        startActivity(new Intent(Main.this, LoadingFile.class));
                        break;
                    case 16:// 自定义View
                        startActivity(new Intent(Main.this, CustomView.class));
                        break;
                    case 17:// 测试View
                        startActivity(new Intent(Main.this, TestCusView.class));
                        break;
                    case 18:// wifi
                        startActivity(new Intent(Main.this, WIFIView.class));
                        break;
                    case 19:// socket
                        startActivity(new Intent(Main.this, SocketView.class));
                        break;
                    case 20:// 沉浸式页面
                        startActivity(new Intent(Main.this, ImmHeaderView.class));
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
