package net.qintalk.italker.push.bean;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

/**
 * 消息接收记录表
 * @author CLW
 *
 */
@Entity
@Table(name="TB_PUSH_HISTORY")
public class PushHistory {
	
	//这是一个主键
	@Id
	@PrimaryKeyJoinColumn
	//主键生成存储的类型为UUID
	@GeneratedValue(generator="uuid")
	//把uuid的生成器定义为uuid2,uuid2是常规的UUID toString
	@GenericGenerator(name="uuid",strategy="uuid2")
	@Column(updatable=false,nullable=false)
    private String id;
	
	//推送的都是实体的信息为JSON
	@Lob
	@Column(nullable=false,columnDefinition="BLOB")
	private String entity;
	
	//实体的类型
	@Column(nullable=false)
	private int entityType;
	
	//接收者
	//一个接收者可以接受多个推送消息
	//FetchType.EAGER 加载一条推送消息之间加载用户信息
	@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="receiveId")
	private User receiver;
	@Column(nullable=false,updatable=false,insertable=false)
	private String receiveId;
	
	//接收者当前状态下的设备
	//User.pushId
	@Column
	private String recevicePushId;
	
	//创建创建时间戳
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime=LocalDateTime.now();
	
	//创建更新时间戳
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime updateDatetime=LocalDateTime.now();
	
	//消息到达时间
	@Column
	private LocalDateTime arriveDatetime;
	
	//发送者
	//一个接收者可以接受多个推送消息
	//FetchType.EAGER 加载一条推送消息之间加载用户信息
	@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="sendId")
	private User sender;
	@Column(updatable=false,insertable=false)
	private String sendId;
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public int getEntityType() {
		return entityType;
	}
	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public String getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}
	public String getRecevicePushId() {
		return recevicePushId;
	}
	public void setRecevicePushId(String recevicePushId) {
		this.recevicePushId = recevicePushId;
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
	public LocalDateTime getArriveDatetime() {
		return arriveDatetime;
	}
	public void setArriveDatetime(LocalDateTime arriveDatetime) {
		this.arriveDatetime = arriveDatetime;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

}
