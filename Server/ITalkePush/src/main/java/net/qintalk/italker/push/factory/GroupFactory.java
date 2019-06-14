package net.qintalk.italker.push.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.metamodel.SetAttribute;

import org.hibernate.Session;

import net.qintalk.italker.push.bean.api.group.GroupCreateModel;
import net.qintalk.italker.push.bean.db.Group;
import net.qintalk.italker.push.bean.db.GroupMember;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.utils.Hib;
import net.qintalk.italker.push.utils.TextUtil;
import net.qintalk.italker.push.utils.Hib.Query;

public class GroupFactory {

	/**
	 * 通过群id去拿群model
	 * @param groupId 群ID
	 * @return  群model 
	 */
	public static Group findById(String groupId) {
		
		
		return Hib.query(session->{
			return session.get(Group.class, groupId);
		});
	}

	public static Set<GroupMember> findMember(Group group) {
		// TODO 查询某个群里的所有成员
		return null;
	}

	/**
	 * 查询某个群,并且查询是否属于他是否已经在群里面
	 * @param user 用户
	 * @param groupId 群ID
	 * @return 返回一个群model
	 */
	public static Group findById(User user, String groupId) {
		GroupMember member = getMember(user.getId(), groupId);
		if(member!=null){
			//由于
			return member.getGroup();
		}
		return null;
	}

	/**
	 * 通過群名字来查询群
	 * @param name 群名字
	 * @return 群Model
	 */
	public static Group findByName(String name) {
		return Hib.query(new Query<Group>() {
			@Override
			public Group query(Session session) {
				// TODO Auto-generated method stub
				return (Group) session.createQuery("from Group where lower (name)=:name")
				.setParameter("name", name.toLowerCase())
				.uniqueResult();
			}
		});
		
	}

	/**
	 * 创建一个群
	 * @param self 自己
	 * @param model 创建群的信息
	 * @param users 群里面的成员
	 * @return 返回一个群model
	 */
	public static Group create(User self, GroupCreateModel model, List<User> users) {
		return Hib.query(new Query<Group>() {

			@Override
			public Group query(Session session) {
				Group group = new Group(self,model);
				session.save(group);
				GroupMember ownerMember = new GroupMember(self,group);
				ownerMember.setPermissionType(GroupMember.PERMISSION_TYPE_ADMIN_SU);
				session.save(ownerMember);
	
				for(User user:users)
				{
					GroupMember member = new GroupMember(user, group);
					session.save(member);
				}
				return group;
			}
		});
		
	}

	/**
	 * 获取一个群的成员
	 * @param userId 成员的id
	 * @param groupId 群id
	 * @return 返回一个groupMember
	 */
	public static GroupMember getMember(String userId, String groupId) {
		return Hib.query(new Query<GroupMember>() {

			@Override
			public GroupMember query(Session session) {
			return	(GroupMember) session.createQuery("from GroupMember where userId=:userId and groupId=:groupId")
				.setParameter("userId", userId)
				.setParameter("groupId", groupId)
				.setMaxResults(1)
				.uniqueResult();
				
			}
		});
	}

	/**
	 * 查询群成员
	 * @param group 群
	 * @return 返回一个set群成员集合
	 */
	public static Set<GroupMember> getMembers(Group group) {
		 return Hib.query(new Query<Set<GroupMember>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Set<GroupMember> query(Session session) {
				List<GroupMember> groupMembers = session.createQuery("from GroupMember where group=:group")
				.setParameter("group", group)
				.list();
				return new HashSet<>(groupMembers);
			}
			
		});
		
	}

	public static Set<GroupMember> getMembers(User self) {
		// TODO Auto-generated method stub
		return Hib.query(new Query<Set<GroupMember>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Set<GroupMember> query(Session session) {
				List<GroupMember> groupMembers = session.createQuery("from GroupMember where userId=:userId")
				.setParameter("userId", self.getId())
				.list();
				return new HashSet<>(groupMembers);
			}
			
		});
	}
	
	public static List<Group> search(String name) {
		// TODO Auto-generated method stub
		if(!TextUtil.StringNotEmpty(name))
			name = "";
		String searchName = "%"+name+"%";
		return Hib.query(new Query<List<Group>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Group> query(Session session) {
				// 设置只能查询20条
				return  session.createQuery("from Group where lower(name) like :name")
				.setParameter("name", searchName)
				.setMaxResults(20)
				.list();
				
			}
		});
	}

	
	/**
	 * 添加群用户成员
	 * @param group 具体群
	 * @param insertUsers 插入的群成员
	 * @return
	 */
	public static Set<GroupMember> addMembers(Group group, List<User> insertUsers) {
		// TODO Auto-generated method stub
		return Hib.query(session->{
			Set<GroupMember> groupMembers = new HashSet<>();
			for(User user:insertUsers)
			{
				GroupMember groupMember = new GroupMember(user, group);
				session.save(groupMember);
				groupMembers.add(groupMember);
			}
			//进行数据刷新
			
			return groupMembers;
		});
	}

	

}
