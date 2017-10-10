package net.factory.data;

import android.support.annotation.NonNull;


import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.common.factory.data.DataSource;
import net.common.factory.data.DbDataSource;
import net.common.utils.CollectionUtil;
import net.factory.data.Helper.DBHelper;
import net.factory.model.db.BaseDbModel;
import net.factory.model.db.User;
import net.factory.model.db.User_Table;
import net.factory.persistence.Account;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

/**
 * 基礎的数据库仓库
 * Created by CLW on 2017/9/26.
 */

public abstract class BaseRespository<Model extends BaseDbModel>  implements DbDataSource<Model>
        ,DBHelper.ChangeListener<Model>,QueryTransaction.QueryResultListCallback<Model>{
    protected DataSource.SuccessCallback<List<Model>> callback ;
    protected  LinkedList<Model> dataList = new LinkedList<>();
    protected Class<Model> dataClass;

    /**
     * 初始加载则获取泛型的类
     */
    public BaseRespository()
    {
        //通过反射获取泛型
        ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();

        dataClass = (Class<Model>)(parameterizedType.getActualTypeArguments()[0]);
    }


    @Override
    public void load(SuccessCallback<List<Model>> callback) {
        this.callback = callback;
        addDbChangeListener();
    }


    public void dispose() {
        this.callback=null;
        DBHelper.removeListener(dataClass,this);
        //将缓存数据清除
        dataList.clear();
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Model> tResult) {

        if(tResult.size()==0)
        {
            dataList.clear();
            notifyDataChanger();
            return;
        }
        dataList.addAll(tResult);
        Model[] models =CollectionUtil.toArray(tResult,dataClass);
        onDataSave(models);
    }

    /**
     * 通知界面刷新的地方
     */
    private void notifyDataChanger()
    {
        if(callback!=null) {
            callback.onDataLoader(dataList);
        }
    }

    /**
     * 数据库统一通知的地方:增加/更改
     * @param models 数据类型
     */
    @Override
    public void onDataSave(Model... models) {
        boolean isChange = false;
        for(Model model:models)
        {
            //自己实现过滤
            if(isRequired(model)) {
                insertOrUpdate(model);
                isChange=true;
            }
        }
        if(isChange) {
            notifyDataChanger();
        }
    }


    /**
     * 判断是执行插入还是更新操作
     * @param model 数据
     */
    private void insertOrUpdate(Model model)
    {
        int index=indexOf(model);
        if(index>=0) {
            update(index,model);
        }else {
            insert(model);
        }
    }
    /**
     * 判断是缓存里是否有
     * @param newModel 传递进来的值
     * @return 如果有则返回相应的Index,如果没有则返回-1;
     */
    private int indexOf(Model newModel)
    {
        int index = -1;
        for(Model model:dataList)
        {
            index++;
            if(newModel.isSame(model)) {
                return index;
            }
        }
        return -1;
    }

    private void update(int index,Model model)
    {
        dataList.remove(index);
        dataList.add(index,model);
    }
    protected void insert(Model model)
    {
        dataList.add(model);
    }


    /**
     * 数据库统一通知的地方:删除
     * @param models 数据类型
     */
    @Override
    public void onDataDelete(Model... models) {
        boolean isChange =false;
        for(Model model:models)
        {
            dataList.remove(model);
            isChange=true;
        }
        if(isChange)
        {
            notifyDataChanger();
        }
    }
    /**
     * 检查一个user是否是关注的并且不是自己
     * @param Model 数据
     */
    public abstract boolean isRequired(Model model);

    /**
     * 添加监听器
     */
    public void addDbChangeListener()
    {
        DBHelper.addListener(dataClass,this);
    }
}
