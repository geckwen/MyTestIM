package net.factory.present;

import net.common.factory.data.DataSource;
import net.common.factory.data.DbDataSource;
import net.common.factory.present.BaseContract;
import net.common.factory.present.BaseRecyclePresent;

import java.util.List;

/**
 * 基础仓库的present
 * Created by CLW on 2017/9/26.
 */

public abstract class BaseSourcePresent<Data,ViewModel,Source extends DbDataSource, View extends BaseContract.RecycleView>
        extends BaseRecyclePresent<ViewModel,View>
        implements DataSource.SuccessCallback<List<Data>>{
    protected Source dbDataSource;


    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public BaseSourcePresent(Source source,View view) {
        super(view);
        dbDataSource = source;
    }

    @Override
    public void start() {
        super.start();
        dbDataSource.load(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        dbDataSource.dispose();
        dbDataSource = null;

    }
}
