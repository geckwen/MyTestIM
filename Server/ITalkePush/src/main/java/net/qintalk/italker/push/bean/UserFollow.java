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
	
	
	


}
