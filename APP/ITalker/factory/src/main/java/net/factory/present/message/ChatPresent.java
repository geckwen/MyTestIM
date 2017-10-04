package net.factory.present.message;

import android.support.v7.util.DiffUtil;

import net.common.widget.recycle.RecycleAdapter;
import net.factory.data.Helper.MessageHelper;
import net.factory.data.message.MessageDataSource;
import net.factory.data.message.MessageRespository;
import net.factory.model.api.MessageCreateModel;
import net.factory.model.db.Message;
import net.factory.model.db.User;
import net.factory.persistence.Account;
import net.factory.present.BaseSourcePresent;
import net.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by CLW on 2017/10/3.
 */

public  class ChatPresent<View extends ChatContract.View>
        extends BaseSourcePresent<Message,Message,MessageDataSource,View>
        implements ChatContract.Present {
    protected  String receiverId;
    protected  int receiverType;

    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param source
     * @param view
     */
    public ChatPresent(MessageDataSource source, View view,String receiverId,int receiverType) {
        super(source, view);
        this.receiverId=receiverId;
        this.receiverType=receiverType;
    }

    @Override
    public void onDataLoader(List<Message> newList) {
        final ChatContract.View view =getmView();
        if(view==null)
            return;
        RecycleAdapter<Message> adapter =view.getRecycleAdapter();
        List<Message> oldList = adapter.getDataList();
        //进行数据对比
        DiffUiDataCallback<Message> callback =new DiffUiDataCallback<Message>(oldList,newList);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        //进行界面的刷新
        refreshData(newList,result);
    }

    @Override
    public void pushMsg(String msg) {
        MessageCreateModel messageCreateModel = new MessageCreateModel.Builder()
                .receiver(receiverId,receiverType)
                .content(msg,Message.TYPE_STR)
                .build();

        //进行网络发送
        MessageHelper.push(messageCreateModel);
    }

    @Override
    public void pushAudio(String path) {

    }

    @Override
    public void pushPic(String[] paths) {

    }

    @Override
    public boolean refresh(Message message) {
        if(Account.getUserId().equalsIgnoreCase(message.getSender().getId())&&message.getStatus()==Message.STATUS_FAILED)
        {
            message.setStatus(Message.STATUS_CREATED);
           MessageCreateModel  messageCreateModel = MessageCreateModel.buildWithMessage(message);
            MessageHelper.push(messageCreateModel);
            return true;
        }
        return  false;

    }
}
