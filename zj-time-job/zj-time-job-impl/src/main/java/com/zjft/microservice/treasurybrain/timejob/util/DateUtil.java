package com.zjft.microservice.treasurybrain.timejob.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 常 健
 * @since 2019/07/16
 */
public class DateUtil {

    public static String getDateByInterval(int interval) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date curDate = new Date();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_MONTH, interval);
        String dateStr = sdf.format(calendar.getTime());
        return dateStr;
    }
}
