package net.common.widget.recycle;

import net.common.Data;

/**
 * 适配器回调接口
 * Created by CLW on 2017/7/30.
 */

public interface AdapterCallBack {
    void update(Data data, RecycleAdapter.MyViewHolder myViewHolder);
}
