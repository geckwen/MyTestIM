package net.qintalk.italker.push.bean.api.group;

import java.util.HashSet;
import java.util.Set;

import net.qintalk.italker.push.utils.TextUtil;

/**
 * @author CLW
 *	拉取群成员Model
 */
public class GroupAddModel {
	
	private Set<String> members = new HashSet<String>();

	public Set<String> getMembers() {
		return members;
	}

	public void setMembers(Set<String> members) {
		this.members = members;
	}
	
	public  static boolean checkIsOK(GroupAddModel model)
	{
		return model!=null && (model.members.size()>0||model.members!=null);
	}
}
