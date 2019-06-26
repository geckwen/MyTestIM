package net.factory.present.group;

import net.common.factory.present.BaseRecyclePresent;
import net.factory.data.Helper.ContactHelper;
import net.factory.data.Helper.GroupHelper;
import net.factory.model.view.MemberUserModel;
import net.factory.model.view.UserSampleModel;
import net.factory.present.Factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CLW on 2019/6/22.
 */

public class GroupMemberPresenter extends BaseRecyclePresent<MemberUserModel,GroupMemberContract.View>
    implements GroupMemberContract.Presenter {
    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public GroupMemberPresenter(GroupMemberContract.View view) {
        super(view);
    }

    @Override
    public void refresh() {
        start();
        //异步加载
        Factory.runAsync(loader);
    }

    private  Runnable loader = new Runnable() {
        @Override
        public void run() {
            GroupMemberContract.View view = getmView();
            if(view==null)
                return;
            String groupId = view.getGroupId();
            //-1代表查询所有
            List<MemberUserModel> memberSampleModels = GroupHelper.getMemberUser(groupId,-1);
            refreshData(memberSampleModels);
        }
    };
}
