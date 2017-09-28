package net.qintalk.italker.push.factory;

import net.qintalk.italker.push.bean.api.base.PushModel;
import net.qintalk.italker.push.bean.db.Message;
import net.qintalk.italker.push.bean.db.PushHistory;
import net.qintalk.italker.push.bean.db.User;

/**
 * 消息存储与处理的工具类
 * @author CLW
 *
 */
public class PushFactory {

	/**
	 * 发送一条消息,并在当前的历史存储
	 * @param sender 发送者
	 * @param message 信息
	 */
	public static void pushNewMessage(User sender, Message message) {
		// TODO Auto-generated method stub
		if(message==null||sender==null)
			return;
		
		PushHistory pushHistory = new PushHistory();
		pushHistory.setEntity(message.getAttach());
		
		PushModel pushModel = new PushModel();
		
		
	}

}
