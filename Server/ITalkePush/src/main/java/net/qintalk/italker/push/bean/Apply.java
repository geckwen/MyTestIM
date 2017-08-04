package net.qintalk.italker.push.bean;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

/**
 * 申请
 * @author CLW
 *
 */
public class Apply {
	 public static final int APPLY_TYPE_USER = 1;//申请为用户
	 public static final int APPLY_TYPE_GROUP = 2; //申请为群用户
	 
	    //主键
		@Id
		//主键生成类型为uuid
		@GeneratedValue(generator="uuid")
		//将生成器uuid更换为uuid2
		@GenericGenerator(name="uuid",strategy="uuid2")
		@Column(nullable=false,updatable=false)
		private String id;
		
		//申请描述
		//eg:我想加你
		 @Column
		private String description;
		 
		 //添加时所带的附件例如地址等
		 @Column(columnDefinition="TEXT")
		 private String attach;
		
		 //添加对象类型
		 @Column(nullable=false)
		 private int type;
		 
		 //目标Id,不进行强关联，不建立主外键
		 //type->APPLY_TYPE_USER:user.id
		 //type->APPLY_TYPE_GROUP:group:id
		 @Column(nullable=false)
		 private String targetId;
		
		 //创建创建时间戳
		@CreationTimestamp
		@Column(nullable=false)
		private LocalDateTime createDatetime = LocalDateTime.now();
			
		//创建更新时间戳
		@CreationTimestamp
		@Column(nullable=false)
		private LocalDateTime updateDatetime = LocalDateTime.now();
		
		//申请人，可为空，为系统信息
		//一个人可以有许多个申请
		@ManyToOne
		@JoinColumn(name="applyId")
		private  User appler;
        @Column(insertable=false,updatable=false)
        private String applyId;
	
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getAttach() {
			return attach;
		}
		public void setAttach(String attach) {
			this.attach = attach;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getTargetId() {
			return targetId;
		}
		public void setTargetId(String targetId) {
			this.targetId = targetId;
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
		public User getAppler() {
			return appler;
		}
		public void setAppler(User appler) {
			this.appler = appler;
		}
		public String getApplyId() {
			return applyId;
		}
		public void setApplyId(String applyId) {
			this.applyId = applyId;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
        
        
}
