package net.qintalk.italker.push.bean;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

/**
 * �û����û��������м��
 * @author CLW
 *
 */
@Entity
@Table(name="TB_USERFOLLOW")
public class UserFollow {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid2")
	private String id;
	
	//��ע������,һ�������ע�����봴�����ұ���Ҫ�й�ע�ߣ�������м��FollowҲ������
	//����Թ�ע���ˣ�ÿһ�ι�ע����һ����Ϣ
	//�����manyָ����Follow
	@ManyToOne(optional=false)
	//��������ı��ֶ���ΪoriginId,��Ӧuser.id
	//�������ݵ����ݿ�Ĵ洢�ֶ�
	@JoinColumn(name="originId")
	private User origin;
	//������Ϊnull,��������£�����
	@Column(nullable=false,updatable=false,insertable=false)
	private String originId;
	
	
	//����ע��һ�����ڣ����Ա������ע�Ź�ע
	@ManyToOne(optional=false)
	//��������ı��ֶ���ΪacceptedId,��Ӧuser.id
	//�������ݵ����ݿ�Ĵ洢�ֶ�
	@JoinColumn(name="acceptedId")
	private User accepted;
	//������Ϊnull,��������£�����
	@Column(nullable=false,updatable=false,insertable=false)
	private String acceptedId;
	
	//����ע�߱���������Ϊnull
	@Column
	private String ailes;
	
	//����ʱ��
	@Column(nullable=false)
	@CreationTimestamp
	private LocalDateTime createDatetime=LocalDateTime.now();
	
	//����ʱ��
	@Column(nullable=false)
	@CreationTimestamp
	private LocalDateTime upDateTime=LocalDateTime.now();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getOrigin() {
		return origin;
	}

	public void setOrigin(User origin) {
		this.origin = origin;
	}

	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public User getAccepted() {
		return accepted;
	}

	public void setAccepted(User accepted) {
		this.accepted = accepted;
	}

	public String getAcceptedId() {
		return acceptedId;
	}

	public void setAcceptedId(String acceptedId) {
		this.acceptedId = acceptedId;
	}

	public String getAiles() {
		return ailes;
	}

	public void setAiles(String ailes) {
		this.ailes = ailes;
	}

	public LocalDateTime getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(LocalDateTime createDatetime) {
		this.createDatetime = createDatetime;
	}

	public LocalDateTime getUpDateTime() {
		return upDateTime;
	}

	public void setUpDateTime(LocalDateTime upDateTime) {
		this.upDateTime = upDateTime;
	}
	
	
	


}
