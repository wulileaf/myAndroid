package org.zackratos.kanebo.adapter;

import android.content.Context;
import android.view.View;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.bean.BBluetoothConnect;

import java.util.List;

import androidx.annotation.Nullable;

public class ADBluetoothConnect extends SuperAdapter<BBluetoothConnect> {
    private Context context;

    public ADBluetoothConnect(Context context, List<BBluetoothConnect> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, BBluetoothConnect item) {
        holder.setText(R.id.eqNameValue, item.getEqName());
        holder.setText(R.id.eqAddressValue, item.getEqAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(layoutPosition);// 这里是onClick方法的实现
                }
            }
        });
    }

    // 点击整个item
    public itemClick listener;
    public interface itemClick {
        void onClick(int position);
    }
    public void setOnItemClickListener(itemClick listener) {
        this.listener = listener;
    }
}
