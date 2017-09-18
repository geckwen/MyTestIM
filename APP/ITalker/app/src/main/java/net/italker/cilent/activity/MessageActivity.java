package net.italker.cilent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import net.common.app.BaseActivity;
import net.common.model.Author;
import net.italker.cilent.R;

public class MessageActivity extends BaseActivity {

    /**
     * Message显示
     * @param context 上下文
     * @param author 用户信息
     */
    public static void show(Context context, Author author)
    {
        context.startActivity(new Intent(context,MessageActivity.class));
    }

    @Override
    public int getContentLayoutId()
    {
        return R.layout.activity_message;
    }
}
