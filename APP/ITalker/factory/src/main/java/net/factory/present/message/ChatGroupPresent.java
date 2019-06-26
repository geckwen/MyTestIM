package net.factory.present.message;

import net.factory.data.Helper.ContactHelper;
import net.factory.data.Helper.GroupHelper;
import net.factory.data.message.MessageDataSource;
import net.factory.data.message.MessageGroupRespository;
import net.factory.data.message.MessageRespository;
import net.factory.model.db.Group;
import net.factory.model.db.Message;
import net.factory.model.view.MemberUserModel;
import net.factory.persistence.Account;

import java.util.List;

/**
 * Created by CLW on 2017/10/3.
 */

public class ChatGroupPresent extends ChatPresent<ChatContract.GroupView> implements ChatContract.Present{

    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public ChatGroupPresent(String receiverId, ChatContract.GroupView view) {
        super(new MessageGroupRespository(receiverId),view,receiverId, Message.RECEIVER_TYPE_GROUP);

    }

    @Override
    public void start() {
        super.start();
       //那群的信息
        Group group = GroupHelper.findFromLocal(receiverId);
        if(group!=null)
        {
            //初始化操作
            ChatContract.GroupView view = getmView();
            boolean isAdmin = Account.getUserId().equalsIgnoreCase(group.getOwner().getId());

            List<MemberUserModel> models = group.getLatetyGroupMember();
            final long memberCount = group.getGroupMemberCount();
            //没有显示的成员的数量
            long moreCount = memberCount - models.size();
            view.onInitGroupMembers(models,moreCount);

        }
    }
}
