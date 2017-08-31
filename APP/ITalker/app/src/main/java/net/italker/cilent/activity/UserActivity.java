package net.italker.cilent.activity;

import android.content.Intent;

import net.common.app.BaseActivity;
import net.common.app.BaseFragment;
import net.italker.cilent.R;

public class UserActivity extends BaseActivity {
    private BaseFragment mUpdateInfoFragment;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mUpdateInfoFragment.onActivityResult(requestCode,resultCode,data);
    }
}
