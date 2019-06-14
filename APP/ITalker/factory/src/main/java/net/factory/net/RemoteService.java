package net.factory.net;

import net.factory.model.api.MessageCreateModel;
import net.factory.model.api.account.AccountRsqModel;
import net.factory.model.api.account.LoginModel;
import net.factory.model.api.account.RegisterModel;
import net.factory.model.base.RspModel;
import net.factory.model.card.GroupCard;
import net.factory.model.card.GroupMemberCard;
import net.factory.model.card.MessageCard;
import net.factory.model.card.UserCard;
import net.factory.model.group.GroupAddModel;
import net.factory.model.group.GroupCreateModel;
import net.factory.model.user.UpdateInfoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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

    /**
     * 拉取好友列表
     * @return  帶有信息的userCard
     */
    @GET("user/contact")
    Call<RspModel<List<UserCard>>> contact();

    /**
     * 通過用戶Id進行添加
     * @param followId 被添加用戶的Id
     * @return 返回一個usercard
     */
    @PUT("user/follow/{followId}")
    Call<RspModel<UserCard>> follow(@Path(encoded = true,value = "followId") String followId);

    /**
     * 進行用戶的Id查詢
     * @param id  用户的
     * @return 被查到的id的信息
     */
    @GET("user/{id}")
    Call<RspModel<UserCard>> getUser(@Path(encoded = true,value = "id" )String  id);

    /**
     * 通过模糊名字查询来搜查用户信息
     * @param name 用户名字
     * @return  返回一些系列用户
     */
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> search(@Path(encoded = true,value = "name" )String  name);

    /**
     * 发送一条消息
     * @return  返回mesaagecard
     */
    @POST("msg")
    Call<RspModel<MessageCard>> push(@Body MessageCreateModel messageCreateModel);

    /**
     * 创建群
     * @return 返回 群card
     */
    @POST("group")
    Call<RspModel<GroupCard>> groupCreate(@Body GroupCreateModel groupCreateModel);


    /**
     * 搜索群
     * @param groupId 群id
     * @return 返回一个群信息
     */
    @POST("group/{groupId}")
    Call<RspModel<GroupCard>> groupFind(@Path("groupId")String groupId);

    /**
     * 搜索群
     * @param name 群名字模糊匹配
     * @return 群信息
     */
    @GET("group/search/{name}")
    Call<RspModel<List<GroupCard>>> groupSearchByName(@Path(value = "name",encoded = true) String name);

    /**
     * 自己的群列表
     * @param date 时间戳
     * @return 群列表
     */
    @GET("group/list/{date}")
    Call<RspModel<List<GroupCard>>> groups(@Path(value = "date",encoded = true)String date);

    /**
     * 群里成员的信息
     * @param groupId 群Id
     * @return 群的成员信息
     */
    @GET("group/{groupId}/members")
    Call<RspModel<List<GroupMemberCard>>> groupMembers(@Path("groupId")String groupId);

    /**
     * 群成员添加
     * @param groupId 群ID
     * @param groupAddModel 被拉取的群成员信息
     * @return 返回群成员信息
     */
    @POST("group/{groupId}/members")
    Call<RspModel<List<GroupMemberCard>>> groupMembersAdd(@Path("groupId")String groupId, @Body GroupAddModel groupAddModel);

}
