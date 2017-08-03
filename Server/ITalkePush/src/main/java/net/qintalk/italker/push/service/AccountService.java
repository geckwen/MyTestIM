package net.qintalk.italker.push.service;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import net.qintalk.italker.push.bean.User;

/**
 * @author CLW
 */

// 127.0.0.1/api/account/
@Path("/account")
public class AccountService {
	
	
	@GET
	@Path("/login")
	public String loginGet()
	{
		return "you are loginget()";
	}

	@POST
	@Path("/login")
	//指定请求和响应都是Json
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User loginPost()
	{
		User user=new User();
		user.setName("小美");
		user.setSex(0);
		return user;
	}
}
