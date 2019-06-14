package net.qintalk.italker.push.service;

import java.awt.datatransfer.StringSelection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
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

import com.sun.org.apache.bcel.internal.generic.NEW;
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
import net.qintalk.italker.push.provider.LocalDateTimeConverter;
import net.qintalk.italker.push.utils.LocalUser;
import net.qintalk.italker.push.utils.TextUtil;

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
	
	
	/**
	 * 群查找
	 * @param name 搜索参数
	 * @return 群信息列表
	 */
	@Path("/search/{name:(.*)?}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<List<GroupCard>> search(@DefaultValue("") @PathParam("name")String name){
		User self = LocalUser.getLocalUser();
		List<Group> groups = GroupFactory.search(name);
		if(groups != null && groups.size()>0){
			List<GroupCard> groupCards = groups.stream().
					map(group ->{
						GroupMember member = GroupFactory.getMember(self.getId(), group.getId());
						return new GroupCard(group,member);
			}).collect(Collectors.toList());
			return ResponseModel.buildOk(groupCards);
		}
		return ResponseModel.buildOk();
	}
	
	/**
	 * 个人群的列表拉取
	 * @param date 
	 * @return
	 */
	@Path("/list/{date:(.*)?}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<List<GroupCard>> list(@DefaultValue("") @PathParam("date")String date){
		User self = LocalUser.getLocalUser();
		LocalDateTime dateTime = null;
		if(!TextUtil.StringNotEmpty(date)){
			try{
				dateTime = LocalDateTime.parse(date,LocalDateTimeConverter.FORMATTER);
			}catch (Exception e) {
				// TODO: handle exception
				dateTime = null;
			}
		}
		Set<GroupMember> groupMembers = GroupFactory.getMembers(self);
		if( groupMembers==null && groupMembers.size()==0){
			return ResponseModel.buildOk();
		}

		final LocalDateTime finalDateTime = dateTime;
		List<GroupCard> groupCards = (List<GroupCard>) groupMembers.stream()
				.filter(groupMember-> finalDateTime == null 
				|| groupMember.getUpdateAt().isAfter(finalDateTime))
				.map(groupMember-> new GroupCard(groupMember))
				.collect(Collectors.toList());
			
		return ResponseModel.buildOk(groupCards);
	}
	
	/**
	 * 拉取群信息 必须是群成员
	 * @param id 群Id
	 * @return 群信息
	 */
	@Path("/{groupId}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<GroupCard> getGroup(@DefaultValue("") @PathParam("groupId")String id){
		if(TextUtil.StringNotEmpty(id)){
			ResponseModel.buildParameterError();
		}
		User self = LocalUser.getLocalUser();
		GroupMember member = GroupFactory.getMember(self.getId(), id);
		if(member==null){
			return ResponseModel.buildServiceError();
		}
		return ResponseModel.buildOk(new GroupCard(member));
	}
	
	/**
	 * 拉取群内成员信息
	 * @param id
	 * @return
	 */
	@Path("/{groupId}/members")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel<List<GroupMemberCard>> members(@DefaultValue("") @PathParam("groupId")String id){
		
		User self = LocalUser.getLocalUser();
		Group group = GroupFactory.findById(id);
		if(group==null)
			return ResponseModel.buildNotFoundGroupError(null);
		//检查权限
		GroupMember selfMember = GroupFactory.getMember(self.getId(), id);
		if(selfMember==null)
			return ResponseModel.buildNoPermissionError();
		
		Set<GroupMember> groupMembers = GroupFactory.getMembers(group);
		if(groupMembers==null && groupMembers.size()==0)
			return ResponseModel.buildServiceError();
		
		List<GroupMemberCard> groupMemberCards = groupMembers
				.stream()
				.map(groupMember -> {return new GroupMemberCard(groupMember);})
				.collect(Collectors.toList());
		return ResponseModel.buildOk(groupMemberCards);
	}
	
	/**
	 * 群成员拉取 拉取人必须为管理员
	 * @param id 群ID
	 * @param model 拉取的成员
	 * @return
	 */
	@Path("/{groupId}/members")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public  ResponseModel<List<GroupMemberCard>> memeberAdd(@DefaultValue("") @PathParam("groupId")String id,GroupAddModel model){
		
		if(!model.checkIsOK(model))
			return ResponseModel.buildParameterError();
		User self = LocalUser.getLocalUser();
		model.getMembers().remove(self.getId());
		if(model.getMembers().size()==0)
			return ResponseModel.buildParameterError();
		GroupMember selfMember  = GroupFactory.getMember(self.getId(), id);
		if(selfMember==null || selfMember.getPermissionType()==GroupMember.PERMISSION_TYPE_NONE)
			return ResponseModel.buildNoPermissionError();
		
		Group group = GroupFactory.findById(id);
		Set<GroupMember> oldMembers = GroupFactory.getMembers(group);
		Set<String> oldMemberIds = oldMembers
				.stream()
				.map(oldMember->{return oldMember.getId();})
				.collect(Collectors.toSet());
		List<User> insertUsers = new ArrayList<>();
		for(String userId:model.getMembers())
		{
			User user = UserFactory.findById(userId);
			if(user==null)
				continue;
			//判断是否存在老用户
			if(oldMemberIds.contains(userId))
				continue;
			insertUsers.add(user);
		}
		if(insertUsers==null|| insertUsers.size()==0)
			return ResponseModel.buildParameterError();
		
		Set<GroupMember> insertGroupMembers = GroupFactory.addMembers(group,insertUsers);
		
		if(insertGroupMembers==null||insertGroupMembers.size()==0)
			return ResponseModel.buildServiceError();
		List<GroupMemberCard> groupMemberCards = insertGroupMembers
				.stream()
				.map(groupMember -> {return new GroupMemberCard(groupMember);})
				.collect(Collectors.toList());
		
		//进行添加后的信息通知
		//1.老用户显示 XX加入群
		
		PushFactory.pushGroupMemberJoin(oldMembers,groupMemberCards);
		//2.新用户提示 加入XX群
		
		PushFactory.pushJoinGroup(insertGroupMembers);
		
		return ResponseModel.buildOk(groupMemberCards);
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
