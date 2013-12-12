package com.gs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;



public class MyDate {

	public static int getBefore(Date d)  { 
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(d);// 月份是从0开始的，所以11表示12月
		ca.add(Calendar.DATE, -1);// 日期减1
		Date r = ca.getTime(); // 结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int m = Integer.valueOf(sdf.format(r));
		return m;
	}
	
	public static int getBefore(int d) throws ParseException  { 
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(d)));// 月份是从0开始的，所以11表示12月
		ca.add(Calendar.DATE, -1);// 日期减1
		Date r = ca.getTime(); // 结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int m = Integer.valueOf(sdf.format(r));
		return m;
	}

}
