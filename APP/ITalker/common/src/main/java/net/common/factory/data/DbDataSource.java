package net.common.factory.data;

import java.util.List;

/**
 * Created by CLW on 2017/9/26.
 */

public interface DbDataSource<Data> extends DataSource {

    /**
     * 加载数据源
     * @param callback 传递一个callback回调 方法
     */
        void load(DataSource.SuccessCallback<List<Data>> callback);

}
