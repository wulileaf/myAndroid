package org.zackratos.kanebo.broadcastHandle;

import android.content.IntentFilter;

public class BroadCastHandle {

    private IntentFilter intentFilter;

    // 网络变化监听
    public IntentFilter initNetworChanges() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        return intentFilter;
    }



}
