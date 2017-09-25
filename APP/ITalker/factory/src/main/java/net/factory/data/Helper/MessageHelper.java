package net.factory.data.Helper;

import net.factory.model.db.Message;

/**
 * Created by CLW on 2017/9/22.
 */

public class MessageHelper {

    /**
     * 从本地查找数据
     * @param id
     * @return
     */
    public static Message findLocal(String id) {
        return new Message();
    }
}
