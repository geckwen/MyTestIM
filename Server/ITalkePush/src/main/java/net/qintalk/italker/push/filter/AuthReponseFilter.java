package net.qintalk.italker.push.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;



import net.qintalk.italker.push.utils.LocalUser;

public class AuthReponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		// TODO Auto-generated method stub
		if(LocalUser.getLocalUser()!=null)
		LocalUser.remove();
		
		return;
		
	}
	
}
