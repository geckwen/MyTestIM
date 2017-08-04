package net.qintalk.italker.push.bean;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.ws.rs.CookieParam;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;

/**
 * Ⱥ��Ա
 * @author CLW
 *
 */
@Entity
@Table(name="TB_GROUP_MEMBER")
public class GroupMember {
	public static final int PERMISSION_TYPE_NONE=0;// ��ͨ��Ա
	public static final int PERMISSION_TYPE_ADMIN=1;// ����Ա
	public static final int PERMISSION_TYPE_ADMIN_SU=2;//����Ա�ʹ�����
	
	
	public static final int NOTIFY_LEVEL_INVALID = -1;//Ĭ�ϲ�����
	public static final int NOTIFY_LEVEL_NONE = 0; //Ĭ��֪ͨ����
    public static final int NOTIFY_LEVEL_CLOSE =1 ;//������Ϣ����ʾ
	//����
	@Id
	//������������Ϊuuid
	@GeneratedValue(generator="uuid")
	//��������uuid����Ϊuuid2
	@GenericGenerator(name="uuid",strategy="uuid2")
	@Column(nullable=false,updatable=false)
	private String id;
	
	//Ⱥ��Ա���ֱ���
	@Column
	private String ailas;
	
	//��Ϣ֪ͨ����
	@Column(nullable=false)
	private int notifyLevel = NOTIFY_LEVEL_NONE;
	
	//Ȩ��
	@Column(nullable=false)
	private int permissType = PERMISSION_TYPE_NONE;
	
	//Ⱥ��Ա
	//�ڼ���Ⱥ��Աʱ������Ⱥ��Ա��Ϣ
	@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="memberId")
	private User member;
	@Column(updatable=false,nullable=false,insertable=false)
	private String memberId;
	
	//Ⱥ��Ա
		//�ڼ���Ⱥ��Աʱ������Ⱥ��Ա��Ϣ
		@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
		@JoinColumn(name="groupId")
		private Group group;
		@Column(updatable=false,nullable=false,insertable=false)
		private String groupId;
	
	//��������ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime = LocalDateTime.now();
	
	//��������ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime updateDatetime = LocalDateTime.now();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAilas() {
		return ailas;
	}

	public void setAilas(String ailas) {
		this.ailas = ailas;
	}

	public int getNotifyLevel() {
		return notifyLevel;
	}

	public void setNotifyLevel(int notifyLevel) {
		this.notifyLevel = notifyLevel;
	}

	public int getPermissType() {
		return permissType;
	}

	public void setPermissType(int permissType) {
		this.permissType = permissType;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
   
}
