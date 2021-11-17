package org.zackratos.basemode.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import org.zackratos.basemode.R;
import org.zackratos.basemode.adapter.PopupwindowList;
import org.zackratos.basemode.bean.PopupwindowBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author leaf
 * @version 1.0
 * @Date 2017/9/26
 * @Note 自定义底部弹出框, 主要对应拍照或者相册
 * @PS 2021/6/27 修改呈现逻辑
 */
public class CustomPopupWindow extends PopupWindow {

    private View mPopView;
    public RecyclerView recyclerView;
    public PopupwindowList popupwindowList;

    public CustomPopupWindow(Context context, List<PopupwindowBean> list) {
        super(context);
        init(context, list);// 初始化布局
        setPopupWindow();// 设置窗口的相关属性
    }

    // 初始化布局
    private void init(Context context, List<PopupwindowBean> list) {
        LayoutInflater inflater = LayoutInflater.from(context);

        //绑定布局
        mPopView = inflater.inflate(R.layout.act_popupwindow_list, null);
        recyclerView = mPopView.findViewById(R.id.rec_List);

        // list是数据
        // 参数三：布局
        popupwindowList = new PopupwindowList(context, list, R.layout.item_popupwindowbean);
        // new LinearLayoutManager(this) 正常的item
        // new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)) 左右可以拖动
        // new GridLayoutManager(this, 3) 瀑布流
        recyclerView.setLayoutManager(new LinearLayoutManager(context));// RecyclerView需要配置，ListView不需要
        recyclerView.setAdapter(popupwindowList);
    }

    // 设置弹出窗口的相关属性
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置弹出框的View
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x80808080));// 设置背景透明

        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果点击位置在窗口外面则销毁
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.con_Rec_List).getTop();// 获取窗口的高度
                int y = (int) event.getY();// 获取整个页面的高度
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

}
