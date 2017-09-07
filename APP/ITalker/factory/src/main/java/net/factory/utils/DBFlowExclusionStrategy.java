package net.factory.utils;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

/**
 * DBflow数据库字段过滤的gson
 * Created by CLW on 2017/9/6.
 */

public class DBFlowExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        //被跳过字段
        //只要是属于DBflow数据库的
        return f.getDeclaredClass().equals(ModelAdapter.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        //被跳过的Class
        return false;
    }
}
