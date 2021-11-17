package org.zackratos.kanebo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.bean.b_act_main;

import java.util.List;

// 重要文件
public class act_Main extends SuperAdapter<b_act_main> {

    private Context context;

    public act_Main(Context context, List<b_act_main> items, int layoutResId) {
        super(context, items, layoutResId);
            this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, b_act_main item) {
        holder.setText(R.id.item_name, item.getName());// 赋值
        holder.setImageResource(R.id.icon_img_set, item.getImg());

        /**
         * 定义短按点击事件
         * ps:点击的对象是真个item
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 这里的onClick是系统提供的接口
                if (listener != null) {
                    listener.onClick(layoutPosition);// 这里是自己写的onClick方法的实现
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

        // 1
//        String check = judge(param);

        // 2
//        EditText pnumber = holder.findViewById(R.id.product_number);
//        EditText pprice = holder.findViewById(R.id.product_price);
//        if (pnumber.getText().toString().equals("")) {// 销售数量
//            holder.setText(R.id.product_number, item.getProductNumber());
//        } else {
//            holder.setText(R.id.product_number, pnumber.getText());
//        }
//        if (pprice.getText().toString().equals("")) {// 销售价格
//            holder.setText(R.id.product_price, item.getProductPrice());
//        } else {
//            holder.setText(R.id.product_price, pprice.getText());
//        }

        // 3
        // 适配
        // check选择
//        if (item.isCheck()) {
//            holder.setImageDrawable(R.id.img_item_prompt, context.getResources().getDrawable(R.drawable.ic_action_checked));
//        } else {
//            holder.setImageDrawable(R.id.img_item_prompt, context.getResources().getDrawable(R.drawable.ic_action_unchecked));
//        }

//        // 调整详情
//        TextView textView = holder.findViewById(R.id.deta_show);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                Intent intent = new Intent(context, ViewDetail.class);
//                bundle.putSerializable("list", item);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
//        });

    }

    // 第一步 定义短按接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    // 第二步， 写一个公共的方法
    // 其实在这步就实现了 void onClick(int position)方法 这里是实现
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 定义长按接口
    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    private OnItemLongClickListener longClickListener;

    // 第二步， 写一个公共的方法
    // 其实在这步就实现了 void onClick(int position)方法 这里是实现
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    // 点击item中的某个控件
    public interface OnRegionClikListener {
        void onClick(int position);
    }

    private OnRegionClikListener regionClikListener;

    public void setOnRegionClikListener(OnRegionClikListener regionClikListener) {
        this.regionClikListener = regionClikListener;
    }


    /**
     * 判断审核状态
     */
    private String judge(int param) {
        String backparam = null;
        switch (param) {
            case 0:
                backparam = "待审核";
                break;
            case 1:
                backparam = "审核通过";
                break;
            case 2:
                backparam = "审核未通过";
                break;
        }
        return backparam;
    }


}
