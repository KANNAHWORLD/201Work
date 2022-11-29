package bansalsi_CSCI201_Assignment3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil{
	public static String getCurrentTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		Date date = new Date();
		String time = dateFormat.format(date); //2016/11/16 12:08:43
		return time;
	}

	public static String getTime()
	{
		long durationInMillis = System.currentTimeMillis() - startTime;
		long millis = durationInMillis % 1000;
		long second = (durationInMillis / 1000) % 60;
		long minute = (durationInMillis / (1000 * 60)) % 60;
		long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
		
		return time;
	}
	
	public static String getTimeAss3()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String time = dateFormat.format(date); //2016/11/16 12:08:43
		return time;
	}
	
	public static int getSeconds()
	{
		return (int) (((System.currentTimeMillis() - startTime) / 1000));
	}
	
	public static void setStartTime()
	{
		startTime = System.currentTimeMillis();
	}
	
	public static long startTime = 0;
}
