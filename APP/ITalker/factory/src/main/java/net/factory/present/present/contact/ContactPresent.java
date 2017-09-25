package net.factory.present.present.contact;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.util.DiffUtil;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.common.factory.data.DataSource;
import net.common.factory.present.BasePresent;
import net.common.factory.present.BaseRecyclePresent;
import net.common.widget.recycle.RecycleAdapter;
import net.factory.data.user.ContactDataSource;
import net.factory.data.user.ContactRespository;
import net.factory.model.card.UserCard;
import net.factory.model.db.AppDatabase;
import net.factory.model.db.User;

import net.factory.data.Helper.ContactHelper;
import net.factory.model.db.User_Table;
import net.factory.persistence.Account;
import net.factory.utils.DiffUiDataCallback;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by CLW on 2017/9/17.
 */

public class ContactPresent extends BaseRecyclePresent<User,ContactContract.ContactView> implements ContactContract.Present,DataSource.SuccessCallback<List<User>> {

    private ContactDataSource contactDataSource;


    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public ContactPresent(ContactContract.ContactView view) {
        super(view);
        contactDataSource= new ContactRespository();
    }

    @Override
    public void start() {
        super.start();
        //添加本地数据库数据,并进行监听
        contactDataSource.load(this);

            //进行网络数据库刷新
        ContactHelper.contact();
    }

    /**
     * 运行时在子线程
     * @param newList 新的数据
     */
    @Override
    public void onDataLoader(List<User> newList) {
        final  ContactContract.ContactView  view= getmView();
        if(view==null)
            return;
        RecycleAdapter<User> adapter =view.getRecycleAdapter();
        List<User> oldList = adapter.getDataList();
        //进行数据对比
            DiffUtil.Callback callback =new DiffUiDataCallback<User>(oldList,newList);
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        //进行界面的刷新
            refreshData(newList,result);


    }

    @Override
    public void destroy() {
        super.destroy();
        contactDataSource.dispose();
    }
}
