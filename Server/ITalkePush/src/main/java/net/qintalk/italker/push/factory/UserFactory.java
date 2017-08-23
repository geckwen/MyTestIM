package net.qintalk.italker.push.factory;

import org.hibernate.Session;

import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.utils.Hib;
import net.qintalk.italker.push.utils.Hib.Query;
import net.qintalk.italker.push.utils.TextUtil;

import java.util.List;
import java.util.UUID;

import net.qintalk.italker.push.utils.Hib.QueryOnly;


/**
 * 用户操作
 * @author CLW
 *
 */
public class UserFactory {
	/**
	 * 用token查询信息，改token只能查询自己的信息，不能查询别人的信息
	 * @param phone token
	 * @return	返回一个user
	 */
	public static User findByToken(String token)
	{
		return Hib.query(new Query<User>() {
			@Override
			public User query(Session session) {
				// session进行查询操作
				User user =(User)session.createQuery("from User where token=:token")
				.setParameter("token", token)
				.uniqueResult();
				return user;
			}
		});
	}

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
	 * 登陆查询操作
	 * @param account 用户账户
	 * @param password	用户密码
	 * @return	返回用户信息
	 */
	public static User loginCheck(String account,String password)
	{
		final String pas = password.trim();
		final String encodePas = passwordMd5(pas);
		User user = Hib.query(new Query<User>() {
			@Override
			public User query(Session session) {
				// TODO 进行查询操作
				return (User)session.createQuery("from User where phone=:account "
						+ "and password=:encodePas")
				.setParameter("account", account)
				.setParameter("encodePas", encodePas)
				.uniqueResult();

			}
		});
		if(user != null)
		{
			//进行登陆操作
			user = loginUser(user);
		}
		return user;
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
		User user = createUser(account, password, name);
		if(user !=null)
		{
			user = loginUser(user);
		}
		return user;
	}
	
	/**
	 * 实际用户登录操作
	 * 并进行更新用户操作
	 * @param user 用户
	 * @return 一个带有token的用户
	 */
	public static User loginUser( User user)
	{	
			//用UUID生成一个随机token
		String newToken = UUID.randomUUID().toString();
		//newToken进行base64处理
		newToken = TextUtil.encodeBase64(newToken);
		user.setToken(newToken);
		Hib.query(new Query<User>() {
			@Override
			public User query(Session session) {
				// TODO 进行数据的更新或者存储
				session.saveOrUpdate(user);
				return user;
			}
		});
		return user;
	}
	
	/**
	 * 进行用户的注册操作
	 * @param account	用户名
	 * @param password 加密密码
	 * @param name	用户名字	
	 * @return	一个成功或者空的用户
	 */
	private static User createUser(String account,String password,String name) 
	{

		User user = new User();
		user.setPassword(password);
		user.setName(name);
		//用户账号
		user.setPhone(account);
		return Hib.query(new Query<User>() {
			@Override
			public User query(Session session) {
				 session.save(user);
				 return user;
				
			}
		});
		
	}
	
	/**
	 * 给当前用户绑定pushId
	 * @param user 自己的User
	 * @param pushId 设备ID
	 * @return 返回绑定过的user
	 */
	public static User bindPushId(User user,String pushId){
		//查看这个设备有哪些绑定过
		//解除这些绑定 防止推送错乱
		Hib.queryOnly(new QueryOnly() {
			@Override
			public void query(Session session) {
				// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			List<User> userList=(List<User>)session.createQuery("from User where lower(pushId)=:pushId and Id!=:userID")
				.setParameter("pushId", pushId.toLowerCase())
				.setParameter("userID", user.getId())
				.list();
			for(User u:userList)
			{
				//解除绑定
				u.setPushId(null);
				//进行再更新
				session.saveOrUpdate(u);
			}
			}
		});
		//如果这个设备ID是user的设备ID则继续.
		if(pushId.equalsIgnoreCase(user.getPushId()))
		{
			return user;
		}else{
			//如果这个设备ID不是user之前的设备ID
			//则需要单点登录，让之前的用户进行推出
			//并推送一条消息
			if(TextUtil.StringNotEmpty(user.getPushId()))
			{
				
			}
			//进行更新设备ID
			user.setPushId(pushId);
			return updateUser(user);
			
		}
		
		
		
	}
	
	/**
	 * 进行用户信息更新
	 * @param user 用户
	 * @return 返回一个更新后的用户
	 */
	public static User updateUser(User user)
	{
		return Hib.query(new Query<User>() {
			@Override
			public User query(Session session) {
				// TODO 进行数据更新
				session.saveOrUpdate(user);
				return user;
			}
		});
	}
	/**
	 * 进行MD5加密和Base64加密
	 * @param password
	 * @return
	 */

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
