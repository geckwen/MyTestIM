package net.qintalk.italker.push.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.qintalk.italker.push.bean.api.base.PushModel;
import net.qintalk.italker.push.bean.api.base.PushModel.Entity;
import net.qintalk.italker.push.bean.api.base.ResponseModel;
import net.qintalk.italker.push.bean.card.UserCard;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.bean.db.UserFollow;
import net.qintalk.italker.push.bean.user.UpdateInfoModel;
import net.qintalk.italker.push.factory.UserFactory;
import net.qintalk.italker.push.utils.LocalUser;
import net.qintalk.italker.push.utils.PushDispatcher;

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
	
	
	/**
	 * 查询用户关注人列表
	 * @return 返回一个带有用户关注的列表的responsemodel
	 */
	//127.0.0.1/api/user/contract
	@Path("/contact")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<List<UserCard>> contract()
	{
		User self = LocalUser.getLocalUser();
		List<User> users = UserFactory.getFollows(self);
		PushDispatcher pushDispatcher =PushDispatcher.getInstance();
		PushModel pushModel = new PushModel();
		pushModel.add(0, "hello world");
		try {
			pushDispatcher.add(self, pushModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pushDispatcher.submit();
		List<UserCard> userCards = users.stream().map(user->{
			return new UserCard(user,true);
		}).collect(Collectors.toList());
		return ResponseModel.buildOk(userCards) ;

	}
	
	/**
	 * 关注某人
	 * @param followId 关注人的Id
	 * @return 返回是否关注的usercard
	 */
	//127.0.0.1/api/user/follow/{followId}
	@Path("/follow/{followId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<UserCard> getFollow(@PathParam("followId") String followId)
	{
		User self =LocalUser.getLocalUser();
		if(self.getId().equals(followId))
			return ResponseModel.buildOk(null);
		User target= UserFactory.findById(followId);
		if(target==null)
			return ResponseModel.buildNotFoundUserError("用户不存在");		
		User user = UserFactory.follow(self, target);
		if(user == null)
			return ResponseModel.buildServiceError();
		
		//TODO 通知我关注的人 我已经关注了他
		return ResponseModel.buildOk(new UserCard(user,true));
	}
	
	//127.0.0.1/api/user/id
	/**查看某個人的信息
	 * @param id
	 * @return
	 */
	@Path("{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<UserCard> getUser(@PathParam("id") String id)
	{
		if(LocalUser.getLocalUser().getId().equals(id))
			return ResponseModel.buildParameterError();
		User target = UserFactory.findById(id);
		if(target==null)
			return ResponseModel.buildNotFoundUserError(null);
		UserFollow userFollow = UserFactory.getUserFollow(gerSelf(), target);
		if(userFollow!=null)
			return ResponseModel.buildOk(new UserCard(target,true));
		return ResponseModel.buildOk(new UserCard(target));
	}
	
	/**
	 * 通过名字来搜索联系人
	 * @param name 联系人的名人
	 * @return 一个List的联系人集合
	 */
	@Path("/search/{name:(.*)?}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<List<UserCard>> searchName(@DefaultValue("")@PathParam("name") String name)
	{
		User self = LocalUser.getLocalUser();
		List<User> searchs = UserFactory.findByLikeName(name);
		List<User> contracts = UserFactory.getFollows(self);
		List<UserCard> result = searchs.stream().map(user->{
			boolean isFollow = user.getId().equalsIgnoreCase(self.getId())||
					//查询是否里面有自己好友,如果有则显示已关注
					contracts.stream()
					.anyMatch(contract->contract.getId().equalsIgnoreCase(user.getId()));
			return new UserCard(user,isFollow);
		}).collect(Collectors.toList());
		
		return ResponseModel.buildOk(result);
	}
	
	
	
	
}
