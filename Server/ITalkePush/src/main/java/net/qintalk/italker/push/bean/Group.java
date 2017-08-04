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
 * Ⱥ���ϵ
 * @author CLW
 *
 */
@Entity
@Table(name="TB_GROUP")
public class Group {
	
	//����id
	@Id
	//�������ɴ洢������ΪUUID
	@GeneratedValue(generator="uuid")
	//��������uuid���Ϊuuid2,uuid2���ǳ����uuid to string
	@GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
	
	//��������ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createDatetime = LocalDateTime.now();

	//��������ʱ���
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime updateDatetime = LocalDateTime.now();
	
	//Ⱥ����
	@Column(nullable=false)
	private String description;

	//Ⱥ����
	@Column(nullable=false)
	private String name;
	
	//Ⱥӵ����
	//fetch�����ط�ʽFetchType.EAGER��������
	//��ζ�ż���Ⱥ����Ϣ��ʱ���������û�����Ϣ
	//casecade����������Ϊall��Ϊ������ʲô�ı䶼���¼���
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
