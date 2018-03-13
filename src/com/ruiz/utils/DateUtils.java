package com.ruiz.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static String getCurrentTimeStr() {
		Date current = getCurrentTime();
		String nowtime = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(current);
		return nowtime;
	}

	public static Date getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();;
		return date;
	}
	
	public static long getCurrentTimeMillisec() {
		return getCurrentTime().getTime();
	}
	
	public static long getTimeElapsedInMillisec(long beforeMillisec) {
		return getCurrentTime().getTime() - beforeMillisec;
	}

}
