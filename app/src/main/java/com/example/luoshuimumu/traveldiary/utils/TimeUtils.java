package com.example.luoshuimumu.traveldiary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by luoshuimumu on 2016/4/19.
 */
public class TimeUtils {
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return "" + format.format(new Date());
    }
}
