package org.zackratos.basemode.adapter;

import android.content.Context;
import android.view.View;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.zackratos.basemode.R;
import org.zackratos.basemode.bean.PopupwindowBean;

import java.util.List;

public class PopupwindowList extends SuperAdapter<PopupwindowBean> {

    private Context context;

    public PopupwindowList(Context context, List<PopupwindowBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PopupwindowBean item) {
        holder.setText(R.id.name, item.getName());

        holder.findViewById(R.id.pop_bean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(layoutPosition, item);// 这里是onClick方法的实现
                }
            }
        });
    }


    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position, PopupwindowBean popupwindowBean);
    }

    // 第二步， 写一个公共的方法
    // 其实在这步就实现了 void onClick(int position)方法 这里是实现
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
