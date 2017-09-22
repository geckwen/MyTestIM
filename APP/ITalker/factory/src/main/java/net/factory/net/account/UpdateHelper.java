package net.factory.net.account;

import net.common.factory.data.DataSource;
import net.factory.R;
import net.factory.main.Factory;
import net.factory.model.DBHelper;
import net.factory.model.api.account.AccountRsqModel;
import net.factory.model.base.RspModel;
import net.factory.model.card.UserCard;
import net.factory.model.db.User;
import net.factory.model.user.UpdateInfoModel;
import net.factory.net.NetWork;
import net.factory.net.RemoteService;

import java.util.List;
import java.util.ListResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CLW on 2017/9/9.
 */

public class UpdateHelper {
    public static  void upDateInfo(UpdateInfoModel model, final DataSource.Callback<UserCard> callback)
    {
        RemoteService service = NetWork.getAccountRemoteService();
        Call<RspModel<UserCard>> call = service.UpdateInfo(model);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                        RspModel<UserCard> rspModel = response.body();
                        if(rspModel.success()) {
                            UserCard userCard = rspModel.getResult();
                            User user =userCard.build();
                            DBHelper.save(User.class,user);
                            callback.onDataLoader(userCard);
                        }else{
                            Factory.decodeRsqCode(rspModel,callback);
                        }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvaliable(R.string.data_rsp_error_service);
            }
        });
    }

    public static  void contact( final DataSource.Callback<List<UserCard>> callback)
    {
        RemoteService service = NetWork.getAccountRemoteService();
        Call<RspModel<List<UserCard>>>call = service.contact();
        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if(rspModel.success()) {
                    List<UserCard> userCards = rspModel.getResult();

                    callback.onDataLoader(userCards);
                }else{
                    Factory.decodeRsqCode(rspModel,callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                callback.onDataNotAvaliable(R.string.data_rsp_error_service);
            }
        });
    }

    /**
     * 通过模糊搜索进行搜寻
     * @param name 搜索的名字
     * @param callback 回调函数
     * @return 防止多次调用.
     */
    public static  Call search( String name,final DataSource.Callback<List<UserCard>> callback)
    {
        RemoteService service = NetWork.getAccountRemoteService();
        Call<RspModel<List<UserCard>>> call = service.search(name);
        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel =response.body();
                if(rspModel.success())
                {
                    List<UserCard> userCards =rspModel.getResult();
                    callback.onDataLoader(userCards);

                }else{
                    Factory.decodeRsqCode(rspModel,callback);
                }

            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                callback.onDataNotAvaliable(R.string.data_rsp_error_service);
            }
        });
        return call;

    }
    public static  void follow( String id,final DataSource.Callback<UserCard> callback)
    {
        RemoteService service = NetWork.getAccountRemoteService();
        Call<RspModel<UserCard>> call = service.follow(id);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel =response.body();
                if(rspModel.success())
                {
                    UserCard userCard =rspModel.getResult();
                    User user = userCard.build();
                    DBHelper.save(User.class,user);
                    //TODO联系人刷新
                    callback.onDataLoader(userCard);

                }else{
                    Factory.decodeRsqCode(rspModel,callback);
                }

            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvaliable(R.string.data_rsp_error_service);
            }
        });
    }



}
