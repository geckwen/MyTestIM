package net.factory.data.message;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.common.factory.data.DataSource;
import net.factory.data.BaseRespository;
import net.factory.model.db.Message;
import net.factory.model.db.Message_Table;

import java.util.Collections;
import java.util.List;

/**
 * Created by CLW on 2017/10/3.
 */

public class MessageRespository extends BaseRespository<Message> implements MessageDataSource{

    String receiverId;
    public MessageRespository(String receiverId){
        this.receiverId = receiverId;
    }

    @Override
    public void load(SuccessCallback<List<Message>> callback) {
        super.load(callback);

        //group==null和sendId==receiverId 或者 receiverId==receiverId
        SQLite.select().from(Message.class)
                .where(OperatorGroup.clause()
                        .and(Message_Table.sender_id.eq(receiverId))
                        .and(Message_Table.group_id.isNull()))
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
    public boolean isRequired(Message model) {
        //receiverId相对应的接收者ID
        return (receiverId.equalsIgnoreCase(model.getSender().getId())
                &&model.getGroup()==null)||(model.getReceiver()!=null&&receiverId.equalsIgnoreCase(model.getReceiver().getId()));
    }
}
