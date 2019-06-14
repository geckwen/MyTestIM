package net.factory.data.Helper;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.common.utils.CollectionUtil;
import net.factory.model.base.RspModel;
import net.factory.model.card.UserCard;
import net.factory.model.db.User;
import net.factory.model.view.UserSampleModel;
import net.factory.model.db.User_Table;
import net.factory.net.NetWork;
import net.factory.net.RemoteService;
import net.factory.persistence.Account;
import net.factory.present.Factory;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CLW on 2017/9/18.
 */

public class ContactHelper {
    private  static  final  String TAG = ContactHelper.class.getName();
    /**
     * 拉取联系人
     */
        public static void contact()
        {
           final RemoteService remoteService = NetWork.getAccountRemoteService();
            Call<RspModel<List<UserCard>>> call = remoteService.contact();
            call.enqueue(new Callback<RspModel<List<UserCard>>>() {
                @Override
                public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                    RspModel<List<UserCard>> result = response.body();
                    if(result.success()) {
                        List<UserCard> userCards = result.getResult();
                        if(userCards==null||userCards.size()==0)
                            return;
                        //第一中转化方法
                        UserCard[] userCards1 =CollectionUtil.toArray(userCards,UserCard.class);
                        //第二钟 userCards1=userCards.toArray(new UserCard[0]);
                        Factory.getUserCenter().dispatch(userCards1);

                    }
                }

                @Override
                public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                    Log.e(TAG,"服务器错误");
                }
            });
        }

    /**
     * 查询本地缓存用户通过ID
     * @param userId 用户ID
     * @return 返回一个用户
     */
        public static User findLocalUserById(String userId)
        {
            return  SQLite.select()
                    .from(User.class)
                    .where(User_Table.id .eq(userId))
                    .querySingle();

        }

    /**
     * 进行网络查找用户
     * @param userId 用户Id
     * @return 返回一个用户
     */
        public static User findNetUserById(String userId)  {
           RemoteService service = NetWork.getAccountRemoteService();
            try {
                Response<RspModel<UserCard>> response=service.getUser(userId).execute();
                UserCard userCard = response.body().getResult();
                if(userCard!=null)
                {
                    //TODO 数据库操作刷新 但是不进行通知
                    User user =userCard.build();
                    Factory.getUserCenter().dispatch(userCard);
                    return user;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



    /**
     * 优先从本地拉取数据
     * @param id 用户iD
     * @return 查询得到用户
     */
        public static User searchId(String id)
        {
            User user = findLocalUserById(id);
            if(user==null)
                user = findNetUserById(id);
            return  user;
        }

    /**
     * 优先从网络拉取数据
     * @param id 用户Id
     * @return 查询得到的用户
     */
        public static  User searchIdFirstNet(String id)
        {
            User user = findNetUserById(id);
            if(user==null)
                user = findLocalUserById(id);
            return  user;
        }


    /**
     * 拉取联系人
     * @return 联系人
     */
        public static List<UserSampleModel> getContact(){
            return SQLite.select(User_Table.id.withTable().as("id")
                    , User_Table.name.withTable().as("name")
                    ,User_Table.portrait.withTable().as("portrait"))
                    .from(User.class)
                    .where(User_Table.isFollow.eq(true))
                    .and(User_Table.id.notEq(Account.getUserId()))
                    .orderBy(User_Table.name,true)
                    .limit(100) //最多查询100条数据
                    //转换成这个格式
                    .queryCustomList(UserSampleModel.class);
        }


}
