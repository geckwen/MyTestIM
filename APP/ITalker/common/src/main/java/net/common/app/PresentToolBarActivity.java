package net.common.app;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import net.common.R;
import net.common.factory.present.BaseContract;

/**
 * Created by CLW on 2017/9/14.
 */

public abstract  class PresentToolBarActivity<Present extends BaseContract.Present> extends ToolBarActivity implements BaseContract.View<Present> {
    protected Present mPresent;

    @Override
    protected void initBefore() {
        super.initBefore();
        initPresent();
    }

    /**
     * 初始化Present
     * @return Present
     */
    protected abstract Present initPresent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresent!=null)
            mPresent.destroy();
    }

    /**
     * 显示错误
     * @param str 要显示的资源的int值
     */
    @Override
    public void showError(@StringRes int str) {
        if(mplaceHolderView!=null) {
            mplaceHolderView.triggerError(str);
        }else {
            Application.showToast(str);
        }
    }

    @Override
    public void showLoading() {
        //显示一个loading
        if(mplaceHolderView!=null)
            mplaceHolderView.triggerLoading();
    }

    /**
     * 隐藏loading
     */
    public void showHide()
    {
        if(mplaceHolderView!=null)
            mplaceHolderView.triggerOk();
    }
    /**
     * 在一个view注册Present
     * @param present
     */
    @Override
    public void setPresent(Present present) {
        //赋值
        mPresent = present;
    }
}
