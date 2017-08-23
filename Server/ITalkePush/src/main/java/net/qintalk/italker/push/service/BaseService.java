package net.qintalk.italker.push.service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import net.qintalk.italker.push.bean.db.User;

public class BaseService {
	//加入一个上下文注解，该注解会给securityContext进行赋值
	//具体的值为我们的拦截器中返回的securityContext
	@Context
	protected SecurityContext securityContext;
	
	//获取自己的信息
	protected User gerSelf()
	{
		return (User)securityContext.getUserPrincipal();
	}
}
