package net.factory.data.user;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.common.factory.data.DataSource;
import net.factory.data.BaseRespository;
import net.factory.data.Helper.DBHelper;
import net.factory.model.db.User;
import net.factory.model.db.User_Table;
import net.factory.persistence.Account;

import java.util.List;


/**
 * 联系人仓库
 * Created by CLW on 2017/9/24.
 */

public class ContactRespository  extends BaseRespository<User> implements ContactDataSource {


    @Override
    public void load(DataSource.SuccessCallback<List<User>> callback) {
            super.load(callback);
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



    /**
     * 检查一个user是否是关注的并且不是自己
     * @param user user
     * @return True是自己关注的并且不是自己,否则则是
     */
    public boolean isRequired(User user)
    {
       return user.isFollow()&&!user.getId().equals(Account.getUserId());
    }
}
