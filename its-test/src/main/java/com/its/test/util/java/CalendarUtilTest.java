package com.its.test.util.java;

import java.util.Date;

import org.junit.Test;

import com.its.common.utils.java.CalendarUtil;


public class CalendarUtilTest {
	
	@Test
	public void test(){
//		System.out.println("获取年、月、日、时、分、秒、毫秒：");
//		System.out.println(CalendarUtil.getCurrYMDHMSM());
//		System.out.print("当前月第一天：");
//		System.out.println(CalendarUtil.getCurrMonthFirstDay());
//		System.out.print("当前月最后一天：");
//		System.out.println(CalendarUtil.getCurrMonthLastDay());
		System.out.print("指定年月第一天：");
		System.out.println(CalendarUtil.getMonthFirstDay(2016,2));
		System.out.print("指定年月第最后一天：");
		System.out.println(CalendarUtil.getMonthLastDay(2016,2));
		System.out.println("根据日期取得星期几：");
		System.out.println(CalendarUtil.getWeekArr(new Date()));
		System.out.println(CalendarUtil.getWeek(new Date()));
	}
}

