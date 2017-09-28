package net.qintalk.italker.push.factory;

import org.hibernate.Session;

import net.qintalk.italker.push.bean.api.account.MessageCreateModel;
import net.qintalk.italker.push.bean.db.Group;
import net.qintalk.italker.push.bean.db.Message;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.utils.Hib;
import net.qintalk.italker.push.utils.Hib.Query;

/**
 * 操作数据存储的类
 * @author CLW
 *
 */
public class MessageFactory {
	
	/**
	 * 查询之前是否有这条消息
	 * @param message
	 * @return
	 */
	public static Message findById(String  messageId)
	{
		return Hib.query(session->{
			return session.get(Message.class, messageId);
		});
	}
	
	
	/**
	 * 消息添加
	 * @param sender 发送者
	 * @param receiver 接收者
	 * @param messageCreateModel
	 * @return 返回的message
	 */
	public static Message add(User sender,User receiver,MessageCreateModel messageCreateModel){
		Message message = new Message(sender, receiver, messageCreateModel);
		return save(message);
	}
	
	
	/**
	 * 添加一条数据
	 * @param sender 发送者
	 * @param group 接收群
	 * @param messageCreateModel 消息model
	 * @return 返回的message
	 */
	public  static Message add(User sender,Group group,MessageCreateModel messageCreateModel){
		Message message = new Message(sender, group, messageCreateModel);
		return save(message);
	}
	
	/**
	 * 进行数据库存储
	 * @param message 需要存储的数据
	 * @return 返回已经存储的数据
	 */
	public static Message save(Message message)
	{
		return Hib.query(session->{
			session.save(message);
			//写入数据库
			session.flush();
			//查询数据库
			session.refresh(message);
			return message;
		});
	}
	
}
