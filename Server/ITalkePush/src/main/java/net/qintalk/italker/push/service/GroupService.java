package net.qintalk.italker.push.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.api.Self;
import org.hibernate.jpa.event.spi.jpa.ExtendedBeanManager.LifecycleListener;

import com.sun.prism.GraphicsPipeline;

import net.qintalk.italker.push.bean.api.base.ResponseModel;
import net.qintalk.italker.push.bean.api.group.GroupAddModel;
import net.qintalk.italker.push.bean.api.group.GroupCreateModel;
import net.qintalk.italker.push.bean.api.group.GroupMemberUpdateModel;
import net.qintalk.italker.push.bean.card.ApplyCard;
import net.qintalk.italker.push.bean.card.GroupCard;
import net.qintalk.italker.push.bean.card.GroupMemberCard;
import net.qintalk.italker.push.bean.db.Group;
import net.qintalk.italker.push.bean.db.GroupMember;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.factory.GroupFactory;
import net.qintalk.italker.push.factory.PushFactory;
import net.qintalk.italker.push.factory.UserFactory;
import net.qintalk.italker.push.utils.LocalUser;

@Path("/group")
public class GroupService {
	
	/**
	 * 群创建接口 
	 * @param model 群创建的
	 * @return 返回群信息卡片
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<GroupCard> create(GroupCreateModel model){
		if(GroupCreateModel.checkIsOK(model))
		{
			return ResponseModel.buildParameterError();
		}
		User self = LocalUser.getLocalUser();
		model.getMembers().remove(self.getId());
		if(model.getMembers().size()==0)
		{
			return ResponseModel.buildParameterError();
		}
		if(GroupFactory.findByName(model.getName())!=null)
		{
			return ResponseModel.buildHaveNameError();
		}
		List<User> users = new LinkedList<>();
		for(String userId:model.getMembers())
		{
			User user = UserFactory.findById(userId);
			if(user==null)
				continue;
			users.add(user);
		}
		
		if(users.size()==0)
		{
			return ResponseModel.buildParameterError();
		}
		
		Group group =GroupFactory.create(self,model,users);
		if(group==null)
		{
			return ResponseModel.buildServiceError();
		}
		GroupMember creatorMember = GroupFactory.getMember(self.getId(),group.getId());
		if(creatorMember==null)
		{
			return ResponseModel.buildServiceError();
		}
		Set<GroupMember> groupMembers = GroupFactory.getMembers(group);
		groupMembers = groupMembers.stream().filter(groupMmber->{
			return !groupMmber.getId().equalsIgnoreCase(creatorMember.getId());
		}).collect(Collectors.toSet());
				
		if(groupMembers==null)
		{
			return ResponseModel.buildServiceError(); 
		}
		
		PushFactory.pushGroupAdd(groupMembers);
		
		return ResponseModel.buildOk(new GroupCard(group));
	}
	
	
	@Path("/search/{name:(.*)?}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<List<GroupCard>> search(@DefaultValue("") @PathParam("name")String name){
		
		return null;
	}
	
	@Path("/search/{date:(.*)?}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<List<GroupCard>> list(@DefaultValue("") @PathParam("date")String date){
		return null;
		
	}
	
	@Path("/{groupId}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<GroupCard> getGroup(@DefaultValue("") @PathParam("groupId")String id){
		return null;
	}
	
	@Path("/{groupId}/members")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<List<GroupMemberCard>> members(@DefaultValue("") @PathParam("groupId")String id){
		return null;
	}
	
	@Path("/{groupId}/members")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public  ResponseModel<List<GroupMemberCard>> memeberAdd(@DefaultValue("") @PathParam("groupId")String id,GroupAddModel model){
			return null;
	}
	
	@Path("/members/{memberId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public  ResponseModel<GroupMemberCard> modiftMember(@DefaultValue("") @PathParam("memberId")String id,GroupMemberUpdateModel model){
		return null;
	}
	
	@Path("/applyJoin/{groupId}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<ApplyCard> join(@DefaultValue("") @PathParam("groupId")String id){
		return null;
	}
	
}
