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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.sun.tools.classfile.Opcode.Set;

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
	
}
