package net.factory.net.contact;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.common.factory.data.DataSource;
import net.common.tools.UiShow;
import net.factory.R;
import net.factory.main.Factory;
import net.factory.model.DBHelper;
import net.factory.model.base.RspModel;
import net.factory.model.card.UserCard;
import net.factory.model.db.User;
import net.factory.model.db.User_Table;
import net.factory.net.NetWork;
import net.factory.net.RemoteService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CLW on 2017/9/18.
 */

public class ContactHelper {

        public static void contact(final DataSource.Callback<List<UserCard>> callback)
        {
           final RemoteService remoteService = NetWork.getAccountRemoteService();
            Call<RspModel<List<UserCard>>> call = remoteService.contact();
            call.enqueue(new Callback<RspModel<List<UserCard>>>() {
                @Override
                public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                    RspModel<List<UserCard>> result = response.body();
                    if(result.success()) {
                        List<UserCard> userCards = result.getResult();
                        callback.onDataLoader(userCards);
                    }else{
                        Factory.decodeRsqCode(result,callback);
                    }
                }

                @Override
                public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                   callback.onDataNotAvaliable(R.string.data_rsp_error_service);
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
                    User user = userCard.build();
                    DBHelper.save(User.class,user);
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
}
