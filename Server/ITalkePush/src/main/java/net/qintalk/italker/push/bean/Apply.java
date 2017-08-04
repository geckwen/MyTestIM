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
 * ����
 * @author CLW
 *
 */
public class Apply {
	 public static final int APPLY_TYPE_USER = 1;//����Ϊ�û�
	 public static final int APPLY_TYPE_GROUP = 2; //����ΪȺ�û�
	 
	    //����
		@Id
		//������������Ϊuuid
		@GeneratedValue(generator="uuid")
		//��������uuid����Ϊuuid2
		@GenericGenerator(name="uuid",strategy="uuid2")
		@Column(nullable=false,updatable=false)
		private String id;
		
		//��������
		//eg:�������
		 @Column
		private String description;
		 
		 //���ʱ�����ĸ��������ַ��
		 @Column(columnDefinition="TEXT")
		 private String attach;
		
		 //��Ӷ�������
		 @Column(nullable=false)
		 private int type;
		 
		 //Ŀ��Id,������ǿ�����������������
		 //type->APPLY_TYPE_USER:user.id
		 //type->APPLY_TYPE_GROUP:group:id
		 @Column(nullable=false)
		 private String targetId;
		
		 //��������ʱ���
		@CreationTimestamp
		@Column(nullable=false)
		private LocalDateTime createDatetime = LocalDateTime.now();
			
		//��������ʱ���
		@CreationTimestamp
		@Column(nullable=false)
		private LocalDateTime updateDatetime = LocalDateTime.now();
		
		//�����ˣ���Ϊ�գ�Ϊϵͳ��Ϣ
		//һ���˿�������������
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
