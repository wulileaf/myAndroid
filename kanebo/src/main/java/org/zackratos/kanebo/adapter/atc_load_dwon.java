package org.zackratos.kanebo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.bean.b_atc_load_dwon;

import java.util.List;

// LoadDwon对于的适配器
public class atc_load_dwon extends SuperAdapter<b_atc_load_dwon> {

    private Context context;

    public atc_load_dwon(Context context, List<b_atc_load_dwon> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, b_atc_load_dwon item) {
        holder.setText(R.id.item_name, item.getName());// 赋值
//        holder.setImageResource(R.id.icon_img_set, item.getImg());

        // 测试Glide加载OK
        ImageView img = holder.findViewById(R.id.icon_img_set);
        Glide.with(context).load(item.getImg()).into(img);

        /**
         * 定义短按点击事件
         * ps:点击的对象是真个item
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 这里的onClick是系统提供的接口
                if (listener != null) {
                    listener.onClick(layoutPosition);// 这里是onClick方法的实现
                }
            }
        });

        /**
         * 定义长按点击事件
         * ps:点击的对象是真个item
         */
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {// 这里的onLongClick是系统提供的接口
                if (longClickListener != null) {
                    longClickListener.onClick(layoutPosition);// 这里是onClick方法的实现
                }
                return true;
            }
        });

        /**
         * 定义点击item中间对象的事件
         * ps:点击的对象是item中的某个区域
         */
        holder.findViewById(R.id.item_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regionClikListener != null) {
                    regionClikListener.onClick(layoutPosition);// 这里是onClick方法的实现
                }
            }
        });

        holder.findViewById(R.id.icon_img_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regionClikListener != null) {
                    regionClikListener.onClick(layoutPosition);// 这里是onClick方法的实现
                }
            }
        });
    }


    // 第一步 定义短按接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private act_Main.OnItemClickListener listener;

    // 第二步， 写一个公共的方法
    // 其实在这步就实现了 void onClick(int position)方法 这里是实现
    public void setOnItemClickListener(act_Main.OnItemClickListener listener) {
        this.listener = listener;
    }

    // 定义长按接口
    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    private act_Main.OnItemLongClickListener longClickListener;

    // 第二步， 写一个公共的方法
    // 其实在这步就实现了 void onClick(int position)方法 这里是实现
    public void setOnItemLongClickListener(act_Main.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public interface OnRegionClikListener {
        void onClick(int position);
    }

    private act_Main.OnRegionClikListener regionClikListener;

    public void setOnRegionClikListener(act_Main.OnRegionClikListener regionClikListener) {
        this.regionClikListener = regionClikListener;
    }


}
