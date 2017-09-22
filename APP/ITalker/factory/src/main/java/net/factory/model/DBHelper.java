package net.factory.model;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import net.common.utils.CollectionUtil;
import net.factory.model.db.AppDatabase;

import java.util.Arrays;

/**
 * Created by CLW on 2017/9/22.
 */

public class DBHelper {
    private static final DBHelper dbHelper;

    static {
        dbHelper = new DBHelper();
    }
    private DBHelper(){}

    /**
     * 进行增加或者修改操作
     * @param tclass 传递进来的类型
     * @param models models
     * @param <Model> 泛型
     */
    public static<Model extends BaseModel> void save(final Class<Model> tclass, final Model... models)
    {
        if(models==null||models.length==0)
            return;

        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                 ModelAdapter<Model> modelAdapter = FlowManager.getModelAdapter(tclass);
                modelAdapter.saveAll(CollectionUtil.toArrayList(models));
                //也可以这样
                //modelAdapter.saveAll(Arrays.asList(models));
            }
        });
    }

    /**
     * 进行删除操作
     * @param tclass 传递进来的类型
     * @param models models
     * @param <Model> 泛型
     */
    public static<Model extends BaseModel> void delete(final Class<Model> tclass, final Model... models)
    {
        if(models==null||models.length==0)
            return;
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Model> modelAdapter = FlowManager.getModelAdapter(tclass);
                modelAdapter.deleteAll(CollectionUtil.toArrayList(models));
                //也可以这样
                //modelAdapter.saveAll(Arrays.asList(models));
            }
        });
    }

    /**
     * 进行通知调用
     * @param tclass 传递进来的类型
     * @param models models
     * @param <Model> 泛型
     */
    public static<Model extends BaseModel> void notifySave(final Class<Model> tclass, final Model... models)
    {
        //TODO 通知保存
    }

    /**
     * 进行删除调用
     * @param tclass 传递进来的类型
     * @param models models
     * @param <Model> 泛型
     */
    public static<Model extends BaseModel> void motifyDelete(final Class<Model> tclass, final Model... models)
    {
        //TODO 通知删除
    }

}
