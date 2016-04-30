package com.example.luoshuimumu.traveldiary.model.DB;

import android.database.sqlite.SQLiteDatabase;

/**
 * 媒体实体类型 用于保存fragment从数据库查询到的媒体列表数据
 * Created by luoshuimumu on 2016/4/13.
 */
public class MediaEntity {
    String type;
    String date;
    String location;
    String uri;
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    //在title表插入新的轨迹
    public static void insertTraceTitle(SQLiteDatabase db, String name) {

    }

    /**
     * 准备 创建或打开数据库
     */
    public static void create() {


    }
}