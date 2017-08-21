package net.italker.cilent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


import net.common.BaseActivity;
import net.common.BaseFragment;
import net.italker.cilent.R;
import net.italker.cilent.fragment.account.UpdateInfoFragment;
import net.italker.cilent.fragment.media.GalleyFragment;

/**
 * 登陆activity
 * Created by CLW on 2017/8/13.
 */

public class AccountActivity extends BaseActivity {
    private  BaseFragment mUpdateInfoFragment;

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
        mUpdateInfoFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
               .beginTransaction()
               .add(R.id.lay_container,mUpdateInfoFragment)
               .commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mUpdateInfoFragment.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
