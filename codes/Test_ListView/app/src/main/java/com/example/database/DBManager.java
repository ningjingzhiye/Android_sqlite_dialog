package com.example.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper helper;
    private  SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context); // 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,mFactory);
        // 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
        db = helper.getReadableDatabase();
    }

    public boolean user_insertUser(Person per) {
        boolean b = false;
        db.beginTransaction();
        try {
            Cursor c = db.rawQuery(
                    "select * from Person where sname=?" , new String[] { per.sname });
            if (c == null || c.getCount() <= 0) {
                db.execSQL("insert into Person values(null,?,?)", new Object[] {
                        per.sname,
                        per.sid }
                );
                b = true;
            }
            c.close();
            db.setTransactionSuccessful(); // 这个事务成功的也必须加上
        }catch (SQLException e) {
            db.endTransaction();
            b = false ;
        }finally{
            db.endTransaction(); // 最后事务一定要结束？？
        }
        return b;
    }

    public List<Person> user_getAll() {
        List<Person> models = new ArrayList<Person>();
        Person p = null ;
        Cursor c = db.rawQuery("select * from Person" , null) ;
        while (c.moveToNext()) {
            p = new Person();
            p.id = c.getInt(c.getColumnIndex("id"));
            p.sname = c.getString(c.getColumnIndex("sname"));
            p.sid = c.getString(c.getColumnIndex("sid"));
            models.add(p);
        }
        c.close() ;
        return models ;
    }

    public boolean user_deleteBySid(String sid) {
        boolean b = false;
        try {
            db.execSQL("delete from Person where sid=?",
                    new String[] { sid });
            b = true;
        }catch (SQLException e) {
            b = false ;
        }
        return b;
    }

    public Person user_getBySid(String sid) {
        Person p = null ;
        Cursor c = db.rawQuery("select * from Person where sid=?" , new String[] { sid }) ;
        while (c.moveToNext()) {
            p = new Person();
            p.id = c.getInt(c.getColumnIndex("id"));
            p.sname = c.getString(c.getColumnIndex("sname"));
            p.sid = c.getString(c.getColumnIndex("sid"));
        }
        c.close() ;
        return p ;
    }

    public Person user_getBySname(String sname) {
        Person p = null ;
        Cursor c = db.rawQuery("select * from Person where sname=?" , new String[] { sname }) ;
        while (c.moveToNext()) {
            p = new Person();
            p.id = c.getInt(c.getColumnIndex("id"));
            p.sname = c.getString(c.getColumnIndex("sname"));
            p.sid = c.getString(c.getColumnIndex("sid"));
        }
        c.close() ;
        return p ;
    }

//    public Person user_updateBySname(String sname) {
//        Person p = null ;
//        Cursor c = db.rawQuery("update Person set sid=123 where sname=?" , new String[] { sname }) ;
//        while (c.moveToNext()) {
//            p = new Person();
//            p.id = c.getInt(c.getColumnIndex("id"));
//            p.sname = c.getString(c.getColumnIndex("sname"));
//            p.sid = c.getString(c.getColumnIndex("sid"));
//        }
//        c.close() ;
//        return p ;
//    }

}
