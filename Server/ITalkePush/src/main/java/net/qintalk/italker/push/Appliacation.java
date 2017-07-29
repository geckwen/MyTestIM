package net.qintalk.italker.push;

import java.util.logging.Logger;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;





/**
 * @author CLW
 */
public class Appliacation extends ResourceConfig {
        public Appliacation()
        {   
        	 // 注册逻辑处理
        	packages("net.qintalk.italker.push.service");
        	//注册json解析器
        	register(JacksonJsonProvider.class);
        	//注册日志记录
        	register(Logger.class);
        }
}
