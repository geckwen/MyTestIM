package net.common.factory.present;

import android.support.annotation.StringRes;

import net.common.widget.recycle.RecycleAdapter;

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

    //最基本的列表的View的职责
    interface RecycleView<T extends Present,ViewType> extends View<T>{
        //拿到适配器，然后自主的进行刷新
        RecycleAdapter<ViewType> getRecycleAdapter();

        //当适配器进行数据更改时刷新
        void onAdapterDataRefresh();


    }
}
