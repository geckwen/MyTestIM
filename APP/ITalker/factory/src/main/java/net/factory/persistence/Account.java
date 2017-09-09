package net.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.factory.main.Factory;
import net.factory.model.api.account.AccountRsqModel;
import net.factory.model.db.User;
import net.factory.model.db.User_Table;

/**
 * 登陆的设备信息设置
 * Created by CLW on 2017/9/4.
 */

public class Account {
    private  static  final  String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static  final  String KEY_IS_BIND = "KEY_IS_BIND";
    private  static  final String KEY_USER_ACCOUNT = "KEY_USER_ACCOUNT ";
    private  static  final String KEY_USER_TOKEN = "KEY_USER_TOKEN";
    private  static  final String KEY_USER_ID = "KEY_USER_ID";
    private static String pushId ="test" ;//个推ID
    private static  boolean isBind ;
    //用户的token
    private static String token;
    //用户Id
    private static String userId;
    //用户名
    private static String account;

    /**
     * 将设备ID存储到XML中
     * @param context
     */
   public static void save(Context context)
    {
        //获取持久化
        SharedPreferences.Editor editor = context.getSharedPreferences(Account.class.getName(),context.MODE_PRIVATE).edit();
        editor.putString(KEY_PUSH_ID,pushId)
                .putBoolean(KEY_IS_BIND,isBind)
                .putString(KEY_USER_ACCOUNT,account)
                .putString(KEY_USER_TOKEN,token)
                .putString(KEY_USER_ID,userId);
        editor.commit();
    }
    public  static void load(Context context)
    {
        //获取持久化
        SharedPreferences editor = context.getSharedPreferences(Account.class.getName(),context.MODE_PRIVATE);
        pushId = editor.getString(KEY_PUSH_ID,"");
        isBind = editor.getBoolean(KEY_IS_BIND,false);
        token = editor.getString(KEY_USER_TOKEN,"");
        userId = editor.getString(KEY_USER_ID,"");
        account = editor.getString(KEY_USER_ACCOUNT,"");
    }

    public static String getPushId() {
        return pushId;
    }

    /**
     * 设置并存储id
     * @param pushId 设备ID
     */
    public static void setPushId(String pushId) {
        Account.pushId = pushId;
        save(Factory.getApp());
    }
    public static void setIsBind(boolean isBind)
    {
        Account.isBind = isBind;
        save(Factory.getApp());
    }

    /**
     * 是否是登陆状态
     * @return
     */
    public static boolean isAccount()
    {
        //用户id和token不为空
        return !TextUtils.isEmpty(userId)&& !TextUtils.isEmpty(token);
    }

    /**
     * 是否完善信息
     * @return  完善好信息返回true
     */
    public static  boolean isComplete()
    {
        //判断是否登录状态
        if(isAccount())
        {
            User user = getUser();
            return !TextUtils.isEmpty(user.getDesc()) &&!TextUtils.isEmpty(user.getPortrait())
                    &&user.getSex()!=0;
        }
        return false;
    }

    /**
     * 是否绑定
     * @return  绑定则返回true
     */
    public static boolean isBind()
    {
        return true;
    }

    /**
     * 对自己进行存储
     * @param model AccountRsqModel
     */
    public static void saveUser(AccountRsqModel model)
    {
        Account.account = model.getAccount();
        Account.token = model.getToken();
        Account.userId = model.getUser().getId();
        save(Factory.getApp());
    }


    /**
     * 获取当前用户的登录信息
     * @return
     */
    public static User getUser()
    {

        return  TextUtils.isEmpty(userId)?new User():SQLite.select().from(User.class)
                .where(User_Table.id.eq(userId)).querySingle();
    }

    /**
     * 拿到token值
     * @return
     */
    public  static  String getToken()
    {
        return token;
    }
}
