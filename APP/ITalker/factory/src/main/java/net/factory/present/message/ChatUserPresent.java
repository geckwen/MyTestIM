package net.factory.present.message;

import net.factory.data.Helper.ContactHelper;
import net.factory.data.message.MessageDataSource;
import net.factory.data.message.MessageRespository;
import net.factory.model.db.Message;
import net.factory.model.db.User;

/**
 * Created by CLW on 2017/10/3.
 */

public class ChatUserPresent extends ChatPresent<ChatContract.UserView> {

    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     * @param receiverId
     * @param type
     */


    public ChatUserPresent(ChatContract.UserView view, String receiverId) {
        super(new MessageRespository(receiverId),view, receiverId, Message.TYPE_STR);
    }


    @Override
    public void start() {
        super.start();
       User receiver = ContactHelper.findLocalUserById(receiverId);
        getmView().init(receiver);
    }
}
