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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;


/**
 * 群组关系
 * @author CLW
 *
 */
@Entity
@Table(name="TB_GROUP")
public class Group {
	
	//主键id
	@Id
	//主键生成存储的类型为UUID
	@GeneratedValue(generator="uuid")
	//把生成器uuid变更为uuid2,uuid2就是常规的uuid to string
	@GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
	
	//创建创建时间戳
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime = LocalDateTime.now();

	//创建更新时间戳
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime updateDatetime = LocalDateTime.now();
	
	//群描述
	@Column(nullable=false)
	private String description;

	//群名字
	@Column(nullable=false)
	private String name;
	
	//群拥有者
	//fetch：加载方式FetchType.EAGER，急加载
	//意味着加载群的信息的时候必须加载用户的信息
	//casecade：级联级别为all，为无论有什么改变都更新级联
	@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="ownId")
	private User owner;
	@Column(nullable=false,updatable=false,insertable=false)
	private String ownId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getOwnId() {
		return ownId;
	}
	public void setOwnId(String ownId) {
		this.ownId = ownId;
	}
	
	
	
	

}
