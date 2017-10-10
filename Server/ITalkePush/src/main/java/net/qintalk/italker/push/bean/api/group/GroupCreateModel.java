package net.qintalk.italker.push.bean.api.group;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

import net.qintalk.italker.push.utils.TextUtil;



/**
 * 群创建时的信息
 * @author CLW
 *
 */
public class GroupCreateModel {
	@Expose
	private String name;
	@Expose
	private String des;
	@Expose
	private String portrait;
	@Expose
	private Set<String> members = new HashSet<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public Set<String> getMembers() {
		return members;
	}
	public void setMembers(Set<String> members) {
		this.members = members;
	}
	public  static boolean checkIsOK(GroupCreateModel model)
	{
		return model!=null && TextUtil.StringNotEmpty(model.name)
				&&TextUtil.StringNotEmpty(model.des)
				&&TextUtil.StringNotEmpty(model.portrait)
				&&(model.members.size()>0||model.members!=null);
	}
	
	
}
