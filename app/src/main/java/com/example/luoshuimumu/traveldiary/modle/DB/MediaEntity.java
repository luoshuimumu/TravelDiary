package com.example.luoshuimumu.traveldiary.modle.DB;

import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 媒体实体类型 用于保存fragment从数据库查询到的媒体列表数据
 * Created by luoshuimumu on 2016/4/13.
 */
public class MediaEntity implements Comparable<MediaEntity>,Parcelable{
    static int CREATOR;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int compareTo(MediaEntity another) {
        if (Long.parseLong(this.id) > Long.parseLong(another.getId())) {
            return 1;

        } else if (Long.parseLong(this.id) == Long.parseLong(another.getId())) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (!((MediaEntity) o).getId().equals(this.getId())) {
            return false;
        }
        if (!((MediaEntity) o).getPath().equals(this.getPath())) {
            return false;
        }
        if (!((MediaEntity) o).getType().equals(this.getType())) {
            return false;
        }
        if (!((MediaEntity) o).getDate().equals(this.getDate())) {
            return false;
        }
        if (!((MediaEntity) o).getLocation().equals(this.getLocation())) {
            return false;
        }
        if (!((MediaEntity) o).getUri().equals(this.getUri())) {
            return false;
        }

        return true;
    }

    public static final String TYPE_PIC = "pic";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_TRACE = "trace";

    public static final String COLUMN_NAME_TYPE = "type";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_LCOATION = "location";
    public static final String COLUMN_NAME_URI = "uri";
    public static final String COLUMN_NAME_PATH = "path";
    public static final String COLUMN_NAME_ID = "_id";

    String type;
    String date;
    String location;
    String uri;
    String path;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
