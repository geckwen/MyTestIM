package net.factory.present.session;

import android.support.v7.util.DiffUtil;

import net.factory.data.session.SessionDataSource;
import net.factory.data.session.SessionResponsitory;
import net.factory.model.db.Session;
import net.factory.present.BaseSourcePresent;
import net.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by CLW on 2017/10/5.
 */

public class SessionPresent extends BaseSourcePresent<Session,Session,SessionDataSource,SessionContract.View>implements SessionContract.Present {
    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param source
     * @param view
     */
    public SessionPresent(SessionContract.View view) {
        super(new SessionResponsitory(), view);
    }

    @Override
    public void onDataLoader(List<Session> sessions) {
        SessionContract.View view = getmView();
        if(view==null)
            return;
        List<Session> oldList = view.getRecycleAdapter().getDataList();
        DiffUiDataCallback<Session> callback = new DiffUiDataCallback<>(oldList,sessions);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        refreshData(sessions,result);
    }
}
