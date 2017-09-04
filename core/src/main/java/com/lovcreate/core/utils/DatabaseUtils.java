package com.lovcreate.core.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lidroid.xutils.DbUtils;

/**
 * 数据库基本工具
 * 使用xUtils的DbUtils模块
 * Created by Albert.Ma on 2015/9/28.
 */
public class DatabaseUtils {

    private static DbUtils DB;//数据库对象
    private static SQLiteDatabase sqLiteDB;//android自带数据库

    public static DbUtils getDb(Context context) {
        //创建数据库
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
        config.setDbName(AppUtil.getAPPName(context) + ".db");//以项目名称作为数据库名
        config.setDbVersion(1);//db版本
        DB = DbUtils.create(config);
        sqLiteDB = DB.getDatabase();
        //初始化表
        initTables();
        return DB;
    }

    /**
     * 第一次运行时 创建所有表
     */
    public static void initTables() {
//        try {
//            DB.createTableIfNotExist(VO.class);
//        } catch (DbException e) {
//            e.printStackTrace();
//            Logcat.e( "DatabaseUtils.initTables() 创建所有表失败" + e.getMessage());
//        }
    }

}
