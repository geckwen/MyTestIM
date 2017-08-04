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
 * ��Ϣ
 * @author CLW
 *
 */
@Entity
@Table(name="TB_MESSAGE")
public class Message {
	public static final int TYPE_STR = 1; //�ַ�������
	public static final int TYPE_PIC = 2;//ͼƬ����
	public static final int TYPE_FIL = 3;//�ļ�����
	public static final int TYPE_AUDIO = 4;//�������� 

	@Id
	//���Զ�����uuid���ɿͻ���д��
	@GenericGenerator(name="uuid",strategy="uuid2")
	@Column(updatable=false,nullable=false)
	private String id;
	
	//��Ϣ���ݲ���Ϊnull,�Լ�����ΪTEXT
	@Column(nullable=false,columnDefinition="TEXT")
	private String content;
	
	//����
	@Column
	private String attach;
	
	//������Ϣ������
	@Column(nullable=false)
	private int type;
	
	//��������ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime=LocalDateTime.now();
	
	//��������ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime updateDatetime=LocalDateTime.now();
	
	//������
	//һ���û���Ӧ�����Ϣ
	@ManyToOne(optional=false)
	@JoinColumn(name="sendId")
	private User sender;
	//����ֶ�ֻ��Ϊ�˶�Ӧsender�����ݿ��ֶ�sendId
	//�������ֶ��ĸ��»��߲���
	@Column(nullable=false,updatable=false,insertable=false)
	private String sendId;
	
	//������
	//һ���û���Ӧ�����Ϣ
    @ManyToOne
	@JoinColumn(name="receiverId")
	private User receiver;
	@Column(updatable=false,insertable=false)
	private String receiverId;
	
	//����Ⱥ��Ϣ
	//һ��Ⱥ��Ӧ�����Ϣ
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
