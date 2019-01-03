package com.manage.common.commonUtil;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2)
    {
        if(date1==null || date2==null){
            return 0;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
    public static Date getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return cal.getTime();
    }
    public static Date getBeginDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int beginDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, beginDay);
        return cal.getTime();
    }


    /**
     *  获取指定月份的25号
     * @param year 年
     * @param month 月
     * @param m 前几月
     * @return
     */
    public static Date getMonthDate(int year,int month,int m){
        Calendar endDate = Calendar.getInstance();
        //设置年份
        endDate.set(Calendar.YEAR,year);
        //设置月份
        endDate.set(Calendar.MONTH,month-1-m);
        endDate.set(Calendar.DAY_OF_MONTH,25);
        return  endDate.getTime();
    }

    public static  void  main(String[] arge){
        Random rd = new Random();
        BigDecimal decimal = new BigDecimal(rd.nextFloat()+1);
        // System.out.println(decimal.setScale(2,BigDecimal.ROUND_UP));
        System.out.println(StringUtils.isEmpty(null));
    }

    /**
     * 获取指定日期中间的月份
     * @param minDate
     * @param maxDate
     * @return
     * @throws Exception
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }


}
