package org.unclesky4.Utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间类工具
 * @author uncle
 *
 */
public class DateUtils {
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTime() {
		Date time = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(time);
		return dateString;
	}

	/**
	 * 时间前推或后推分钟,其中minute表示分钟.
	 */
	public static String deadLine(String startTime, int minute) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String mydate1 = "";
	    try {
	        Date date1 = format.parse(startTime);
	        long Time = (date1.getTime() / 1000) + minute * 60;
	        date1.setTime(Time * 1000);
	        mydate1 = format.format(date1);
	      } catch (Exception e) {
	      }
	      return mydate1;
	}
	
	/**
	 * 两个时间之间的天数
	 * @param nowTime      yyyy-MM-dd HH:mm:ss
	 * @param lastTime     yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long getHours(String nowTime, String lastTime) {
	    if (nowTime == null || nowTime.equals(""))
	      return 0;
	    if (lastTime == null || lastTime.equals(""))
	      return 0;
	    // 转换为标准时间
	    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    java.util.Date date1 = null;
	    java.util.Date date2 = null;
	    try {
	    	date1 = myFormatter.parse(nowTime);
	    	date2 = myFormatter.parse(lastTime);
	    } catch (Exception e) {
	    }
	    long hour = (date2.getTime() - date1.getTime()) / (60 * 60 * 1000);
	    return hour;
	}
	
	/**
	 * 某个时间距离现在时间的秒数
	 * @param time  格式： yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long getSeconds(String time) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long seconds = 0L;
		try {
			Date date = myFormatter.parse(time);
			seconds = (date.getTime() - new Date().getTime())/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return seconds;
	}
	
	/**
	 * String类型的日期转为Date类型
	 * @param time    格式： yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getDate(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取当月的天数
	 * @return
	 */
	public static int getCurrentMonthDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		int maxDay = calendar.get(Calendar.DATE);
		return maxDay;
	}
	
	/**
	 * 根据 年、月 获取对应的月份的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDayByYearMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		int maxDay = calendar.get(Calendar.DATE);
		return maxDay;
	}
	
	/**
	 * 根据日期 找到对应日期的 星期几
	 * @param date
	 * @return
	 */
	public static String getDayOfWeekByDate(String date) {
		String dayOfWeek = "-1";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
			Date myDate = format.parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat("E");
			String string = formatter.format(myDate);
			dayOfWeek = string;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return dayOfWeek;
	}
  
	/**
	 * 获取本周一时间，然后+/-1就是下周/上周一的时间
	 * @param date
	 * @return
	 */
    public static Date getThisWeekMonday(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        // 获得当前日期是一个星期的第几天  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);  
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);  
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);  
        return cal.getTime();  
    } 
    
    /**
     * 上周一的时间
     * @param date
     * @return
     */
    public static Date geLastWeekMonday(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getThisWeekMonday(date));  
        cal.add(Calendar.DATE, -7);  
        return cal.getTime();  
    }  
  
    /**
     * 下周一的时间
     * @param date
     * @return
     */
    public static Date getNextWeekMonday(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getThisWeekMonday(date));  
        cal.add(Calendar.DATE, 7);  
        return cal.getTime();  
    }
    
    /**
    * 得到本周周五日期
    * @return
    */
    public static String getWeekFriday(){
    SimpleDateFormat formater=new SimpleDateFormat("MM/dd/yyyy");
    	//Calendar cal=new GregorianCalendar();
    	Calendar cal = Calendar.getInstance();  
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 4);
        Date last=cal.getTime();
        return  formater.format(last);
    }
    /**
    * 获取当月第一天
    * @return
    */
    public static String getMonthFirstday(){
    	SimpleDateFormat formater=new SimpleDateFormat("MM/dd/yyyy");
    	Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = formater.format(c.getTime());
        return first;
    }
    
    /**
     * 当前周是今年的第几周
     * @return
     */
    public static int getWeekOfYear() {
    	return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 主函数--测试
     * @param args
     */
    public static void main(String[] args) {
    	System.out.println(getWeekFriday());
    	System.out.println(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
	}
}
