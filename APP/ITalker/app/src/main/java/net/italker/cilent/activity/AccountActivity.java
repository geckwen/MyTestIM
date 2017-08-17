package net.italker.cilent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


import net.common.BaseActivity;
import net.italker.cilent.R;
import net.italker.cilent.fragment.account.UpdateInfoFragment;
import net.italker.cilent.fragment.media.GalleyFragment;

/**
 * 登陆activity
 * Created by CLW on 2017/8/13.
 */

public class AccountActivity extends BaseActivity {
    private GalleyFragment mGalleyFragment;

    /**
     * 账户显示activity界面
     * @param context
     */
    public static void onShow(Context context)
    {
        context.startActivity(new Intent(context,AccountActivity.class));
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mGalleyFragment = new GalleyFragment();
       getSupportFragmentManager()
               .beginTransaction()
               .add(R.id.lay_container,mGalleyFragment)
               .commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mGalleyFragment.onActivityResult(requestCode,resultCode,data);
    }
}
