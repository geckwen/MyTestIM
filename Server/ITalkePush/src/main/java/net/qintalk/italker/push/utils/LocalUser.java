package net.qintalk.italker.push.utils;

import net.qintalk.italker.push.bean.db.User;

public class LocalUser {
	private static ThreadLocal<User> local = new ThreadLocal<User>();
	
	public static User getLocalUser()
	{
		return local.get();
	}
	
	public static void addLocalUser(User user)
	{
		local.set(user);
	}
	
	public static void remove()
	{
		local.remove();
	}
}
