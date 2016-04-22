package com.example.luoshuimumu.traveldiary.model.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luoshuimumu on 2016/4/13.
 */
public class MapSqliteHelper extends SQLiteOpenHelper {
    //数据库需要两个表，一个用于储存轨迹编号(id 名称)，一个用于储存轨迹的实际值(id x y 时间)
    final String CREATE_TABLE_TRACE_TITLE = "create table " + "trace_title" + " (_id integer" +
            "primary key autoincrement,name);";
    final String CREATE_TABLE_TRACE_CONTENT = "create table " + "trace_title" + " (_id integer" +
            "primary key autoincrement,time,x,y);";

    public MapSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TRACE_TITLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRACE_CONTENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("onUpgrade Called:oldVersion(" + i + ")" + "---newVersion("
                + i1 + ").");
    }
}
