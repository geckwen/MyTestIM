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
 * ��Ϣ���ռ�¼��
 * @author CLW
 *
 */
@Entity
@Table(name="TB_PUSH_HISTORY")
public class PushHistory {
	
	//����һ������
	@Id
	@PrimaryKeyJoinColumn
	//�������ɴ洢������ΪUUID
	@GeneratedValue(generator="uuid")
	//��uuid������������Ϊuuid2,uuid2�ǳ����UUID toString
	@GenericGenerator(name="uuid",strategy="uuid2")
	@Column(updatable=false,nullable=false)
    private String id;
	
	//���͵Ķ���ʵ�����ϢΪJSON
	@Lob
	@Column(nullable=false,columnDefinition="BLOB")
	private String entity;
	
	//ʵ�������
	@Column(nullable=false)
	private int entityType;
	
	//������
	//һ�������߿��Խ��ܶ��������Ϣ
	//FetchType.EAGER ����һ��������Ϣ֮������û���Ϣ
	@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="receiveId")
	private User receiver;
	@Column(nullable=false,updatable=false,insertable=false)
	private String receiveId;
	
	//�����ߵ�ǰ״̬�µ��豸
	//User.pushId
	@Column
	private String recevicePushId;
	
	//��������ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime=LocalDateTime.now();
	
	//��������ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime updateDatetime=LocalDateTime.now();
	
	//��Ϣ����ʱ��
	@Column
	private LocalDateTime arriveDatetime;
	
	//������
	//һ�������߿��Խ��ܶ��������Ϣ
	//FetchType.EAGER ����һ��������Ϣ֮������û���Ϣ
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
