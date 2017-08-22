package net.qintalk.italker.push.bean.api.account;

import java.awt.datatransfer.StringSelection;

import org.springframework.remoting.RemoteTimeoutException;

import com.google.gson.annotations.Expose;

import net.qintalk.italker.push.utils.TextUtil;

public class LoginModel {
	//用户名
	@Expose
	private String account;
	//密码
	@Expose
	private String password;
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
	
	public String getPushId() {
		return pushId;
	}
	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	/**
	 * 判断传入的model是否为空并且里面的值是否为空
	 * @param model 登陆model
	 * @return 都不为空为true,其中一个为空为false
	 */
	public static boolean checkisNull(LoginModel model)
	{
		return model!=null
				&&TextUtil.StringNotEmpty(model.getAccount())
				&&TextUtil.StringNotEmpty(model.getPassword());
	}
	
}
