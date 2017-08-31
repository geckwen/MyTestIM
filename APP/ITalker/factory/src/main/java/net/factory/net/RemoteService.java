package net.factory.net;

import net.factory.model.api.account.AccountRsqModel;
import net.factory.model.api.account.RegisterModel;
import net.factory.model.base.RspModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 所有网络的接口
 * Created by CLW on 2017/8/30.
 */

public interface RemoteService {
    /**
     * 网络请求注册接口
     * @param registerModel 注册的信息体
     * @return 返回一个rspModel的登录信息体
     */
    @POST("account/register")
    Call<RspModel<AccountRsqModel>> accountRegister(@Body RegisterModel registerModel);
}
