package net.qintalk.italker.push.bean.api.account;

import com.google.gson.annotations.Expose;

import net.qintalk.italker.push.bean.card.UserCard;
import net.qintalk.italker.push.bean.db.User;

public class AccountRsqModel {
	
	//基本信息
	@Expose
	private UserCard user;
	
	//用户登录名
	@Expose
	private String account;
	//当前登录获得一个Token
	//通过Token来获取用户信息
	@Expose
	private String token;
	//是否绑定设备
	@Expose
	private boolean isBind;
	
	public AccountRsqModel(User user)
	{
		//默认不绑定
		this(user, false);
	}
	public AccountRsqModel(User user,boolean isBind){
		this.user = new UserCard(user);
		this.account = user.getPhone();
		this.token = user.getToken();
		this.isBind = isBind;
	}
	
	
	public UserCard getUser() {
		return user;
	}
	public void setUser(UserCard user) {
		this.user = user;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isBind() {
		return isBind;
	}
	public void setBind(boolean isBind) {
		this.isBind = isBind;
	}
	
}
