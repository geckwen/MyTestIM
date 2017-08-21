package net.qintalk.italker.push.factory;

import org.hibernate.Session;

import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.utils.Hib;
import net.qintalk.italker.push.utils.Hib.Query;
import net.qintalk.italker.push.utils.TextUtil;

public class UserFactory {
	
	/**
	 * 查询账号是否存在	
	 * @param phone 账号
	 * @return	返回一个user
	 */
	public static User findByPhone(String phone)
	{
		return Hib.query(new Query<User>() {
			@Override
			public User query(Session session) {
				// session进行查询操作
				User user =(User)session.createQuery("from User where phone=:IPHONE")
				.setParameter("IPHONE", phone)
				.uniqueResult();
				return user;
			}
		});
	}
	
	/**
	 * 查询用户名字是否存在
	 * @param name 用户名字
	 * @return	user
	 */
	public static User findByName(String name)
	{
		return Hib.query(new Query<User>() {
			@Override
			public User query(Session session) {
				// session进行查询操作
				User user =(User)session.createQuery("from User where name=:NAME")
				.setParameter("NAME", name)
				.uniqueResult();
				return user;
			}
		});
	}
	
	/**
	 * 用户注册
	 * 注册成功后返回一个user,并对数据库写操作
	 * @param account	用户帐号
	 * @param password	用户密码
	 * @return	user
	 */
	public static User register(String account,String password,String name) { 	
		//去除头尾空格
		account = account.trim();
		//进行MD5操作
		password = passwordMd5(password);
		User user = new User();
		user.setPassword(password);
		user.setName(name);
		//用户账号
		user.setPhone(account);
		//开启一个会话
		Session session = Hib.session();
		//开启事务
		session.beginTransaction();
		try {
			session.save(user);
			//提交事务
			session.getTransaction().commit();
			return user;
		} catch (Exception e) {
			//事务没有成功提交，回滚之前状态
			session.getTransaction().rollback();
			return null;
			}			
	}
	
	private static String passwordMd5(String password)
	{
		//去除头尾空格
		password = password.trim();
		//这里可以用一个token来结合,在项目整合后可以进行这个操作,进行MD5加密
		String passwordMd5 = TextUtil.getMD5(password);
		//再进行一次对称的BASE64的加密
		return TextUtil.encodeBase64(passwordMd5);
	}
}
