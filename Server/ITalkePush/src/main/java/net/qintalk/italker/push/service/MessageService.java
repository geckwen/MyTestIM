package net.qintalk.italker.push.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.qintalk.italker.push.bean.api.account.MessageCreateModel;
import net.qintalk.italker.push.bean.api.base.ResponseModel;
import net.qintalk.italker.push.bean.card.MessageCard;
import net.qintalk.italker.push.bean.card.UserCard;
import net.qintalk.italker.push.bean.db.Group;
import net.qintalk.italker.push.bean.db.Message;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.bean.db.UserFollow;
import net.qintalk.italker.push.factory.GroupFactory;
import net.qintalk.italker.push.factory.MessageFactory;
import net.qintalk.italker.push.factory.PushFactory;
import net.qintalk.italker.push.factory.UserFactory;
import net.qintalk.italker.push.utils.LocalUser;

/**
 * 消息服务的接口
 * @author CLW
 *
 */

//127.0.0.1/ITalkePush/api/msg/
@Path("/msg")
public class MessageService {

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<MessageCard> getUser(MessageCreateModel messageCreateModel)
	{
		if(!MessageCreateModel.checkisNull(messageCreateModel))
		{
			return ResponseModel.buildParameterError();
		}
		User sender= LocalUser.getLocalUser();
		Message message = MessageFactory.findById(messageCreateModel.getId());
		if(message!=null)
		{
			return ResponseModel.buildOk(new MessageCard(message));
		}
		if(messageCreateModel.getReceiverType()==Message.TYPE_RECEIVER_GROUP)
		{
			return pushGroup(sender,messageCreateModel);
		}else{
			return pushUser(sender,messageCreateModel);
		}
		
	}

	/**
	 * 推送到用户
	 * @param sender
	 * @param messageCreateModel
	 * @return
	 */
	private ResponseModel<MessageCard> pushUser(User sender, MessageCreateModel messageCreateModel) {
		// TODO Auto-generated method stub
		User receiver = UserFactory.findById(messageCreateModel.getReceiverId());
		if(receiver==null)
			return ResponseModel.buildNotFoundUserError("Can't find receiver user");
		if(receiver.getId().equals(sender.getId()))
			return ResponseModel.buildCreateError(ResponseModel.ERROR_CREATE_MESSAGE);
		Message message = MessageFactory.add(sender, receiver, messageCreateModel);
		return buildPushResponse(sender,message);
	}

	
	//发送到群
	private ResponseModel<MessageCard> pushGroup(User sender, MessageCreateModel messageCreateModel) {
		Group group = GroupFactory.findById(sender,messageCreateModel.getReceiverId());
		if(group==null)
			return ResponseModel.buildNotFoundUserError("Can't find receiver user");
		Message message = MessageFactory.add(sender, group, messageCreateModel);
		if(message==null)
			return ResponseModel.buildServiceError();
		return buildPushResponse(sender,message);
	}
	
	private ResponseModel<MessageCard> buildPushResponse(User sender, Message message) {
		// TODO Auto-generated method stub
		if(message==null)
			return ResponseModel.buildServiceError();
		
		PushFactory.pushNewMessage(sender,message);
		return ResponseModel.buildOk(new MessageCard(message));
	}
	
}
