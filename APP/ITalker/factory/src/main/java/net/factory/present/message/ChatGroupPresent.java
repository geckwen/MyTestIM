package net.factory.present.message;

import net.factory.data.message.MessageDataSource;
import net.factory.data.message.MessageRespository;
import net.factory.model.db.Group;

/**
 * Created by CLW on 2017/10/3.
 */

public class ChatGroupPresent extends ChatPresent<ChatContract.GroupView> {

    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public ChatGroupPresent( ChatContract.GroupView view) {
        super(new MessageRespository(), view);
    }
}
