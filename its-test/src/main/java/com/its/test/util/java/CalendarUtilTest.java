package com.its.test.util.java;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.its.common.utils.java.CalendarUtil;


public class CalendarUtilTest {
	
	@Test
	public void test(){
		System.out.println("获取年、月、日、时、分、秒、毫秒：");
		System.out.println(CalendarUtil.getCurrYMDHMSM());
		System.out.print("当前月第一天：");
		System.out.println(CalendarUtil.getCurrMonthFirstDay());
		System.out.print("当前月最后一天：");
		System.out.println(CalendarUtil.getCurrMonthLastDay());
		System.out.print("指定年月第一天：");
		System.out.println(CalendarUtil.getMonthFirstDay(2016,2));
		System.out.print("指定年月第最后一天：");
		System.out.println(CalendarUtil.getMonthLastDay(2016,2));
		System.out.println("根据日期取得星期几：");
		System.out.println(CalendarUtil.getWeekArr(new Date()));
		System.out.println(CalendarUtil.getWeek(new Date()));
	}
	
	@Test
	public void test2() {
		try {
			// 当前时间的前一天
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd:HH");
			String tmStr = "2015-5-18:00";
			Date d = sdf2.parse(tmStr);
			System.out.println(d);
			Calendar now = Calendar.getInstance();
			now.setTime(d);
			now.set(Calendar.HOUR, now.get(Calendar.HOUR) - 1);
			System.out.println(sdf2.format(now.getTime()));

			// 最近N天
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			cal.add(Calendar.DATE, -7);
			System.out.println("最近7天" + df2.format(cal.getTime()));
			Calendar ca2 = Calendar.getInstance();
			// 最近N个月
			ca2.add(Calendar.MONTH, -1);// 最近一个月
			System.out.println("最近一个月" + df2.format(ca2.getTime()));
			Calendar ca3 = Calendar.getInstance();
			// 最近N个年
			ca3.add(Calendar.YEAR, -1);// 最近一年
			System.out.println("最近一年" + df2.format(ca3.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

