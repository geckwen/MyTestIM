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
        	 // ע���߼�����
        	packages("net.qintalk.italker.push.service");
        	//ע��json������
        	register(JacksonJsonProvider.class);
        	//ע����־��¼
        	register(Logger.class);
        }
}
