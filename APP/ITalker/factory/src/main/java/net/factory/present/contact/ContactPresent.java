package net.factory.present.contact;

import android.support.v7.util.DiffUtil;

import net.common.factory.data.DataSource;
import net.common.factory.present.BaseRecyclePresent;
import net.common.widget.recycle.RecycleAdapter;
import net.factory.data.user.ContactDataSource;
import net.factory.data.user.ContactRespository;
import net.factory.model.db.User;

import net.factory.data.Helper.ContactHelper;
import net.factory.present.BaseSourcePresent;
import net.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * 联系人列表
 * Created by CLW on 2017/9/17.
 */

public class ContactPresent extends BaseSourcePresent<User,User,ContactDataSource,
        ContactContract.ContactView>
        implements DataSource.SuccessCallback<List<User>>,ContactContract.Present{

    private ContactDataSource contactDataSource;


    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public ContactPresent(ContactContract.ContactView view) {
        super(new ContactRespository(),view);

    }

    @Override
    public void start() {
        super.start();
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

}
