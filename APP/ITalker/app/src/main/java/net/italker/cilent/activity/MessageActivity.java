package net.italker.cilent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;

import net.common.app.BaseActivity;
import net.common.app.BaseFragment;
import net.common.model.Author;
import net.factory.model.db.Group;
import net.factory.model.db.Message;
import net.factory.model.db.Session;
import net.italker.cilent.R;
import net.italker.cilent.fragment.message.ChatGroupFragment;
import net.italker.cilent.fragment.message.ChatUserFragment;

import org.w3c.dom.Text;

public class MessageActivity extends BaseActivity {
    public static final String KEY_RECEIVER_ID ="KEY_RECEIVER_ID";
    public static final String KEY_RECEIVER_IS_GROUP ="KEY_RECEIVER_IS_GROUP";
    private String receiverId;
    private boolean isGroup;

    /**
     * Message显示
     * @param context 上下文
     * @param author 用户信息
     */
    public static void show(Context context, Author author)
    {
        if(context==null||author==null|| TextUtils.isEmpty(author.getId()))
            return;
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID,author.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP,false);
        context.startActivity(intent);
    }



    /**
     * Message显示
     * @param context 上下文
     * @param group 用户信息
     */
    public static void show(Context context, Group group)
    {
        if(context==null||group==null|| TextUtils.isEmpty(group.getId()))
        return;
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID,group.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP,true);
        context.startActivity(intent);
    }

    public static void show(Context context, Session data) {

        if(context==null||data==null|| TextUtils.isEmpty(data.getId()))
            return;
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID,data.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP,data.getReceiverType()== Message.RECEIVER_TYPE_GROUP);
        context.startActivity(intent);

    }

    @Override
    protected boolean initArgs(Bundle bundle) {

       receiverId = bundle.getString(KEY_RECEIVER_ID);
        isGroup = bundle.getBoolean(KEY_RECEIVER_IS_GROUP);
        return !TextUtils.isEmpty(receiverId);
    }

    @Override
    public int getContentLayoutId()
    {
        return R.layout.activity_message;
    }


    @Override
    public void initWidget() {
        super.initWidget();
        BaseFragment fragment;
        if(!isGroup){
            fragment = new ChatUserFragment();
        }else {
            fragment=new ChatGroupFragment();
        }
        Bundle bundle =new Bundle();
        bundle.putString(KEY_RECEIVER_ID,receiverId);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container,fragment).commit();
    }


}
