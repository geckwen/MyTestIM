package net.qintalk.italker.push.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.qintalk.italker.push.bean.api.account.RegisterModel;
import net.qintalk.italker.push.bean.card.UserCard;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.factory.UserFactory;

/**
 * @author CLW
 */

// 127.0.0.1/api/account/
@Path("/account")
public class AccountService {

	@POST
	@Path("/register")
	// 注册
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserCard loginPost(RegisterModel model) {
		UserCard userCard = new UserCard();
		//用户查询用户名
		User user = UserFactory.findByName(model.getName());
		if (user != null) {
			return userCard.setName("已存在用户名字");
		} else {
			if ((user = UserFactory.findByPhone(model.getAccount())) != null) {
				return userCard.setPhone("已存在phone");
			}
		}
		//用户进行注册操作
	user = UserFactory.register(model.getAccount(), model.getPassword(), model.getName());
		if (user != null) {
			return userCard.setName(user.getName()).setPhone(user.getPhone()).setIsfollow(true);
		}

		return null;
	}
}
