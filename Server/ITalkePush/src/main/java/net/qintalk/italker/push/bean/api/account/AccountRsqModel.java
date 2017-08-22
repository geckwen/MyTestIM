package net.qintalk.italker.push.bean.api.account;

import net.qintalk.italker.push.bean.card.UserCard;
import net.qintalk.italker.push.bean.db.User;

public class AccountRsqModel {
	//基本信息
	private UserCard userCard;
	//用户登录名
	private String account;
	//当前登录获得一个Token
	//通过Token来获取用户信息
	private String token;
	//是否绑定设备
	private boolean isBind;
	
	public AccountRsqModel(User user)
	{
		//默认不绑定
		this(user, false);
	}
	public AccountRsqModel(User user,boolean isBind){
		this.userCard = new UserCard(user);
		this.account = user.getPhone();
		this.token = user.getToken();
		this.isBind = isBind;
	}
	public UserCard getUserCard() {
		return userCard;
	}
	public void setUserCard(UserCard userCard) {
		this.userCard = userCard;
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
