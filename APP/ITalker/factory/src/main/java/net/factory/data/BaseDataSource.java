package net.factory.data;

import net.common.factory.data.DataSource;

import java.util.List;

/**
 * Created by CLW on 2017/9/26.
 */

public interface BaseDataSource<Data>{
    /**
     * 对数据进行加载一个职责
     * @param callback 加载成功后返回callback
     */
    void load(DataSource.SuccessCallback<List<Data>> callback);

    /**
     * 销毁操作
     */
    void dispose();
}
