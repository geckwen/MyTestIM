package net.factory.utils;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by CLW on 2017/9/18.
 */

public class DiffUiDataCallback<T extends DiffUiDataCallback.UiDataDiffer<T>> extends DiffUtil.Callback {
    private List<T> oldList,newList;

    public DiffUiDataCallback(List<T> oldList,List<T> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        //旧的数据大小
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        //新的数据大小
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        //两个类是否就是同一个东西,比如Id相等的User.
        T oldBean = oldList.get(oldItemPosition);
        T newBean = newList.get(newItemPosition);
        return newBean.isSame(oldBean);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        //经过相等判断后，进一步判断是否有数据更改
        //比如.同一个用户的两个不同实例，其中的name字段不同
        T oldBean = oldList.get(oldItemPosition);
        T newBean = newList.get(newItemPosition);
        return newBean.isUiContentSame(oldBean);
    }

    public interface UiDataDiffer<T>
    {
        //传递一个旧的数据,判断是否是同一个数据
        boolean isSame(T old);
        //传递一个旧的数据,判断是否内容相等
        boolean isUiContentSame(T old);
    }
}
