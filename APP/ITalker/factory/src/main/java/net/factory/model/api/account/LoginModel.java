package net.factory.model.api.account;

/**
 * 登陆信息
 * Created by CLW on 2017/9/7.
 */

public class LoginModel {
    //用户名
    private String account;
    //用户密码
    private String password;
    //用户设备ID
    private String pushId;

    public LoginModel(String account, String password, String pushId) {
        this.account = account;
        this.password = password;
        this.pushId = pushId;
    }

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
}
