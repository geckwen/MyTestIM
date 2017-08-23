package net.qintalk.italker.push.service;

import javax.ws.rs.Consumes;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.qintalk.italker.push.bean.api.base.ResponseModel;
import net.qintalk.italker.push.bean.card.UserCard;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.bean.user.UpdateInfoModel;
import net.qintalk.italker.push.factory.UserFactory;
import net.qintalk.italker.push.utils.LocalUser;

/**
 * 用户进行数据操作
 * @author CLW
 *
 */
//127.0.0.1/api/user/
@Path("/user")
public class UserService extends BaseService {
	
	/**
	 * 用户进行数据更新接口
	 * @param token
	 * @param model
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<UserCard> updateUser(UpdateInfoModel model)
	{
		if(!UpdateInfoModel.check(model))
		{
			return ResponseModel.buildParameterError();
		}
		User user = LocalUser.getLocalUser();
		//拿到更新后的user
		user = model.updateToUser(user);
		//进行数据库的更新
		user = UserFactory.updateUser(user);
		if(user!=null){
			UserCard userCard = new UserCard(user,true);
			return ResponseModel.buildOk(userCard);
		}else{
			return ResponseModel.buildServiceError();
			}
	}
}
