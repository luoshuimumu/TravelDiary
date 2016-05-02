package com.example.luoshuimumu.traveldiary.modle.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luoshuimumu on 2016/4/13.
 */
public class MediaSqliteHelper extends SQLiteOpenHelper {
//    //数据库需要两个表，一个用于储存轨迹编号(id 名称)，一个用于储存轨迹的实际值(id x y 时间)
//    final String CREATE_TABLE_TRACE_TITLE = "create table " + "trace_title" + " (_id integer" +
//            "primary key autoincrement,name);";
//    final String CREATE_TABLE_TRACE_CONTENT = "create table " + "trace_title" + " (_id integer" +
//            "primary key autoincrement,time,x,y);";

    /**
     * 会有一个共有数据库 保存所有的媒体数据 media
     * 共有五种类型媒体数据 txt pic audio video trace
     * 共有的表结构为 _id type date location uri path
     * 每次在从AbsActNewMedia返回ActNewMedia的时候向数据库插入信息
     */

    //数据库地址
    public static final String NAME_MEDIA_SQL = "media.db3";

    //创建数据表
    final String SQL_CREATE_TABLE_MEDIA = "create table " + "media" + " (_id integer" +
            " primary key autoincrement,type varchar(255),date varchar(255),location varchar(255),uri varchar(255),path varchar(255));";

    public MediaSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //第一次使用数据库时自动建表
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MEDIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("onUpgrade Called:oldVersion(" + i + ")" + "---newVersion("
                + i1 + ").");
    }


}
