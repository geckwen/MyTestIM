package net.factory.model.card;



import net.factory.model.db.User;

import java.util.Date;







/**
 * 用户卡片用来交互信息
 * @author CLW
 *
 */
public class UserCard {

    private String id;

    private String name;

    private String phone;
  

    private String portrait;

    private String description;


    private int sex = 0;
	

   //用户信息更新时间
    private Date updateAt ;
	

    //用户关注人的数量
    private int follows;


    //有多少人关注的数量
    private int following;

    //我与userCard的关系是怎么样的.
    private boolean isfollow;
    
	public UserCard(final User user)
	{
		this(user, false);
	}

	public UserCard(final User user, final boolean isfollow)
	{
		this.id = user.getId();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.portrait = user.getPortrait();
		this.description = user.getDesc();
		this.updateAt = user.getModifyAt();
		this.sex = user.getSex();
		this.isfollow = isfollow;
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


	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public boolean isfollow() {
		return isfollow;
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

	//缓存一个对应的user,不能被gson框架解析使用
	private transient User user;

	public  User build()
	{
		if(user == null)
		{
			User user =new User();
			user.setId(id);
			user.setName(name);
			user.setPhone(phone);
			user.setPortrait(portrait);
			user.setDesc(description);
			user.setModifyAt(updateAt);
			user.setSex(sex);
			this.user = user;
		}
		return user;
	}
   
	


}
