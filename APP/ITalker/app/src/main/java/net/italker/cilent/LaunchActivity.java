package net.italker.cilent;

import net.common.BaseActivity;
import net.italker.cilent.fragment.assist.PermissionFragment;

/**
 * Created by CLW on 2017/8/20.
 */

public class LaunchActivity extends BaseActivity {
    @Override
    public int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(PermissionFragment.haveAllPermission(this,getSupportFragmentManager()))
        {
            MainActivity.show(this);
            finish();
        }

    }
}
