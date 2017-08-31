package net.common.factory.data;

import android.support.annotation.StringRes;

/**
 * Created by CLW on 2017/8/28.
 */

public interface DataSource {
    /**
     * 同时包括了成功和失败的接口
     * @param <T>
     */
    interface  Callback<T> extends  SuccessCallback<T>,FaileCallback{

    }

    interface SuccessCallback<T>{
        //数据加载成功，网络请求成功
        void onDataLoader(T user);
    }

    interface FaileCallback{
        //数据加载失败，网络请求失败
        void onDataNotAvaliable(@StringRes int res);
    }
}
