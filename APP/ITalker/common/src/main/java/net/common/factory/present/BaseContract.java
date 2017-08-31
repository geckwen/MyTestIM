package net.common.factory.present;

import android.support.annotation.StringRes;

/**
 * MVP模式中公共的基本契约
 * Created by CLW on 2017/8/25.
 */

public interface BaseContract {
    interface View<T extends Present>{
        //公共展示错误的String字符串,必须是Strings里面的
        void showError(@StringRes int str);
        //显示进度条
        void showLoading();
        //提供一个注册Present方法
        void setPresent(T present);
    }
    interface Present{

        //公共的开始方法
        void start();
        //公共的销毁方法
        void destroy();
    }
}
