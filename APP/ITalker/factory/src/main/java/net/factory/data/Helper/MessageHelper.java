package net.factory.data.Helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.factory.model.api.MessageCreateModel;
import net.factory.model.base.RspModel;
import net.factory.model.card.MessageCard;
import net.factory.model.db.Message;
import net.factory.model.db.Message_Table;
import net.factory.net.NetWork;
import net.factory.net.RemoteService;
import net.factory.present.Factory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        return  SQLite.select().from(Message.class)
                .where(Message_Table.id.eq(id))
                .querySingle();

    }

    /**
     * 异步网络查询数据
     * @param messageCreateModel
     */
    public static void push(final MessageCreateModel messageCreateModel) {
        Factory.runAsync(new Runnable() {
            @Override
            public void run() {
                Message message = findLocal(messageCreateModel.getId());
                final MessageCard messageCard = messageCreateModel.buildCard();
                //信息是已经发送的了,则不能重新发送
                //信息正在发送的,则不能重新发送
                if(message==null||message.getStatus()!=Message.STATUS_FAILED)
                    return;

                //TODO 如果是文件类型的(语音,图片,文件),需要先上传后才发送

                //直接发送

                Factory.getMessageCente().dispatch(messageCard);
                RemoteService remoteService = NetWork.getAccountRemoteService();
                remoteService.push(messageCreateModel).enqueue(new Callback<RspModel<MessageCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<MessageCard>> call, Response<RspModel<MessageCard>> response) {
                        RspModel<MessageCard> rspModel = response.body();

                        if(response!=null&&rspModel.success()) {
                            MessageCard messageCard = rspModel.getResult();
                            if(messageCard!=null)
                            {
                                messageCard.setStatus(Message.STATUS_DONE);
                                Factory.getMessageCente().dispatch(messageCard);
                            }
                        }else{
                            Factory.decodeRsqCode(rspModel,null);
                            onFailure(call,null);
                            }
                    }

                    @Override
                    public void onFailure(Call<RspModel<MessageCard>> call, Throwable t) {
                            messageCard.setStatus(Message.STATUS_FAILED);
                            Factory.getMessageCente().dispatch(messageCard);
                    }
                });
            }
        });

    }
}
