package net.factory.net;

import net.factory.model.api.account.AccountRsqModel;
import net.factory.model.api.account.LoginModel;
import net.factory.model.api.account.RegisterModel;
import net.factory.model.base.RspModel;
import net.factory.model.card.UserCard;
import net.factory.model.user.UpdateInfoModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    /**网络登陆接口
     * @param loginModel 登陆的信息体
     * @return  返回一个rsqModel的登陆信息体
     */
    @POST("account/login")
    Call<RspModel<AccountRsqModel>> accountLogin(@Body LoginModel loginModel);


    /**
     * 绑定设备ID
     * @param pushId 设备ID
     * @return 返回一个rsqModel的登陆信息体
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRsqModel>> onBind(@Path(encoded = true,value = "pushId")String pushId);

    /**
     * 用户更新
     * @param model 用户更新model
     * @return 返回一个rsqModel
     */
    @PUT("user")
    Call<RspModel<UserCard>> UpdateInfo(@Body UpdateInfoModel model);
}
