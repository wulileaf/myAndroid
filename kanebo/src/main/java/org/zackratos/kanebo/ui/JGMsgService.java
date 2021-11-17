package org.zackratos.kanebo.ui;

import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

// 极光官方的参考文档：https://docs.jiguang.cn/jpush/client/Android/android_api/#_106
public class JGMsgService extends JPushMessageReceiver {

    // 收到自定义消息回调
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        Log.i("JGTS", customMessage.toString());
    }

    // 收到通知回调
    // 接口收的消息格式
    // 2021-10-19 16:48:46.972 27958-28147/org.zackratos.kanebo I/JGTS: NotificationMessage{notificationId=456917820, msgId='2252053317385427', appkey='b08cec5018731087398986c1', notificationContent='星辰大海让人向往，令人流连', notificationAlertType=7, notificationTitle='你好世界', notificationSmallIcon='', notificationLargeIcon='', notificationExtras='{}', notificationStyle=0, notificationBuilderId=0, notificationBigText='', notificationBigPicPath='', notificationInbox='', notificationPriority=0, notificationCategory='', developerArg0='', platform=0, notificationChannelId='', displayForeground='', notificationType=0', inAppMsgType=1', inAppMsgShowType=2', inAppMsgShowPos=0', inAppMsgTitle=, inAppMsgContentBody=, inAppType=0}
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        Log.i("JGTS", notificationMessage.toString());
    }

    // 点击通知回调
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
    }
}
