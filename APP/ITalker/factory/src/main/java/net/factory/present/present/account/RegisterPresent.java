package net.factory.present.present.account;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import net.common.Common;
import net.common.factory.data.DataSource;
import net.common.factory.present.BasePresent;
import net.common.tools.UiShow;
import net.factory.R;
import net.factory.model.api.account.RegisterModel;
import net.factory.model.db.User;
import net.factory.data.Helper.AcccountHelper;
import net.factory.persistence.Account;

import java.util.regex.Pattern;

/**
 * Created by CLW on 2017/8/25.
 */

public class RegisterPresent extends BasePresent<RegisterContract.View> implements
        RegisterContract.Present, DataSource.Callback<User> {
    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view 注册的view
     */
    public RegisterPresent(RegisterContract.View view) {
        super(view);
    }


    @Override
    public void Register(String phone, String name, String password) {
        //调用开始方法,start默认打开loading
        start();
        RegisterContract.View view = getmView();
        if (!checkPhone(phone)) {
            //phone不满足这些条件
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        } else if (name.length() < 2) {
            //名字需要大于2位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        } else if (password.length() < 6) {
            //密码需要大于6位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        } else {
            RegisterModel registerModel = new RegisterModel(phone, password, name, Account.getPushId());
            AcccountHelper.register(registerModel, this);
        }

    }

    /**
     * 检测手机是否正常
     *
     * @param phone 传入手机号
     * @return true表示手机号正常, false表示不正常
     */
    @Override
    public boolean checkPhone(String phone) {
        //手机号不为空且满足某些条件
        return !TextUtils.isEmpty(phone) && Pattern.matches(Common.Constance.REGAX_PHONE, phone);
    }

    @Override
    public void onDataLoader(User user) {
        //当请求响应，并返回一个用户信息
        //则进行界面的告知，
        final RegisterContract.View view = getmView();
        if (view == null)
            return;
        //此时是从网络请求回来，并不一定是在主线程
        //强制执行主线程
        UiShow.showRes(new Runnable() {
            @Override
            public void run() {
                view.registerSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvaliable(final @StringRes int res) {
        //请求未被相应
        //也进行界面的告知
        final RegisterContract.View view = getmView();

        //注册失败后显示错误
        UiShow.showRes(new Runnable() {
            @Override
            public void run() {
                view.showError(res);
            }
        });
    }
}
