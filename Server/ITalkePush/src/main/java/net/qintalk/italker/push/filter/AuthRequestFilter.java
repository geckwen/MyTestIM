package net.qintalk.italker.push.filter;

import java.io.IOException;
import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ContainerRequest;

import net.qintalk.italker.push.bean.api.base.ResponseModel;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.factory.UserFactory;
import net.qintalk.italker.push.utils.LocalUser;
import net.qintalk.italker.push.utils.TextUtil;


/**
 * 用于所有的请求过滤
 * @author CLW
 *
 */
@Provider
public class AuthRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO 获得后续的路径
		String relationPath = ((ContainerRequest)requestContext).getPath(false);
		//检查是否有过滤的接口
		if(relationPath.startsWith("account/login")
				||relationPath.startsWith("account/register"))
		{
			//继续走逻辑
			return ;
		}
		//从请求头里找到token
		String token = requestContext.getHeaders().getFirst("token");
		if(TextUtil.StringNotEmpty(token))
		{
			final User user = UserFactory.findByToken(token);
			if(user!=null)
			{
				//当前请求上下文
//				requestContext.setSecurityContext(new SecurityContext() {
//					
//					@Override
//					public boolean isUserInRole(String role) {
//						// TODO 可以在这里写入用户权限,role是权限名
//						return true;
//					}
//					
//					@Override
//					public boolean isSecure() {
//						// TODO 默认即可，https
//						return false;
//					}
//					
//					@Override
//					public Principal getUserPrincipal() {
//						// TODO User实现Principal接口
//						return user;
//					}
//					
//					@Override
//					public String getAuthenticationScheme() {
//						// TODO Auto-generated method stub
//						return null;
//					}
//				});
//				//写入上下文就拦截
			LocalUser.addLocalUser(user);
			return;
			}
		}
		ResponseModel model = ResponseModel.buildAccountError();
		//构建一个返回
		Response response = Response.status(Response.Status.OK)
				.entity(model).build();
		//停止一个请求继续转发下去，调用该方法后之间返回请求
				//不会走到service
		requestContext.abortWith(response);
	}

	

}
