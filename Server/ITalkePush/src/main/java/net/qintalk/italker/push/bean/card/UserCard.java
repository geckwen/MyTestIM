package net.qintalk.italker.push.bean.card;

import java.io.Serializable;
import java.time.LocalDateTime;


import com.google.gson.annotations.Expose;


import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.utils.Hib;




/**
 * 用户卡片用来交互信息
 * @author CLW
 *
 */
public class UserCard {
	@Expose
    private String id;

	@Expose
    private String name;

	@Expose
    private String phone;
  

	@Expose
    private String portrait;

	@Expose
    private String description;

	@Expose
    private int sex = 0;
	
	@Expose
   //用户信息更新时间
    private LocalDateTime updateAt = LocalDateTime.now();
	
	@Expose
    //用户关注人的数量
    private int follows;

	@Expose
    //有多少人关注的数量
    private int following;
	@Expose
    //我与userCard的关系是怎么样的.
    private boolean isfollow;
    
	public UserCard(final User user)
	{
		this(user, false);
	}

	public UserCard(final User user,final boolean isfollow)
	{
		this.id = user.getId();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.portrait = user.getPortrait();
		this.description = user.getDescription();
		this.updateAt = user.getUpdateAt();
		this.sex = user.getSex();
		this.isfollow = isfollow;
		
		// 得到关注人和粉丝的数量
		//user.getFollowers().size()
		//懒加载会报错,因为没有session
		Hib.queryOnly(session->
		{
			//重新加载一次用户信息
			session.load(user, user.getId());
			//这个时候仅仅只是进行了数量查询,并没有查询整个集合
			//要查询集合,必须在session存在情况下进行遍历
			//或者使用Hibernate.initialize(user.getFollowers())
			follows = user.getFollowers().size();
			following = user.getFollowing().size();
		});
		
	}
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public UserCard setName(String name) {
		this.name = name;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public UserCard setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public int getFollows() {
		return follows;
	}

	public void setFollows(int follows) {
		this.follows = follows;
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public boolean isIsfollow() {
		return isfollow;
	}

	public UserCard setIsfollow(boolean isfollow) {
		this.isfollow = isfollow;
		return this;
	}
    
   
	


}
