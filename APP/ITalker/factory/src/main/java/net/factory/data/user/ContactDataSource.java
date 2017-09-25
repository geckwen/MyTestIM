package net.factory.data.user;

import net.common.factory.data.DataSource;
import net.factory.model.db.User;

import java.util.List;

/**
 * 联系人数据源
 * Created by CLW on 2017/9/24.
 */

public interface ContactDataSource {
    /**
     * 对数据进行加载一个职责
     * @param callback 加载成功后返回callback
     */
    void load(DataSource.SuccessCallback<List<User>> callback);

    /**
     * 销毁操作
     */
    void dispose();

}
