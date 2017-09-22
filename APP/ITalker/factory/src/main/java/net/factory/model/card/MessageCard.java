package net.factory.model.card;

import net.factory.model.db.Group;
import net.factory.model.db.Message;
import net.factory.model.db.User;

import java.util.Date;

/**
 * Created by CLW on 2017/9/22.
 */

public class MessageCard {

    private String id;

    private String content;

    private String attach;

    private int type;

    private Date createAt;

    private String senderId;

    private String receiverId;

    private String groupId;

    private  transient  int status = Message.STATUS_DONE; //消息现在的状态
    private transient  boolean uploader = false; // 上传是否完成

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Message build(Group group, User sender, User receive)
    {
        Message message =new Message();
        message.setGroup(group);
        message.setId(id);
        message.setAttach(attach);
        message.setContent(content);
        message.setReceiver(receive);
        message.setType(type);
        message.setCreateAt(createAt);
        message.setSender(sender);
        return  message;
    }

}
