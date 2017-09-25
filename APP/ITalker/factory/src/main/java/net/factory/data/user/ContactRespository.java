package net.factory.data.user;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.Index;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.common.factory.data.DataSource;
import net.factory.data.DBHelper;
import net.factory.model.db.User;
import net.factory.model.db.User_Table;
import net.factory.persistence.Account;


import java.util.LinkedList;
import java.util.List;


/**
 * 联系人仓库
 * Created by CLW on 2017/9/24.
 */

public class ContactRespository implements ContactDataSource,QueryTransaction.QueryResultListCallback<User>,DBHelper.ChangeListener<User>
       {

    private DataSource.SuccessCallback<List<User>> callback;

    private final List<User> userList = new LinkedList<>();
    @Override
    public void load(DataSource.SuccessCallback<List<User>> callback) {
        this.callback = callback;
        //注册监听器
        DBHelper.addListener(User.class,this);
        //加载本地数据
        SQLite.select().from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name,true)
                .limit(100) //最多查询100条数据
                .async()
                .queryListResultCallback(this)
                .execute();        //异步操作
    }

    @Override
    public void dispose() {
        this.callback=null;
        DBHelper.removeListener(User.class,this);
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<User> tResult) {
        userList.addAll(tResult);
        if(callback!=null) {
            callback.onDataLoader(tResult);
        }
        onDataSave();
    }

    /**
     * 观察者的方法
     * @param users 变化的user
     */
    @Override
    public void onDataSave(User... users) {
        boolean isChange = false;
        for(User user:users)
        {
            //关注的人,同时不是自己
            if(isRequired(user)) {
                insertOrUpdate(user);
                isChange=true;
            }
        }
        if(isChange) {
            notifyDataChanger();
        }
    }

    private void insertOrUpdate(User user)
    {
        int index=indexOf(user);
        if(index>=0) {
            update(index,user);
        }else {
            insert(user);
        }

    }

    private void update(int index,User user)
    {
        userList.remove(index);
        userList.add(index,user);
    }

    private void insert(User user)
    {
        userList.add(user);
    }

    /**
     * 判断是更新还是插入
     * @param user 传递进来的值
     * @return 如果有则返回相应的Index,如果没有则返回-1;
     */
    private int indexOf(User user)
    {
        int index = -1;
        for(User user1:userList)
        {
            index++;
            if(user1.isSame(user)) {
                    return index;
            }
        }
        return -1;
    }

    /**
     * 观察者 观察是否删除
     * @param users
     */
    @Override
    public void onDataDelete(User... users) {
            boolean isChange =false;
            for(User user:users)
            {
                userList.remove(user);
                isChange=true;
            }
            if(isChange)
            {
                notifyDataChanger();
            }
    }

    private void notifyDataChanger()
    {
        if(callback!=null) {
            callback.onDataLoader(userList);
        }
    }
    /**
     * 检查一个user是否是关注的并且不是自己
     * @param user user
     * @return True是自己关注的并且不是自己,否则则是
     */
    private boolean isRequired(User user)
    {
       return user.isFollow()&&!user.getId().equals(Account.getUserId());
    }
}
