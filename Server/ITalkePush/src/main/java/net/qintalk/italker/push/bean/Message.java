package net.qintalk.italker.push.bean;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;


/**
 * 消息
 * @author CLW
 *
 */
@Entity
@Table(name="TB_MESSAGE")
public class Message {
	public static final int TYPE_STR = 1; //字符串类型
	public static final int TYPE_PIC = 2;//图片类型
	public static final int TYPE_FIL = 3;//文件类型
	public static final int TYPE_AUDIO = 4;//语音类型 

	@Id
	//不自动生成uuid，由客户端写入
	@GenericGenerator(name="uuid",strategy="uuid2")
	@Column(updatable=false,nullable=false)
	private String id;
	
	//消息内容不能为null,以及类型为TEXT
	@Column(nullable=false,columnDefinition="TEXT")
	private String content;
	
	//附件
	@Column
	private String attach;
	
	//传输信息的类型
	@Column(nullable=false)
	private int type;
	
	//创建创建时间戳
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime=LocalDateTime.now();
	
	//创建更新时间戳
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime updateDatetime=LocalDateTime.now();
	
	//发送者
	//一个用户对应多个消息
	@ManyToOne(optional=false)
	@JoinColumn(name="sendId")
	private User sender;
	//这个字段只是为了对应sender的数据库字段sendId
	//不允许手动的更新或者插入
	@Column(nullable=false,updatable=false,insertable=false)
	private String sendId;
	
	//接受者
	//一个用户对应多个消息
    @ManyToOne
	@JoinColumn(name="receiverId")
	private User receiver;
	@Column(updatable=false,insertable=false)
	private String receiverId;
	
	//接收群消息
	//一个群对应多个消息
    @ManyToOne
	@JoinColumn(name="groupId")
	private Group receivergroup;
	@Column(updatable=false,insertable=false)
	private String groupId;
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
	public LocalDateTime getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(LocalDateTime createDatetime) {
		this.createDatetime = createDatetime;
	}
	public LocalDateTime getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(LocalDateTime updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public Group getReceivergroup() {
		return receivergroup;
	}
	public void setReceivergroup(Group receivergroup) {
		this.receivergroup = receivergroup;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	
	
	
	
}
