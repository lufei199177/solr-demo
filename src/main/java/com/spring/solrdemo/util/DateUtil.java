package com.spring.solrdemo.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author lufei
 * @date 2021/1/4
 * @desc
 */
public class DateUtil {

    public static Date computeByYear(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }
}
