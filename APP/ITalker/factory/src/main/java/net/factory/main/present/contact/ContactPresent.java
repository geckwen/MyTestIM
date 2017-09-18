package net.factory.main.present.contact;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.util.DiffUtil;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.common.factory.data.DataSource;
import net.common.factory.present.BaseContract;
import net.common.factory.present.BasePresent;
import net.factory.model.card.UserCard;
import net.factory.model.db.AppDatabase;
import net.factory.model.db.User;
import net.factory.model.db.User_Table;
import net.factory.net.contact.ContactHelper;
import net.factory.persistence.Account;
import net.factory.utils.DiffUiDataCallback;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by CLW on 2017/9/17.
 */

public class ContactPresent extends BasePresent<ContactContract.ContactView> implements ContactContract.Present,DataSource.Callback<List<UserCard>> {

    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public ContactPresent(ContactContract.ContactView view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        SQLite.select().from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name,true)
                .limit(100) //最多查询100条数据
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<User>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<User> tResult) {
                        getmView().getRecycleAdapter().replace(tResult);
                        getmView().onAdapterDataRefresh();
                    }
                })
                .execute();        //异步操作

            //进行网络数据库刷新
        ContactHelper.contact(this);
    }


    @Override
    public void onDataLoader(List<UserCard> userCards) {
        final List<User> users =new LinkedList<User>();
        for(UserCard userCard : userCards)
        {
            users.add(userCard.build());
        }
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                FlowManager.getModelAdapter(User.class).saveAll(users);
            }
        }).build().execute();
        List<User> oldList = getmView().getRecycleAdapter().getDataList();
        diff(oldList,users);

    }

    @Override
    public void onDataNotAvaliable(@StringRes int res) {

    }

    private void diff(List<User> oldList,List<User> newList)
    {
        DiffUtil.Callback callback =new DiffUiDataCallback<User>(oldList,newList);
       DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        getmView().getRecycleAdapter().replace(newList);
        result.dispatchUpdatesTo(getmView().getRecycleAdapter());

        getmView().onAdapterDataRefresh();

    }

}
