package net.factory.model.db;


import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 数据库相关信息
 * Created by CLW on 2017/9/6.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 2;
}