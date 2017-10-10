package net.factory.data.session;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.Insert;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.common.factory.data.DataSource;
import net.factory.data.BaseRespository;
import net.factory.model.db.Session;
import net.factory.model.db.Session_Table;

import java.util.Collections;
import java.util.List;

/**
 * Created by CLW on 2017/10/5.
 */

public class SessionResponsitory extends BaseRespository<Session> implements SessionDataSource {

    private String sessionId;



    @Override
    public void load(SuccessCallback<List<Session>> callback) {
        super.load(callback);
        SQLite.select()
                .from(Session.class)
                .orderBy(Session_Table.modifyAt,false) //倒序
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();

    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Session> tResult) {
        Collections.reverse(dataList);
        super.onListQueryResult(transaction, tResult);

    }


    @Override
    protected void insert(Session model) {
        //复写方法,让新的数据加入到头部
        dataList.addFirst(model);
    }

    @Override
    public boolean isRequired(Session model) {
        return true;
    }
}
