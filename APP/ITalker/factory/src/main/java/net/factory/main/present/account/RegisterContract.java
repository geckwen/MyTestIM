package net.factory.main.present.account;


import net.common.factory.present.BaseContract;

/**
 * Created by CLW on 2017/8/25.
 */

public interface RegisterContract {
    interface View extends BaseContract.View<Present>{
        //注册成功
        void registerSuccess();
    }
    interface Present extends  BaseContract.Present{

        //注册逻辑
        void Register(String phone,String name,String password);

        //检测手机号码的正确
        boolean checkPhone(String phone);

    }
}
