package com.example.luoshuimumu.traveldiary.test;

import android.content.ContentUris;
import android.content.UriMatcher;
import android.net.Uri;

/**
 * Created by luoshuimumu on 2016/1/9.
 */
public class TestUri {

    void testUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //注册需要的uri
        //可以注册多次
        matcher.addURI("包名", "people", 0);
        matcher.addURI("报名", "person/#", 0);
        //匹配uri
        //此uri是目标导向的
        Uri uri = Uri.parse("content://" + "package" + "/people");
        int matchCode = matcher.match(uri);
        switch (matchCode) {
            case 0:
                break;
            case 1:
                break;
            default:
                break;
        }
    }

    void testContentUris() {
        Uri uri = Uri.parse("content://package/people");
        Uri resultUri = ContentUris.withAppendedId(uri, 10);
        //之后resultUri为content://package/people/10
        //从路径中获取id
        Uri uriWithId = Uri.parse("contemt://package/people/10");
        long personId = ContentUris.parseId(uriWithId);


    }


}
