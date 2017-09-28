package net.qintalk.italker.push.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;


import net.qintalk.italker.push.bean.api.base.PushModel;
import net.qintalk.italker.push.bean.db.User;

public class PushDispatcher {
	// 定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
	private final static String appId = "u4PkFhl0OW6yIANu3JvuM9";
	private final static String appKey = "UFqK0fVsum7P7ah6hKRcMA";
	private final static String masterSecret = "ioy5SGLML9AAaTlW1bMpT6";
	private final static String url = "http://sdk.open.api.igexin.com/apiex.htm";

	
	// 要收到消息的人和内容的列表
	private static List<BeanBatch> beanBatchs = new ArrayList<>();
	// 初始化
	private static IGtPush push;

	private PushDispatcher() {
		push = new IGtPush(url, appKey, masterSecret);
	}

	private static class PushDiaspatcherinner {
		private static PushDispatcher iPushDispatcher = new PushDispatcher();
	}

	public  static PushDispatcher getInstance() {
		return PushDiaspatcherinner.iPushDispatcher;
	}

	/**
	 * 添加一個消息
	 * @param recevice 接收者
	 * @param pushModel 接受的信息
	 * @return 
	 * @throws Exception
	 */
	public  boolean add(User recevice, PushModel pushModel) {
		if(recevice==null||!TextUtil.StringNotEmpty(recevice.getPushId())||pushModel==null)
			return false;
		
		String msg = pushModel.getPushString();
		if(!TextUtil.StringNotEmpty(msg))
			return false;
		
		BeanBatch beanBatch=null;
		try {
			beanBatch = constructClientTransMsg(recevice.getPushId(), msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beanBatchs.add(beanBatch);
		return true;
	}

	/**
	 * 构建一个信息
	 * @param cid 接收者的pushId
	 * @param msg 接收的信息
	 * @return 
	 * @throws Exception
	 */
	private  BeanBatch constructClientTransMsg(String cid, String msg) throws Exception {
		SingleMessage message = new SingleMessage();
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionContent(msg);// 传递的消息
		template.setTransmissionType(0); // 这个Type为int型，填写1则自动启动app
		message.setData(template);
		message.setOffline(true); // 是否离线发送
		message.setOfflineExpireTime(24 * 3600 * 1000);// 离线时常
		// 设置推送目标，填入appid和clientId
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(cid);
		BeanBatch beanBatch = new BeanBatch(message, target);
		return beanBatch;
	}

	/**
	 * 发送消息
	 * @return 成功发送则会true 否则为false
	 */
	public boolean submit() {
		IBatch batch = push.getBatch();
		//判断是否有数据
		boolean isHaveData = false;
		for (BeanBatch beanBatch : beanBatchs) {
			try {
				batch.add(beanBatch.message, beanBatch.target);
				isHaveData = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!isHaveData) {
			return false;
		}
		IPushResult iPushResult=null;
		try {
			iPushResult =batch.submit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				batch.retry();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(iPushResult!=null)
		{
			
			return true;
		}
		
		
		return false;
		
	}



	private static class BeanBatch {
		public SingleMessage message;
		public Target target;

		public BeanBatch(SingleMessage message, Target target) {
			// TODO Auto-generated constructor stub
			this.target = target;
			this.message = message;
		}

	}

}
