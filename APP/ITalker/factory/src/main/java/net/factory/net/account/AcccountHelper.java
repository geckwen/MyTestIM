package net.factory.net.account;


import android.text.TextUtils;


import net.common.factory.data.DataSource;
import net.factory.R;
import net.factory.main.Factory;
import net.factory.model.DBHelper;
import net.factory.model.api.account.AccountRsqModel;
import net.factory.model.api.account.LoginModel;
import net.factory.model.api.account.RegisterModel;
import net.factory.model.base.RspModel;
import net.factory.model.db.User;
import net.factory.net.NetWork;
import net.factory.net.RemoteService;
import net.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 注册
 * Created by CLW on 2017/8/28.
 */

public class AcccountHelper {

    /**
     *注册接口,异步的调用
     * @param registerModel 传递一个注册的Model
     * @param callback 成功或者失败的接口回调
     */
    public static void register(RegisterModel registerModel, final DataSource.Callback<User> callback){
        //调用我们创建好的retrofit对我们的网络接口作为代理
        RemoteService remoteService = NetWork.getAccountRemoteService();
        //得到一个call
        Call<RspModel<AccountRsqModel>> call = remoteService.accountRegister(registerModel);
        call.enqueue(getCallback(callback));
    }

    /**
     * 登陆接口,异步的调用
     * @param loginModel 传递一个登陆的Model
     * @param callback 成功或者失败的接口回掉
     */
    public static void login(LoginModel loginModel, final DataSource.Callback<User> callback){
        //调用我们创建好的retrofit对我们的网络接口作为代理
        RemoteService remoteService = NetWork.getAccountRemoteService();
        //得到一个call
        Call<RspModel<AccountRsqModel>> call = remoteService.accountLogin(loginModel);
        call.enqueue(getCallback(callback));
    }

    /**
     * 对设备进行绑定
     * @param callback
     */
    public static void bindPush(final DataSource.Callback<User> callback){
           String pushId = Account.getPushId();
            if(TextUtils.isEmpty(pushId))
                return;
        RemoteService remoteService = NetWork.getAccountRemoteService();
        Call call = remoteService.onBind(pushId);
        call.enqueue(getCallback(callback));


    }

    /**
     * 将callback提取出来
     * @param callback
     * @return
     */
    private static Callback getCallback(final DataSource.Callback<User> callback)
    {
        return new Callback<RspModel<AccountRsqModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRsqModel>> call, Response<RspModel<AccountRsqModel>> response) {
                RspModel<AccountRsqModel> responseResult = response.body();
                if(responseResult.success()) {
                    //拿到我们的实体
                    AccountRsqModel model = responseResult.getResult();
                    final User user = model.getUser();
                    //进行数据库的绑定和缓存的绑定
                    //进行事务操作并存储数据
                    if(user!=null){
                        DBHelper.save(User.class,user);
                        Account.saveUser(model);
                    }
                    if(model.isBind()){
                        //然后返回
                        Account.setIsBind(true);
                        if(callback != null)
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
                if(callback != null)
                callback.onDataNotAvaliable(R.string.data_network_error);
            }
        };
    }


}
