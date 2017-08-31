package net.factory.net.account;

import net.common.factory.data.DataSource;
import net.factory.R;
import net.factory.main.Factory;
import net.factory.model.api.account.AccountRsqModel;
import net.factory.model.api.account.RegisterModel;
import net.factory.model.base.RspModel;
import net.factory.model.db.User;
import net.factory.net.NetWork;
import net.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CLW on 2017/8/28.
 */

public class RegisterHelper {

    /**
     *注册接口,异步的调用
     * @param registerModel 传递一个注册的Model
     * @param callback 成功或者失败的接口回调
     */
    public static void register(RegisterModel registerModel, final DataSource.Callback<User> callback){
        //调用我们创建好的retrofit对我们的网络接口作为代理
        RemoteService remoteService = NetWork.getRetrofit().create(RemoteService.class);
        //得到一个call
        Call<RspModel<AccountRsqModel>> call = remoteService.accountRegister(registerModel);
        call.enqueue(new Callback<RspModel<AccountRsqModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRsqModel>> call, Response<RspModel<AccountRsqModel>> response) {
                //请求成功
                //从返回中获得我们的Model
                RspModel<AccountRsqModel> responseResult = response.body();
                if(responseResult.success()) {
                    //拿到我们的实体
                    AccountRsqModel model = responseResult.getResult();
                    if(model.isBind()){
                    User user = model.getUser();
                    //进行数据库的绑定和缓存的绑定
                    //然后返回
                    callback.onDataLoader(user);
                    }else {
                        //进行绑定
                        bindPush(callback);
                    }
                }
                else {
                    //对返回的rsomodel的失败的code进行解析,解析到对应的res对应的资源
                    Factory.decodeRsqCode(responseResult,callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRsqModel>> call, Throwable t) {
                //请求失败
                callback.onDataNotAvaliable(R.string.data_network_error);
            }
        });
    }

    /**
     * 对设备进行绑定
     * @param callback
     */
    public static void bindPush(final DataSource.Callback<User> callback){

    }


}
