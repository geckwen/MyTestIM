package net.qintalk.italker.push.bean.api.account;

import com.google.gson.annotations.Expose;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.PrivateKeyResolver;

import net.qintalk.italker.push.utils.TextUtil;

public class RegisterModel {
	@Expose
	private String account;
	@Expose
	private String password;
	@Expose
	private String name;
	@Expose
	private String pushId;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPushId() {
		return pushId;
	}
	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	public static boolean checkisNull(RegisterModel model)
	{
		return model!=null
				&&TextUtil.StringNotEmpty(model.getAccount())
				&&TextUtil.StringNotEmpty(model.getPassword())
				&&TextUtil.StringNotEmpty(model.getName());
	}
	
}
