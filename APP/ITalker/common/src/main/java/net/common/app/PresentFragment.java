package net.common.app;

import android.content.Context;
import android.support.annotation.StringRes;

import net.common.factory.present.BaseContract;

/**
 * Created by CLW on 2017/8/25.
 * @param <Present>
 */

public abstract class PresentFragment<Present extends BaseContract.Present > extends BaseFragment implements BaseContract.View<Present>{
    protected Present mPresent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在开始时 就初始化Present
        initPresent();
    }


    /**
     * 初始化Present
     * @return Present
     */
    protected abstract Present initPresent();

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    /**
     * 显示错误
     * @param str 要显示的资源的int值
     */
    @Override
    public void showError(@StringRes int str) {
        Application.showToast(str);
    }

    @Override
    public void showLoading() {
        //显示一个loading
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
