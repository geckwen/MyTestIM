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
 * 用户的Model,对应数据库
 * @author CLW
 *
 */
@Entity
@Table(name="TB_USER")
public class User { 
	//这是一个主键
	@Id
	@PrimaryKeyJoinColumn
	//主键生成存储的类型为UUID
	@GeneratedValue(generator="uuid")
	//把uuid的生成器定义为uuid2,uuid2是常规的UUID toString
	@GenericGenerator(name="uuid",strategy="uuid2")
	@Column(updatable=false,nullable=false)
    private String id;
	
	//用户名必须唯一
	@Column(nullable=false,length=128,unique=true)
	private String userName;
	//手机必须唯一
	@Column(nullable=false,length=64,unique=true)
	private String phone;
	//用户密码
	@Column(nullable=false)
	private String password;
	//用户头像
	@Column
	private String portrait;
	//用户描述
	@Column
	private String description;
	//用户性别
	@Column(nullable=false)
	private int sex=0;
	//token是唯一的
	@Column(unique=true)
	private String token;
	
	//用于推送的设备ID
	@Column
	private String pushId;
	
	//定义为创建时间戳
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime=LocalDateTime.now();
	
	//定义为更新时间戳
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime upDatetime=LocalDateTime.now();
	
	//定义为创建时间戳
	@CreationTimestamp
	@Column
	private LocalDateTime lastReceiverTime=LocalDateTime.now();
	
	//关注用户表
	//对应的数据库表字段为TB_UserFollow.originId
	@JoinColumn(name="originId")
	//定义为懒加载，默认加载User信息的时候，并不查询这个集合
	@LazyCollection(LazyCollectionOption.EXTRA)
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private HashSet<UserFollow> originList=new HashSet<UserFollow>();
	
	//被关注用户表
	//对应的数据库表字段为TB_UserFollow.acceptedId
	@JoinColumn(name="acceptedId")
	//定义为懒加载，默认加载User信息的时候，并不查询这个集合
	@LazyCollection(LazyCollectionOption.EXTRA)
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private HashSet<UserFollow> acceptedList=new HashSet<UserFollow>();
	
}
