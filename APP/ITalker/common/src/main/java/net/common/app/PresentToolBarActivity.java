package net.common.app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.StringRes;

import net.common.R;
import net.common.factory.present.BaseContract;

/**
 * Created by CLW on 2017/9/14.
 */

public abstract  class PresentToolBarActivity<Present extends BaseContract.Present> extends ToolBarActivity implements BaseContract.View<Present> {
    protected Present mPresent;
    protected ProgressDialog mLoadingDialog;

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
        //隐藏自己
        hideDiaLogLoading();
        if(mplaceHolderView!=null) {
            mplaceHolderView.triggerError(str);
        }else {
            Application.showToast(str);
        }
    }

    @Override
    public void showLoading() {
        //显示一个loading
        if(mplaceHolderView!=null) {
            mplaceHolderView.triggerLoading();
        }
        else {
            ProgressDialog  progressDialog = mLoadingDialog;
            if(progressDialog!=null)
            {
                progressDialog = new ProgressDialog(this,R.style.AppTheme_Dialog_Alert_Light);
                //不可触摸取消
                progressDialog.setCanceledOnTouchOutside(false);
                //强制取消 关闭界面
                progressDialog.setCancelable(true);
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
               mLoadingDialog = progressDialog;
            }
            progressDialog.setMessage(getText(R.string.prompt_loading));
            progressDialog.show();
        }
    }

    protected void hideDiaLogLoading(){
        ProgressDialog  progressDialog = mLoadingDialog;
        if(progressDialog!=null){
            mLoadingDialog = null;
            progressDialog.dismiss();
        }
    }

    /**
     * 隐藏loading
     */
    public void showHide()
    {
        //不管如何 先隐藏
        hideDiaLogLoading();
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
