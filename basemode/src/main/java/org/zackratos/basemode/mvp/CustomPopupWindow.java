package org.zackratos.basemode.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
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

    private Button btnTakePhoto, btnSelect, btnCancel;
    private View mPopView;
    //    private OnItemClickListener mListener;
    public RecyclerView recyclerView;
    public PopupwindowList popupwindowList;

    public CustomPopupWindow(Context context) {
        super(context);
        init(context);// 初始化布局
        setPopupWindow();// 设置窗口的相关属性
        // 设置点击属性
//        btnTakePhoto.setOnClickListener(this);
//        btnSelect.setOnClickListener(this);
//        btnCancel.setOnClickListener(this);
    }

    // 初始化布局
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.act_popupwindow_list, null);
        recyclerView = mPopView.findViewById(R.id.rec_List);

        // 如何查询数据
        // 获取数据
        List<PopupwindowBean> list = new ArrayList<>();
        PopupwindowBean bean = new PopupwindowBean();
        bean.setName("安徽办事处");
        list.add(bean);
        PopupwindowBean bean1 = new PopupwindowBean();
        bean1.setName("北京办事处");
        list.add(bean1);


        // list是数据
        // 参数三：布局
        popupwindowList = new PopupwindowList(context, list, R.layout.item_popupwindowbean);
        // new LinearLayoutManager(this) 正常的item
        // new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)) 左右可以拖动
        // new GridLayoutManager(this, 3) 瀑布流
        recyclerView.setLayoutManager(new LinearLayoutManager(context));// RecyclerView需要配置，ListView不需要
        recyclerView.setAdapter(popupwindowList);

//        popupwindowList.setOnItemClickListener(new PopupwindowList.OnItemClickListener() {
//            @Override
//            public void onClick(int position, PopupwindowBean popupwindowBean) {
//                Log.i("bsweizhi", popupwindowBean + "-------66666---------" + position);
//            }
//        });

//        recyclerView.setOnClickListener(this);

        // 点击事件如何传递???
//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


//        btnTakePhoto = (Button) mPopView.findViewById(R.id.id_btn_take_photo);
//        btnSelect = (Button) mPopView.findViewById(R.id.id_btn_select);
//        btnCancel = (Button) mPopView.findViewById(R.id.id_btn_cancelo);
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.rec_List).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
//    public interface OnItemClickListener {
//        void setOnItemClick(View v);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mListener = listener;
//    }
//
//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        if (mListener != null) {
//            mListener.setOnItemClick(v);
//        }
//    }
}
