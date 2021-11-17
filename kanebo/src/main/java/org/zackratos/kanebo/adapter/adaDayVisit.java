package org.zackratos.kanebo.adapter;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.view.View;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.bean.B_Act_DayVisit;
import org.zackratos.kanebo.tools.Tools;

import java.util.List;


public class adaDayVisit extends SuperAdapter<B_Act_DayVisit> {

    private Context context;
    private Double lat, lng;

    public adaDayVisit(Context context, List<B_Act_DayVisit> items, int layoutResId, Double lat, Double lng) {
        super(context, items, layoutResId);
        this.context = context;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, B_Act_DayVisit item) {
        holder.setText(R.id.storename, item.getStorename());
        holder.setText(R.id.storecodevalue, item.getStorecode());
        holder.setText(R.id.isnewstorevalue, item.getIsnewstore());
        holder.setText(R.id.contactvalue, item.getContact());
        holder.setText(R.id.contacttelvalue, item.getContactTel());
        holder.setText(R.id.storelevelvalue, item.getStorelevelname());
        holder.setText(R.id.storetypevalue, item.getStoretypename());
        holder.setText(R.id.storeaddressvalue, item.getAddress());

        if (item.getLat() != 0.00 && item.getLng() != 0.00 && lng != null && lat != null) {
            Double dou = Tools.distance(item.getLng(), item.getLat(), lng, lat);
            // new DecimalFormat("0.00").format(dou) 保留两位小数 测试OK
            holder.setText(R.id.kmvalue, String.valueOf(new DecimalFormat("0.00").format(dou)));
        } else {
            holder.setText(R.id.kmvalue, "--");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(layoutPosition);// 这里是onClick方法的实现
                }
            }
        });

        /**
         * 定义点击item中间对象的事件
         * ps:点击的对象是item中的某个区域
         */
        holder.findViewById(R.id.lcation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regionClikListener != null) {
                    regionClikListener.onClick(layoutPosition);// 这里是onClick方法的实现
                }
            }
        });


    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    // 第二步， 写一个公共的方法
    // 其实在这步就实现了 void onClick(int position)方法 这里是实现
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 点击item中的某个区域
    public interface OnRegionClikListener {
        void onClick(int position);
    }

    private OnRegionClikListener regionClikListener;

    public void setOnRegionClikListener(OnRegionClikListener regionClikListener) {
        this.regionClikListener = regionClikListener;
    }


}
