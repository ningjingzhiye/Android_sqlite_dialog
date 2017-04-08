package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //数据库 名字 自己取
    private static final String DATABASE_NAME = "xx.db";
    private static final int DATABASE_VERSION = 1;

    //构造方法
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建person表
        String personTable = "create table if not exists Person" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "sname VARCHAR(255), " +
                "sid VARCHAR(255) )";
        db.execSQL(personTable);
    }

    // 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE Person ADD COLUMN other STRING");
    }

}