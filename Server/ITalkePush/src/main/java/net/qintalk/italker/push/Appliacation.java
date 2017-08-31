package net.qintalk.italker.push;

import java.util.logging.Logger;

import org.glassfish.jersey.server.ResourceConfig;

import net.qintalk.italker.push.filter.AuthReponseFilter;
import net.qintalk.italker.push.filter.AuthRequestFilter;
import net.qintalk.italker.push.provider.GsonProvider;


/**
 * @author CLW
 */
public class Appliacation extends ResourceConfig {
        public Appliacation()
        {   
        	// 注册逻辑处理的包名
        	packages("net.qintalk.italker.push.service");
        	// 注册Json解析器
        	//register(JacksonJsonProvider.class);
        	//注册Gson解析器
        	register(GsonProvider.class);
        	//注册过滤器
        	register(AuthRequestFilter.class);
/*        	//响应拦截器
        	register(AuthReponseFilter.class);*/
        	// 注册日志打印输出
        	register(Logger.class);
        }
}
