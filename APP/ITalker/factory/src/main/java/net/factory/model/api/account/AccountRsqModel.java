package net.factory.model.api.account;

import net.factory.model.db.User;

/**
 * 登陆请求信息体
 * Created by CLW on 2017/8/30.
 */

public class AccountRsqModel {
    //基本信息
    private User user;
    //用户登录名
    private String account;
    //当前登录获得一个Token
    //通过Token来获取用户信息
    private String token;
    //是否绑定设备
    private boolean isBind;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public void setBind(boolean bind) {
        isBind = bind;
    }

}
