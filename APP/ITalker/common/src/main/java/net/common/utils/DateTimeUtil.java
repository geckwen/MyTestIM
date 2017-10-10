package net.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by CLW on 2017/10/5.
 */

public class DateTimeUtil {
    private static  final SimpleDateFormat FORMAT =new SimpleDateFormat("yy-MM-dd", Locale.CHINA);

    /**
     * 获取一个简单的时间字符串
     * @param date Date
     * @return 时间字符串
     */
    public static String getSimpleDate(Date date)
    {
        return  FORMAT.format(date);
    }

}
