package net.huadong.pd.android.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDeal {
	/**
	 * 返回当前日期的值
	 * @return
	 */
	public static long getCurDate(){
		return new Date().getTime();
	}
	/**
	 * 返回当前日期的字符串内容
	 * @param partten 日期格式
	 * @return
	 */
	public static String getCurDate(String partten){
		SimpleDateFormat format = new SimpleDateFormat(partten);
		return format.format(new Date());
	}
	
	/**
	 * 返回格式为yyyy.mm.dd EEEE日期的字符串
	 * @param dateValue
	 * @return
	 */
	public static String getSectionFormatDate(long dateValue){
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd EEEE");
		Date date = new Date(dateValue);
		return format.format(date);
	}
	
	public static long addDay(long curDate,int days){
		Date date = new Date(curDate);
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, days);
		return calender.getTime().getTime();
	}
	
	public static long getDaysBetweenTimes(long date){
		return ((getCurDate()-date)/1000)/(24*3600);
	}
	public static long getHoursBetweenTimes(long date){
		return ((getCurDate()-date)/1000)/(3600);
	}

}
