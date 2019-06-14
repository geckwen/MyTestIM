package net.factory.data.Group;

import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.common.factory.data.DataSource;
import net.factory.data.BaseRespository;
import net.factory.data.Helper.GroupHelper;
import net.factory.model.db.Group;
import net.factory.model.view.MemberUserModel;

import java.util.List;

/**
 * 我的数据组仓库 是对DataSource的实现
 * Created by CLW on 2019/6/6.
 */

public class GroupRespository extends BaseRespository<Group>
        implements GroupDataSource{

    /**
     * @param callback 传递一个callback回调 方法
     */
    @Override
    public void load(SuccessCallback<List<Group>> callback) {
        super.load(callback);
        SQLite.select()
                .from(Group.class)
                .orderBy(Group_Table.name,true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    public boolean isRequired(Group group) {
        // 一个群的信息，只可可能两种情况 出现
        // 一个是被人拉入群，一个是自己建立群
        if(group.getGroupMemberCount()>0){
            group.holder = buildGroupHolder(group);
        }else{
            group.holder =null;
            GroupHelper.refreshGroupMember(group);
        }
        //所有的群 都需要关注
        return true;
    }

    /**
     * 初始化界面显示的成员信息
     * @param group 群信息
     * @return 群的holder信息
     */
    private String buildGroupHolder(Group group) {
        List<MemberUserModel> userModels = group.getLatetyGroupMember();
        is(userModels == null || userModels.size()==0)
                return null;
        StringBuilder builder  = new StringBuilder();
        for(MemberUserModel userModel : userModels)
        {
            builder.append(TextUtils.isEmpty(userModel.alias)?userModel.name:userModel.alias)
            builder.append(",");
        }
        builder.delete(builder.lastIndexOf(","),builder.length());
        return  builder.toString();
    }
}
