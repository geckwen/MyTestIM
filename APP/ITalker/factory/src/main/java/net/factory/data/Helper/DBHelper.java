package net.factory.data.Helper;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import net.common.utils.CollectionUtil;
import net.factory.model.db.AppDatabase;
import net.factory.model.db.BaseDbModel;
import net.factory.model.db.Group;
import net.factory.model.db.GroupMember;
import net.factory.model.db.Group_Table;
import net.factory.model.db.Message;
import net.factory.model.db.Session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据库操作
 * Created by CLW on 2017/9/22.
 */

public class DBHelper {
    private static final DBHelper dbHelper;


    /**
     * 观察者集合
     * Class<?> 观察的表
     * Set<ChangeListener> 每一个表对应的观察者有很多
     */
    private static Map<Class<?>,Set<ChangeListener>> changeListeners = new HashMap<>();


    static {
        dbHelper = new DBHelper();
    }
    private DBHelper(){}

    /**
     * 获取表对应的观察者集合
     * @param tclass 观察的表
     * @param <Model> 继承bassemodel
     * @return 如果有则返回表对应的观察者集合，否则则为
     */
    private static<Model extends BaseDbModel> Set<ChangeListener> getListeners(Class<Model> tclass)
    {
        if(dbHelper.changeListeners.containsKey(tclass)) {
            return changeListeners.get(tclass);
        }
        return null;
    }

    /**
     * 添加观察者
     * @param tclass 需要观察的表
     * @param changeListener 观察者
     * @param <Model> 继承自Basemodel的信息
     */
    public static <Model extends BaseDbModel> void addListener(final Class<Model> tclass, ChangeListener changeListener)
    {
        Set<ChangeListener> changeListeners = getListeners(tclass);
        if(changeListeners==null) {
            changeListeners=new HashSet<>();
            dbHelper.changeListeners.put(tclass,changeListeners);
        }
        changeListeners.add(changeListener);
    }

    /**
     * 删除观察者
     * @param tclass 需要观察的表
     * @param changeListener 观察者
     * @param <Model> 继承自Basemodel的信息
     */
    public static <Model extends BaseDbModel> void removeListener(final Class<Model> tclass,ChangeListener changeListener)
    {
        Set<ChangeListener> changeListeners = getListeners(tclass);
        if(changeListeners==null) {
            return;
        }
        changeListeners.remove(changeListener);
    }

    /**
     * 进行增加或者修改操作
     * @param tclass 传递进来的类型
     * @param models models
     * @param <Model> 泛型
     */
    public static<Model extends BaseDbModel> void save(final Class<Model> tclass, final Model... models)
    {
        if(models==null||models.length==0)
            return;

        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                 ModelAdapter<Model> modelAdapter = FlowManager.getModelAdapter(tclass);
                modelAdapter.saveAll(CollectionUtil.toArrayList(models));
                dbHelper.notifySave(tclass,models);
                //也可以这样
                //modelAdapter.saveAll(Arrays.asList(models));
            }
        }).build().execute();
    }

    /**
     * 进行删除操作
     * @param tclass 传递进来的类型
     * @param models models
     * @param <Model> 泛型
     */
    public static<Model extends BaseDbModel> void delete(final Class<Model> tclass, final Model... models)
    {
        if(models==null||models.length==0)
            return;
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Model> modelAdapter = FlowManager.getModelAdapter(tclass);
                modelAdapter.deleteAll(CollectionUtil.toArrayList(models));
                dbHelper.notifyDelete(tclass,models);
                //也可以这样
                //modelAdapter.saveAll(Arrays.asList(models));
            }
        }).build().execute();
    }

    /**
     * 进行通知调用
     * @param tclass 传递进来的类型
     * @param models models
     * @param <Model> 泛型
     */
    public <Model extends BaseDbModel> void notifySave(final Class<Model> tclass, final Model... models)
    {
        //TODO 通知保存
       final Set<ChangeListener> changeListeners = getListeners(tclass);
        if(changeListeners!=null&&changeListeners.size()>0) {
            for (ChangeListener<Model> changeListener : changeListeners)
            {
                changeListener.onDataSave(models);
            }
        }
        if(tclass.equals(GroupMember.class)) {
            //群成员变更
            updateGroup((GroupMember[]) models);
        }else{
            if(tclass.equals(Message.class)){
                //消息变化,通知变化更新
                updateSession((Message[]) models);
            }
        }
    }

    /**
     * 进行删除调用
     * @param tclass 传递进来的类型
     * @param models models
     * @param <Model> 泛型
     */
    public <Model extends BaseDbModel> void notifyDelete(final Class<Model> tclass, final Model... models)
    {
        //TODO 通知删除
        final Set<ChangeListener> changeListeners = getListeners(tclass);
        if(changeListeners!=null&&changeListeners.size()>0) {
            for (ChangeListener<Model> changeListener : changeListeners)
            {
                changeListener.onDataDelete(models);
            }
        }
        if(tclass.equals(GroupMember.class)) {
            //群成员变更
            updateGroup((GroupMember[]) models);
        }else{
            if(tclass.equals(Message.class)){
                //消息变化,通知变化更新
                updateSession((Message[]) models);
            }
        }
    }


    /**
     * 从成员中找出成员对应的群,并对群进行更新
     * @param groupMembers 群成员列表
     */
    private  void updateGroup(GroupMember... groupMembers)
    {
        final  Set<String> groupIds = new HashSet<>();
        for(GroupMember groupMember:groupMembers)
        {
            groupIds.add(groupMember.getGroup().getId());
        }
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                List<Group> groups = SQLite.select()
                        .from(Group.class)
                        .where(Group_Table.id.in(groupIds))
                        .queryList();
                //由于群成员更新 也需要更新群里面的信息
                dbHelper.notifySave(Group.class,groups.toArray(new Group[0]));
                //也可以这样
                //modelAdapter.saveAll(Arrays.asList(models));
            }
        }).build().execute();
    }



    /**
     * 从消息列表中,筛选出相应的session
     * @param messages 消息列表
     */
    private void updateSession(Message... messages)
    {
        final Set<Session.Identify> identifies = new HashSet<>();
        for(Message message:messages)
        {
            Session.Identify identify  = Session.createSessionIdentify(message);
            identifies.add(identify);
        }
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Session> adapter =FlowManager.getModelAdapter(Session.class);
                Session[] sessions = new Session[identifies.size()];
                    int index=0;
                    for(Session.Identify identify : identifies)
                    {
                       Session session = SessionHelper.findLocal(identify.id);
                        if(session==null)
                        {
                            session =new Session(identify);
                        }
                        session.save();
                        session.refreshNow();
                        sessions[index++]=session;
                    }
                //由于消息更新 需要重新刷新消息
                notifySave(Session.class,sessions);
                //也可以这样
                //modelAdapter.saveAll(Arrays.asList(models));
            }
        }).build().execute();



    }




    public interface ChangeListener<Data extends BaseDbModel>
    {
        //通知观察者 信息存储
        void onDataSave(Data... datas);
        //通知观察者 信息删除
        void onDataDelete(Data... datas);
    }

}
