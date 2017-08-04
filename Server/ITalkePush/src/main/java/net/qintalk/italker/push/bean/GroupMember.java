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
 * 群成员
 * @author CLW
 *
 */
@Entity
@Table(name="TB_GROUP_MEMBER")
public class GroupMember {
	public static final int PERMISSION_TYPE_NONE=0;// 普通成员
	public static final int PERMISSION_TYPE_ADMIN=1;// 管理员
	public static final int PERMISSION_TYPE_ADMIN_SU=2;//管理员和创建者
	
	
	public static final int NOTIFY_LEVEL_INVALID = -1;//默认不接收
	public static final int NOTIFY_LEVEL_NONE = 0; //默认通知级别
    public static final int NOTIFY_LEVEL_CLOSE =1 ;//接收消息不提示
	//主键
	@Id
	//主键生成类型为uuid
	@GeneratedValue(generator="uuid")
	//将生成器uuid更换为uuid2
	@GenericGenerator(name="uuid",strategy="uuid2")
	@Column(nullable=false,updatable=false)
	private String id;
	
	//群成员名字别名
	@Column
	private String ailas;
	
	//消息通知级别
	@Column(nullable=false)
	private int notifyLevel = NOTIFY_LEVEL_NONE;
	
	//权限
	@Column(nullable=false)
	private int permissType = PERMISSION_TYPE_NONE;
	
	//群成员
	//在加载群成员时，加载群成员信息
	@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="memberId")
	private User member;
	@Column(updatable=false,nullable=false,insertable=false)
	private String memberId;
	
	//群成员
		//在加载群成员时，加载群成员信息
		@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
		@JoinColumn(name="groupId")
		private Group group;
		@Column(updatable=false,nullable=false,insertable=false)
		private String groupId;
	
	//创建创建时间戳
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime = LocalDateTime.now();
	
	//创建更新时间戳
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
