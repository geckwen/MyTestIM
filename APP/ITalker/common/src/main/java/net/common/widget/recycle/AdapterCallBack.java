package net.common.widget.recycle;

/**
 * 适配器回调接口
 * Created by CLW on 2017/7/30.
 */

public interface AdapterCallBack<T> {
    void update(T data, RecycleAdapter.MyViewHolder myViewHolder);
}
