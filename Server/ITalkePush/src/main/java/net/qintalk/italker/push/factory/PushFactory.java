package net.qintalk.italker.push.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.qintalk.italker.push.bean.api.base.PushModel;
import net.qintalk.italker.push.bean.card.MessageCard;
import net.qintalk.italker.push.bean.db.Group;
import net.qintalk.italker.push.bean.db.GroupMember;
import net.qintalk.italker.push.bean.db.Message;
import net.qintalk.italker.push.bean.db.PushHistory;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.utils.Hib;
import net.qintalk.italker.push.utils.PushDispatcher;
import net.qintalk.italker.push.utils.TextUtil;
import sun.security.util.DisabledAlgorithmConstraints;

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
		MessageCard messageCard = new MessageCard(message);
		//要推送的字符串
		String entity = TextUtil.toJson(messageCard);
		//发送者
		PushDispatcher pushDispatcher  = PushDispatcher.getInstance();
		//给朋友发送消息
		if(message.getGroup()==null&&TextUtil.StringNotEmpty(message.getGroupId()))
		{
			User receiver = UserFactory.findById(message.getReceiverId());
			if(receiver==null)
				return;
			PushHistory pushHistory = new PushHistory();
			pushHistory.setEntityType(PushModel.ENTITY_TYPE_MESSAGE);
			pushHistory.setEntity(entity);
			pushHistory.setReceiver(receiver);
			pushHistory.setReceiverPushId(receiver.getPushId());
			//推送的真实model
			PushModel pushModel = new PushModel();
			//每一条记录都是独立的
			pushModel.add(pushHistory.getEntityType(),pushHistory.getEntity());
			pushDispatcher.add(receiver, pushModel);
			
			//保存到数据库
			Hib.queryOnly(session->{
				session.save(pushHistory);
			});
			
		}else{
			Group group = message.getGroup();
			if(group==null)
				//由于Group采用懒加载 可能没加载,所以再次查询
				group = GroupFactory.findById(message.getReceiverId());
			//如果这次还是为Null则真的为Null
			if(group==null)
				return;
			Set<GroupMember> members = GroupFactory.findMember(group);
			//过滤自己
			members =members.stream().filter(
					member->!member.getId().equalsIgnoreCase(sender.getId()))
			.collect(Collectors.toSet());
			List<PushHistory> histories = new ArrayList<>();
			addGroupMembersPushModel(pushDispatcher,//推送的发送者
					histories,//数据库要存储的列表
					members,//所有的成员
					entity,//要发送的消息
					PushModel.ENTITY_TYPE_MESSAGE);//发送的类型
			
			//pushHistory构建并建立
			for(PushHistory pushHistory:histories)
			{
				Hib.queryOnly(session->{
					session.saveOrUpdate(pushHistory);
				});
			}
			
		}
		
		
		
		pushDispatcher.submit();
		
		
	}

	private static void addGroupMembersPushModel(PushDispatcher pushDispatcher, List<PushHistory> histories,
			Set<GroupMember> members, String entity, int entityTypeMessage) {
		// TODO Auto-generated method stub
		for(GroupMember groupMember:members)
		{
			User receiver = groupMember.getUser();
			if(receiver==null)
				return;
			
			//历史记录字段建立
			PushHistory pushHistory = new PushHistory();
			pushHistory.setEntity(entity);
			pushHistory.setEntityType(entityTypeMessage);
			pushHistory.setReceiver(receiver);
			pushHistory.setReceiverPushId(receiver.getPushId());
			histories.add(pushHistory);
			
			//构建实际model
			PushModel pushModel =new PushModel();
			pushModel.add(pushHistory.getEntityType(), pushHistory.getEntity());
			
			pushDispatcher.add(receiver, pushModel);
		}
		
	}

	public static void pushGroupAdd(Set<GroupMember> groupMembers) {
		// TODO 对每个成员已经加入群的消息
		
	}
	
	

}
