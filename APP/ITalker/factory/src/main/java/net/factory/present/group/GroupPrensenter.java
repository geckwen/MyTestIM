package net.factory.present.group;

import android.support.v7.util.DiffUtil;

import net.common.factory.data.DataSource;
import net.factory.data.Group.GroupDataSource;
import net.factory.data.Group.GroupRespository;
import net.factory.data.Helper.ContactHelper;
import net.factory.data.Helper.GroupHelper;
import net.factory.model.db.Group;
import net.factory.present.BaseSourcePresent;
import net.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by CLW on 2019/6/6.
 */

public class GroupPrensenter extends BaseSourcePresent<Group,Group,GroupDataSource,
        GroupContract.GroupView>
        implements DataSource.SuccessCallback<List<Group>>,GroupContract.Present {
    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param source
     * @param view
     */
    public GroupPrensenter( GroupContract.GroupView view) {
        super(new GroupRespository(), view);
    }
    @Override
    public void start() {
        super.start();
        //TODO 进行网络数据库刷新,以后可以优化到下拉刷新
        GroupHelper.refreshGroups();
    }

    @Override
    public void onDataLoader(List<Group> groups) {
        final GroupContract.GroupView view = getmView();
        if(view == null)
            return;
        //对比差异
        List<Group> old = view.getRecycleAdapter().getDataList();
        DiffUiDataCallback<Group> callback = new DiffUiDataCallback<>(old,groups);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        //界面刷新
        refreshData(groups,result);
    }
}
