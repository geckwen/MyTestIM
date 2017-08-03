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
	
	//关注发起者,一但发起关注，必须创建而且必须要有关注者，而这个中间件Follow也创建了
	//你可以关注多人，每一次关注都是一条信息
	//这里的many指的是Follow
	@ManyToOne(optional=false)
	//定义关联的表字段名为originId,对应user.id
	//定义数据的数据库的存储字段
	@JoinColumn(name="originId")
	private User origin;
	//不允许为null,不允许更新，插入
	@Column(nullable=false,updatable=false,insertable=false)
	private String originId;
	
	
	//被关注者一定存在，可以被多个关注着关注
	@ManyToOne(optional=false)
	//定义关联的表字段名为acceptedId,对应user.id
	//定义数据的数据库的存储字段
	@JoinColumn(name="acceptedId")
	private User accepted;
	//不允许为null,不允许更新，插入
	@Column(nullable=false,updatable=false,insertable=false)
	private String acceptedId;
	//被关注者别名，可以为null
	@Column
	private String ailes;
	
	//创建时间
	@Column(nullable=false)
	@CreationTimestamp
	private LocalDateTime createDatetime=LocalDateTime.now();
	
	//更新时间
	@Column(nullable=false)
	@CreationTimestamp
	private LocalDateTime upDateTime=LocalDateTime.now();
	
	
	


}
