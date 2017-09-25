package net.factory.present.present.account;


import android.support.annotation.StringRes;
import android.text.TextUtils;


import net.common.factory.data.DataSource;
import net.common.factory.present.BasePresent;
import net.common.tools.UiShow;
import net.factory.R;
import net.factory.model.api.account.LoginModel;
import net.factory.model.db.User;
import net.factory.data.Helper.AcccountHelper;
import net.factory.persistence.Account;



/**
 * 登录Present
 * Created by CLW on 2017/9/5.
 */

public class LoginPresent extends BasePresent<LoginContract.View>implements LoginContract.Present
        ,DataSource.Callback<User> {
    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view 登录的view
     */
    public LoginPresent(LoginContract.View view) {
        super(view);
    }


    /**
     * 进行登录操作
     * @param phone 登录账户
     * @param password 登录密码
     */
    @Override
    public void login(String phone, String password) {
        final LoginContract.View view = getmView();
        if(TextUtils.isEmpty(phone)&&TextUtils.isEmpty(password)) {
            view.showError(R.string.data_account_login_invalid_parameter);

        }else {
            LoginModel loginModel = new LoginModel(phone,password, Account.getPushId());
            AcccountHelper.login(loginModel,this);
        }


    }


    @Override
    public void onDataLoader(User user) {
        //当请求响应，并返回一个用户信息
        //则进行界面的告知，
        final LoginContract.View view = getmView();
        if (view == null)
            return;
        //此时是从网络请求回来，并不一定是在主线程
        //强制执行主线程
        UiShow.showRes(new Runnable() {
            @Override
            public void run() {
                view.loginSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvaliable(final @StringRes int res) {
        final LoginContract.View view = getmView();
        //注册失败后显示错误
        UiShow.showRes(new Runnable() {
            @Override
            public void run() {
                view.showError(res);
            }
        });
    }
}
