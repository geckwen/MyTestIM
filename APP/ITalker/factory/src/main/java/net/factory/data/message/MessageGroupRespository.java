package net.factory.data.message;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.factory.data.BaseRespository;
import net.factory.model.db.Message;
import net.factory.model.db.Message_Table;

import java.util.Collections;
import java.util.List;

/**
 * 跟群聊天的聊天列表
 *
 * Created by CLW on 2017/10/3.
 */

public class MessageGroupRespository extends BaseRespository<Message> implements MessageDataSource{
    //群的ID
    String receiverId;
    public MessageGroupRespository(String receiverId){
        this.receiverId = receiverId;
    }

    @Override
    public void load(SuccessCallback<List<Message>> callback) {
        super.load(callback);
        //group==null和sendId==receiverId 或者 receiverId==receiverId,groupid==receiverId

        SQLite.select()
                .from(Message.class)
                .where(Message_Table.group_id.eq(receiverId))
                .or(Message_Table.receiver_id.eq(receiverId))
                .orderBy(Message_Table.createAt,false) //正排序
                .limit(30)
                .async() // 异步调用
                .queryListResultCallback(this)
                .execute();

    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Message> tResult) {
        //进行倒序
        Collections.reverse(tResult);
        //再次调度
        super.onListQueryResult(transaction, tResult);
    }

    @Override
    public boolean isRequired(Message message) {
        //如果消息的Group不为空,则一定是发送到一个群的
        //如果群ID等于我们需要的，那就通过
        return message.getGroup()!=null
                && receiverId.equalsIgnoreCase(message.getGroup().getId());

    }
}
