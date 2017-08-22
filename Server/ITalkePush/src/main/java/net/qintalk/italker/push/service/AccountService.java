package net.qintalk.italker.push.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.qintalk.italker.push.bean.api.account.AccountRsqModel;
import net.qintalk.italker.push.bean.api.account.LoginModel;
import net.qintalk.italker.push.bean.api.account.RegisterModel;
import net.qintalk.italker.push.bean.api.base.ResponseModel;
import net.qintalk.italker.push.bean.card.UserCard;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.factory.UserFactory;
import net.qintalk.italker.push.utils.TextUtil;

/**
 * @author CLW
 */

// 127.0.0.1/api/account/
@Path("/account")
public class AccountService {
	
	/**
	 * // 127.0.0.1/api/account/login
	 * 登陆接口 
	 * @param model 传入的模型
	 * @return 返回一个登陆model
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<AccountRsqModel> login(LoginModel model) {
		//检查参数是否正常
		if(!LoginModel.checkisNull(model))
		{
			return ResponseModel.buildParameterError();
		}
		User user = UserFactory.loginCheck(model.getAccount(),model.getPassword());
		if(user != null)
		{
			if(!TextUtil.StringNotEmpty(model.getPushId()))
			{
				return bind(user, model.getPushId());
			}
			AccountRsqModel accountRsqModel = new AccountRsqModel(user,true);
			return ResponseModel.buildOk(accountRsqModel);
		}else{
			//注册异常
			return ResponseModel.buildLoginError();
		}
	}

	/**
	 * // 127.0.0.1/api/account/regin
	 * 注册接口
	 * @param model 注册model
	 * @return 返回一个登陆的Model
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<AccountRsqModel> register(RegisterModel model) {
		if(!RegisterModel.checkisNull(model))
		{
			return ResponseModel.buildParameterError();
		}
		//用户预先查询用户名
		User user = UserFactory.findByName(model.getName().trim());
		if(user != null)
			return ResponseModel.buildHaveNameError();
		user = UserFactory.findByPhone(model.getAccount());
		if(user != null)
			return ResponseModel.buildHaveAccountError();
		//用户进行注册操作
		user = UserFactory.register(model.getAccount(), model.getPassword(), model.getName());
		if(user != null)
		{	
			if(!TextUtil.StringNotEmpty(model.getPushId()))
			{
				return bind(user, model.getPushId());
			}
			AccountRsqModel accountRsqModel = new AccountRsqModel(user,true);
			return ResponseModel.buildOk(accountRsqModel);
		}else{
			//注册异常
			return ResponseModel.buildRegisterError();
		}
		
		
	}

	
	
	
	/**
	 * // 127.0.0.1/api/account/bind/{pushId}
	 * @param token
	 * @param pushId
	 * @return
	 */
	@POST
	@Path("/bind/{pushId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//从请求头去获取token值
	//从url去获取pushId值
	public ResponseModel<AccountRsqModel> bind(@HeaderParam("token")String token,
												@PathParam("pushId")String pushId) {
			//检查参数是否正常
		if(!TextUtil.StringNotEmpty(token)||
				!TextUtil.StringNotEmpty(pushId))
		{
			return ResponseModel.buildParameterError();
		}
		User user = UserFactory.findByToken(token);
		if(user != null)
		{	
			return bind(user,pushId);
		}
		else{
			//注册异常
			return ResponseModel.buildAccountError();
		}
	}

	
	/**
	 * 进行设备绑定
	 * @param self 用户自己
	 * @param pushId 设备ID
	 * @return 响应的model
	 */
	private ResponseModel<AccountRsqModel> bind(User self,String pushId)
	{
		User user = UserFactory.bindPushId(self, pushId);
		if(user!=null){
		//绑定pushId
		AccountRsqModel accountRsqModel = new AccountRsqModel(user,true);
		return ResponseModel.buildOk(accountRsqModel);
		}else{
			return ResponseModel.buildServiceError();
		}
	
	}
}
