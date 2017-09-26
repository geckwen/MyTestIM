package net.italker.cilent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igexin.sdk.PushConsts;

import net.factory.present.Factory;
import net.factory.data.Helper.AcccountHelper;
import net.factory.persistence.Account;

/**
 * 接收器
 * Created by CLW on 2017/9/5.
 */

public class MessagerReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null)
            return;
        //获取信息
        Bundle bundle = intent.getExtras();
        //判断当前信息的意图
        switch (bundle.getInt(PushConsts.CMD_ACTION))
        {
            case PushConsts.GET_CLIENTID:
                //当ID初始化时候
                //获取设备ID
                onCilentInt(bundle.getString("cilentId"));
                break;
            case  PushConsts.GET_MSG_DATA:
                //常规信息传递时候
                byte[] payload = bundle.getByteArray("payload");
                if(payload != null)
                {
                    String message = new String(payload);
                    onMessageArrived(message);
                }
                break;
            default:
                break;
        }
    }

    /**当ID设备初始化的时候
     * @param cId 设备ID
     */
    public void onCilentInt(String cid)
    {
        Account.setPushId(cid);
        if(Account.isAccount())
        {
            //账户登录，进行pushID绑定
            AcccountHelper.bindPush(null);
        }
    }

    /**
     * 消息到达时候
     * @param message 消息
     */
    public void onMessageArrived(String message)
    {
        Factory.disPatcherMessage(message);
    }
}
