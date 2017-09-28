package net.qintalk.italker.push.factory;

import net.qintalk.italker.push.bean.db.Group;
import net.qintalk.italker.push.utils.Hib;

public class GroupFactory {

	public static Group findById(String receiverId) {
		// TODO Auto-generated method stub
		
		return Hib.query(session->{
			return session.get(Group.class, receiverId);
		});
	}

}
