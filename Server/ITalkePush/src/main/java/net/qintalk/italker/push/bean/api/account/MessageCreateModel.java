package net.qintalk.italker.push.bean.api.account;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.google.gson.annotations.Expose;

import net.qintalk.italker.push.bean.db.Group;
import net.qintalk.italker.push.bean.db.Message;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.utils.TextUtil;

public class MessageCreateModel {
	@Expose
    private String id;

    // 内容不允许为空，类型为text
	@Expose
    private String content;

    // 附件
	@Expose
    private String attach;

    // 消息类型
	@Expose
    private int type=Message.TYPE_STR;

    
	@Expose
    private String receiverId;
	@Expose
    private int receiverType = Message.TYPE_RECEIVER_USER;

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

	


	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public int getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(int receiverType) {
		this.receiverType = receiverType;
	}
    
	public static boolean checkisNull(MessageCreateModel model)
	{
		return model!=null
				&&TextUtil.StringNotEmpty(model.id)
				&&TextUtil.StringNotEmpty(model.content)
				&&TextUtil.StringNotEmpty(model.receiverId)
				
				&&(model.receiverType==Message.TYPE_RECEIVER_GROUP
				||model.receiverType==Message.TYPE_RECEIVER_USER)
				
				&&(model.type==Message.TYPE_AUDIO
				||model.type==Message.TYPE_FILE
				||model.type==Message.TYPE_PIC
				||model.type==Message.TYPE_STR);
	}
}
