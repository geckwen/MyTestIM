package net.factory.data.Helper;

import android.os.UserHandle;
import android.view.ViewDebug;

import com.raizlabs.android.dbflow.sql.language.Join;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.common.factory.data.DataSource;
import net.factory.model.base.RspModel;
import net.factory.model.card.GroupCard;
import net.factory.model.card.GroupMemberCard;
import net.factory.model.db.Group;
import net.factory.model.db.GroupMember;
import net.factory.model.db.User;
import net.factory.model.group.GroupCreateModel;
import net.factory.model.view.MemberUserModel;
import net.factory.net.NetWork;
import net.factory.net.RemoteService;
import net.factory.present.Factory;

import net.factory.present.search.GroupPresent;
import net.lang.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by CLW on 2017/9/22.
 */

public class GroupHelper {
    public static Group findById(String groupId) {
        Group group = findFromLocal(groupId);
        if(group==null)
            group = findFromNet(groupId);
        return group;

    }


    /**
     * 本地查找群信息
     * @param groupId 群ID
     * @return 群信息
     */
    public static Group findFromLocal(String groupId) {

        return SQLite.select()
                    .from(Group.class)
                    .where(Group_Table.id.eq(groupId))
                    .querySingle();

    }

    /**
     * 通过网络查询群信息
     * @param groupId 群ID
     * @return 群信息
     */
    public static Group findFromNet(String groupId){
        RemoteService service = NetWork.getAccountRemoteService();
        try {
            Response<RspModel<GroupCard>> response = service.groupFind(groupId).execute();
            GroupCard groupCard = response.body().getResult();
            if(groupCard != null)
            {
                //数据库的存储于通知
                Factory.getGroupCenter().dispatch(groupCard);
                User user  = ContactHelper.searchId(groupCard.getOwnerId());
                if(user != null)
                    return groupCard.build(user);



            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 群的创建
     * @param model 群信息模型
     * @param callback 回调
     */
    public static void create(GroupCreateModel model, final DataSource.Callback<GroupCard> callback) {
        RemoteService service = NetWork.getAccountRemoteService();
       service.groupCreate(model).enqueue(new Callback<RspModel<GroupCard>>() {
           @Override
           public void onResponse(Call<RspModel<GroupCard>> call, Response<RspModel<GroupCard>> response) {
                RspModel<GroupCard> rspmodel = response.body();
               if(rspmodel.success()){
                    GroupCard groupCard = rspmodel.getResult();
                   //唤起进行保存的操作
                   Factory.getGroupCenter().dispatch(groupCard);
                   //返回数据
                   callback.onDataLoader(groupCard);
               }else {
                   Factory.decodeRsqCode(rspmodel,callback);
               }
           }

           @Override
           public void onFailure(Call<RspModel<GroupCard>> call, Throwable t) {
                callback.onDataNotAvaliable(R.string.data_network_error);
           }
       });
    }

    public static Call search(String content, final DataSource.Callback<List<GroupCard>> callback) {
        RemoteService service = NetWork.getAccountRemoteService();
        Call<RspModel<List<GroupCard>>> call = service.groupSearchByName(content);
        call.enqueue(new Callback<RspModel<List<GroupCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupCard>>> call, Response<RspModel<List<GroupCard>>> response) {
                RspModel<List<GroupCard>> rspModel =response.body();
                if(rspModel.success())
                {
                    List<GroupCard> userCards =rspModel.getResult();
                    callback.onDataLoader(userCards);

                }else{
                    Factory.decodeRsqCode(rspModel,callback);
                }

            }

            @Override
            public void onFailure(Call<RspModel<List<GroupCard>>> call, Throwable t) {
                callback.onDataNotAvaliable(R.string.data_rsp_error_service);
            }
        });
        return call;
    }

    /**
     * 刷新群组
     */
    public static void refreshGroups() {
        RemoteService service = NetWork.getAccountRemoteService();
        service.groups("").enqueue(new Callback<RspModel<List<GroupCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupCard>>> call, Response<RspModel<List<GroupCard>>> response) {
                RspModel<List<GroupCard>> rspmodel = response.body();
                if(rspmodel.success())
                {
                    List<GroupCard> groupCards = rspmodel.getResult();
                    if(groupCards.size()>0 && groupCards!=null)
                        Factory.getGroupCenter().dispatch(groupCards.toArray(new GroupCard[0]) );
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<GroupCard>>> call, Throwable t) {

            }
        });
    }

    /**
     * 获取群成员的数量
     * @param id 群号
     * @return 返回群成员的数量
     */
    public static long getMemberCount(String id) {
        return SQLite.selectCountOf()
                .from(GroupMember.class)
                .where(GroupMemeber_Table.group_id.eq(id))
                .count();
    }

    /**
     * 网络刷新群的成员
     * @param group
     */
    public static void refreshGroupMember(Group group) {
        RemoteService service = NetWork.getAccountRemoteService();
        service.groupMembers(group.getId()).enqueue(new Callback<RspModel<List<GroupMemberCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupMemberCard>>> call, Response<RspModel<List<GroupMemberCard>>> response) {
                RspModel<List<GroupMemberCard>> rspmodel = response.body();
                if(rspmodel.success())
                {
                    List<GroupMemberCard> groupCards = rspmodel.getResult();
                    if(groupCards.size()>0 && groupCards!=null)
                        Factory.getGroupCenter().dispatch(groupCards.toArray(new GroupCard[0]) );
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<GroupCard>>> call, Throwable t) {

            }
        });
    }

    /**
     * 关联查询一个用户和群成员列表,返回一个MemberUserModel集合
     * @param groupId
     * @param size
     * @return
     */
    public static List<MemberUserModel> getMemberUser(String groupId, int size) {
        return SQLite.select(GroupMember_Table.alias.withTable().as("alias"),
                User_Table.id.withTable().as("userId"),
                User_Table.name.withTable().as("name"),
                User_Table.portrait.withTable().as("portrait"))
                .from(GroupMember.class)
                .join(User.class, Join.JoinType.INNER)
                .on(GroupMember_Table.user_id.withTable().eq(User_Table.id.withTable()))
                .where(GroupMember_Table.group_id.withTable().eq(groupId))
                .orderBy(GroupMember_Table.user_id,true)
                .limit(size)
                .queryCustomList(MemberUserModel.class);
    }
}
