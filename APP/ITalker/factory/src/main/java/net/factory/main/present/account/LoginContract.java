package net.factory.main.present.account;

import net.common.factory.present.BaseContract;

/**
 * Created by CLW on 2017/8/25.
 */

public interface LoginContract {
    interface View extends BaseContract.View<Present>{
        //注册成功
        void loginSuccess();
    }
    interface Present extends BaseContract.Present{

        //注册逻辑
        void login(String phone,String password);


    }
}
