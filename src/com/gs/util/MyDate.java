package com.gs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;



public class MyDate {

	public static int getBefore(Date d)  { 
		Calendar ca = Calendar.getInstance();// �õ�һ��Calendar��ʵ��
		ca.setTime(d);// �·��Ǵ�0��ʼ�ģ�����11��ʾ12��
		ca.add(Calendar.DATE, -1);// ���ڼ�1
		Date r = ca.getTime(); // ���
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int m = Integer.valueOf(sdf.format(r));
		return m;
	}
	
	public static int getBefore(int d) throws ParseException  { 
		Calendar ca = Calendar.getInstance();// �õ�һ��Calendar��ʵ��
		ca.setTime(new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(d)));// �·��Ǵ�0��ʼ�ģ�����11��ʾ12��
		ca.add(Calendar.DATE, -1);// ���ڼ�1
		Date r = ca.getTime(); // ���
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int m = Integer.valueOf(sdf.format(r));
		return m;
	}

}
