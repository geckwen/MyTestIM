package net.factory.data.message;

import android.text.TextUtils;

import net.factory.data.Helper.DBHelper;
import net.factory.data.Helper.ContactHelper;
import net.factory.data.Helper.GroupHelper;
import net.factory.data.Helper.MessageHelper;
import net.factory.model.card.MessageCard;
import net.factory.model.db.Group;
import net.factory.model.db.Message;
import net.factory.model.db.User;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by CLW on 2017/9/22.
 */

public class MessagerDiapatcher implements MessageCenter {
    private volatile static MessagerDiapatcher instance;

    private static final Executor executor = Executors.newSingleThreadExecutor();

    private MessagerDiapatcher(){}

    public static MessagerDiapatcher getInstance()
    {
        if (instance==null)
        {
            synchronized (MessagerDiapatcher.class)
            {
                if (instance==null)
                {
                    instance= new MessagerDiapatcher();
                }
            }
        }
        return instance;
    }

    @Override
    public void dispatch(MessageCard... messageCards) {

    }

    private class MessagerHelder implements Runnable{
        private final MessageCard[] messageCards;


        public MessagerHelder(MessageCard[] messageCards)
        {
            this.messageCards= messageCards;
        }

        @Override
        public void run() {
            ArrayList<Message> messages =new ArrayList<>();
            for(MessageCard messageCard :messageCards)
            {
                if(messageCard==null || TextUtils.isEmpty(messageCard.getId())
                        ||TextUtils.isEmpty(messageCard.getSenderId())
                        ||(TextUtils.isEmpty(messageCard.getGroupId())&&TextUtils.isEmpty(messageCard.getReceiverId())))
                    continue;
                // 消息卡片有可能是推送过来的，也有可能是直接造的
                // 推送来的代表服务器一定有，我们可以查询到（本地有可能有，有可能没有）
                // 如果是直接造的，那么先存储本地，后发送网络
                // 发送消息流程：写消息->存储本地->发送网络->网络返回->刷新本地状态
                Message message = MessageHelper.findLocal(messageCard.getId());
                if(message!=null)
                {
                    // 消息本身字段从发送后就不变化了，如果收到了消息，
                    // 本地有，同时本地显示消息状态为完成状态，则不必处理，
                    // 因为此时回来的消息和本地一定一摸一样

                    // 如果本地消息显示已经完成则不做处理
                    if(message.getStatus()==Message.STATUS_DONE)
                        continue;
                    // 新状态为完成才更新服务器时间，不然不做更新
                    if (messageCard.getStatus() == Message.STATUS_DONE) {
                        // 代表网络发送成功，此时需要修改时间为服务器的时间
                        message.setCreateAt(messageCard.getCreateAt());

                        // 如果没有进入判断，则代表这个消息是发送失败了，
                        // 重新进行数据库更新而而已
                    }
                    // 更新一些会变化的内容
                    message.setContent(messageCard.getContent());
                    message.setAttach(messageCard.getAttach());
                    // 更新状态
                    message.setStatus(messageCard.getStatus());
                }else {
                    // 没找到本地消息，初次在数据库存储
                    User sender = ContactHelper.searchId(messageCard.getSenderId());
                    User receiver = null;
                    Group group = null;
                    if (!TextUtils.isEmpty(messageCard.getReceiverId())) {
                        receiver = ContactHelper.searchId(messageCard.getReceiverId());
                    } else if (!TextUtils.isEmpty(messageCard.getGroupId())) {
                        group = GroupHelper.findFromLocal(messageCard.getGroupId());
                    }

                    // 接收者总有一个
                    if (receiver == null && group == null && sender != null)
                        continue;
                    message = messageCard.build(group, sender, receiver);
                }
                User sender = ContactHelper.searchId(messageCard.getSenderId());



            }
            if(messages.size()>0)
                DBHelper.save(Message.class,messages.toArray(new Message[0]));
        }
    }
}
