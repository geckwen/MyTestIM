package net.factory.model.api;


import net.factory.model.card.MessageCard;
import net.factory.model.db.Message;

import java.util.UUID;

/**
 * API请求的Model格式
 *
 */
public class MessageCreateModel {
    // ID从客户端生产，一个UUID
    private String id;
    private String content;

    private String attach;

    // 消息类型
    private int type = Message.TYPE_STR;

    // 接收者 可为空

    private String receiverId;

    // 接收者类型，群，人
    private int receiverType = Message.RECEIVER_TYPE_NONE;

    private MessageCreateModel(){
        this.id= UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }



    public String getContent() {
        return content;
    }


    public String getAttach() {
        return attach;
    }



    public int getType() {
        return type;
    }


    public String getReceiverId() {
        return receiverId;
    }



    public int getReceiverType() {
        return receiverType;
    }



    public static class Builder{
        private MessageCreateModel msgCreateModel;

        public Builder()
        {
            this.msgCreateModel = new MessageCreateModel();
        }

        public Builder receiver(String receiverId,int receiverType)
        {
            this.msgCreateModel.receiverId=receiverId;
            this.msgCreateModel.receiverType= receiverType;
            return this;
        }

        public Builder content(String content,int type)
        {
            this.msgCreateModel.content=content;
            this.msgCreateModel.type = type;
            return this;
        }


        public MessageCreateModel build()
        {
            return msgCreateModel;
        }

    }
    private MessageCard messageCard;
    public MessageCard buildCard(){
        if(messageCard==null){
        messageCard =new MessageCard();
        messageCard.setId(id);
        messageCard.setType(type);
        messageCard.setContent(content);
        messageCard.setAttach(attach);
        messageCard.setStatus(Message.STATUS_CREATED);
            if(receiverType==Message.RECEIVER_TYPE_NONE) {
                messageCard.setReceiverId(receiverId);
            }else{
                messageCard.setGroupId(receiverId);
            }
        }
        return messageCard;
    }

    public static  MessageCreateModel buildWithMessage(Message message)
    {
        MessageCreateModel messageCreateModel = new MessageCreateModel();
        messageCreateModel.id = message.getId();
        messageCreateModel.content = message.getContent();
        messageCreateModel.type = message.getType();
        messageCreateModel.attach = message.getAttach();
        if(message.getReceiver()!=null)
        {
            messageCreateModel.receiverType = Message.RECEIVER_TYPE_NONE;
            messageCreateModel.receiverId  = message.getReceiver().getId();
        }else{
            messageCreateModel.receiverId = message.getGroup().getId();
            messageCreateModel.receiverType =Message.RECEIVER_TYPE_GROUP;
        }
        return messageCreateModel;
    }
}
