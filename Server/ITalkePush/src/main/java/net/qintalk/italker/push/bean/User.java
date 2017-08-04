package net.qintalk.italker.push.bean;

import java.time.LocalDateTime;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



/**
 * �û���Model,��Ӧ���ݿ�
 * @author CLW
 *
 */
@Entity
@Table(name="TB_USER")
public class User { 
	//����һ������
	@Id
	@PrimaryKeyJoinColumn
	//�������ɴ洢������ΪUUID
	@GeneratedValue(generator="uuid")
	//��uuid������������Ϊuuid2,uuid2�ǳ����UUID toString
	@GenericGenerator(name="uuid",strategy="uuid2")
	@Column(updatable=false,nullable=false)
    private String id;
	
	//�û�������Ψһ
	@Column(nullable=false,length=128,unique=true)
	private String userName;
	//�ֻ�����Ψһ
	@Column(nullable=false,length=64,unique=true)
	private String phone;
	//�û�����
	@Column(nullable=false)
	private String password;
	//�û�ͷ��
	@Column
	private String portrait;
	//�û�����
	@Column
	private String description;
	//�û��Ա�
	@Column(nullable=false)
	private int sex=0;
	//token��Ψһ��
	@Column(unique=true)
	private String token;
	
	//�������͵��豸ID
	@Column
	private String pushId;
	
	//����Ϊ����ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime=LocalDateTime.now();
	
	//����Ϊ����ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime upDatetime=LocalDateTime.now();
	
	//����Ϊ����ʱ���
	@CreationTimestamp
	@Column
	private LocalDateTime lastReceiverTime=LocalDateTime.now();
	
	//��ע�û���
	//��Ӧ�����ݿ���ֶ�ΪTB_UserFollow.originId
	@JoinColumn(name="originId")
	//����Ϊ�����أ�Ĭ�ϼ���User��Ϣ��ʱ�򣬲�����ѯ�������
	@LazyCollection(LazyCollectionOption.EXTRA)
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private HashSet<UserFollow> originList=new HashSet<UserFollow>();
	
	//����ע�û���
	//��Ӧ�����ݿ���ֶ�ΪTB_UserFollow.acceptedId
	@JoinColumn(name="acceptedId")
	//����Ϊ�����أ�Ĭ�ϼ���User��Ϣ��ʱ�򣬲�����ѯ�������
	@LazyCollection(LazyCollectionOption.EXTRA)
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private HashSet<UserFollow> acceptedList=new HashSet<UserFollow>();
	
	//����Ⱥ��
	@JoinColumn(name="ownId")
	//����Ϊ�����أ�Ĭ�ϼ���User��Ϣ��ʱ��,������ѯ�������
	//������groups.size()������ѯ�����������ؾ����group����Ϣ
	@LazyCollection(LazyCollectionOption.EXTRA)
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private HashSet<Group> createGroupList=new HashSet<Group>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public LocalDateTime getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(LocalDateTime createDatetime) {
		this.createDatetime = createDatetime;
	}

	public LocalDateTime getUpDatetime() {
		return upDatetime;
	}

	public void setUpDatetime(LocalDateTime upDatetime) {
		this.upDatetime = upDatetime;
	}

	public LocalDateTime getLastReceiverTime() {
		return lastReceiverTime;
	}

	public void setLastReceiverTime(LocalDateTime lastReceiverTime) {
		this.lastReceiverTime = lastReceiverTime;
	}

	public HashSet<UserFollow> getOriginList() {
		return originList;
	}

	public void setOriginList(HashSet<UserFollow> originList) {
		this.originList = originList;
	}

	public HashSet<UserFollow> getAcceptedList() {
		return acceptedList;
	}

	public void setAcceptedList(HashSet<UserFollow> acceptedList) {
		this.acceptedList = acceptedList;
	}

	public HashSet<Group> getCreateGroupList() {
		return createGroupList;
	}

	public void setCreateGroupList(HashSet<Group> createGroupList) {
		this.createGroupList = createGroupList;
	}

    
}
