package net.qintalk.italker.push.factory;

import java.util.Set;

import net.qintalk.italker.push.bean.db.Group;
import net.qintalk.italker.push.bean.db.GroupMember;
import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.utils.Hib;

public class GroupFactory {

	public static Group findById(String receiverId) {
		// TODO 查询某个群
		
		return Hib.query(session->{
			return session.get(Group.class, receiverId);
		});
	}

	public static Set<GroupMember> findMember(Group group) {
		// TODO 查询某个群里的所有成员
		return null;
	}

	/**
	 * 查询某个群,并且查询是否属于他是否已经在群里面
	 * @param sender
	 * @param receiverId
	 * @return
	 */
	public static Group findById(User sender, String receiverId) {
		// TODO Auto-generated method stub
		return null;
	}

}
