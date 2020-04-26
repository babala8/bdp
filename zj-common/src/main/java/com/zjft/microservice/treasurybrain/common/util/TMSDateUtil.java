package com.zjft.microservice.treasurybrain.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yuanliu
 * @time 2018-08-08
 * 
 **/
public class TMSDateUtil {
	
	/**
	 * 判断字符串是否是日期格式字符串并转换成需要的格式返回
	 * @author yuanliu
	 * @param dateStr original String to check 
	 * @param dateFormate format needed
	 * @return String "invalid" or formateStr
	 * 
	 * */
	public static String isValidDate(String dateStr,String dateFormate) {
	      String convertSuccess="invalid";
	      //指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
	       SimpleDateFormat format1 = new SimpleDateFormat(dateFormate);
	       
	       try {
	    	   //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	          format1.setLenient(false);
	          convertSuccess=format1.format(format1.parse(dateStr));
	          return convertSuccess;
	       } catch (ParseException e) {
	          //e.printStackTrace();
	    	  //如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	           convertSuccess="invalid";
	       } 
	       return convertSuccess;
	}
	
	//日期格式转换
	public static String changeForm(String dateStr,String oldForm,String newForm) throws ParseException {
		SimpleDateFormat oldFormat = new SimpleDateFormat(oldForm);
		oldFormat.setLenient(false);
		SimpleDateFormat newFormat = new SimpleDateFormat(newForm);
		String result = newFormat.format(oldFormat.parse(dateStr));
		return result;
	} 
	
	/**
	  * 获取与指定的日期相差i月的日期,日期格式为"yyyy-MM-dd"
	  * @param fixDate 指定的日期（格式为"yyyy-MM-dd"）
	  * @param i 相隔月数（i为整数）
	  * @return 计算后的日期
	  */
	public static String getFixedMonth(String fixDate, int i) {
	  int year = Integer.parseInt(fixDate.substring(0, 4));
	  int month = Integer.parseInt(fixDate.substring(5, 7));
	  int day = Integer.parseInt(fixDate.substring(8, 10));

     Calendar date = Calendar.getInstance();
	  date.set(year, month - 1, day);
	  date.add(Calendar.MONTH, i);
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String sysDate = formatter.format(date.getTime()).toString();
	  return sysDate;
	}
	
	/**
	 * 获取下一旬的开始日期
	 * @param fixDate
	 * @return yyyy-MM-dd
	 */
	public static String getFixedXun(String fixDate) {
		int year = Integer.parseInt(fixDate.substring(0, 4));
		int month = Integer.parseInt(fixDate.substring(5, 7));
		int day = Integer.parseInt(fixDate.substring(8, 10));
		if(day >= 1 && day <= 10) {
			day = 1;
		}else if(day >=11 && day <=20) {
			day = 11;
		}else if(day >= 21 && day <=31) {
			day = 21;
		}
		int lastYear = year;
		int lastMonth = month;
		int lastDay = day;
	    if(day == 1 || day == 11) {
	    	lastDay = day + 10;
	    }else if(day == 21) {
	    	lastDay = 1;
	    	lastMonth = month + 1;
	    	if(lastMonth == 13) {
	    		lastMonth = 1;
	    		lastYear = year + 1;
	    	}
	    }
	    Calendar date = Calendar.getInstance();
		date.set(lastYear, lastMonth - 1, lastDay);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = formatter.format(date.getTime()).toString();
		return sysDate;
	}
	


	
	/** 
	 *  获取当前日期所属旬的开始日期
	 * @param 
	 * @return
	 */
	public static String getXunDate(String fixDate) {
		int year = Integer.parseInt(fixDate.substring(0, 4));
		int month = Integer.parseInt(fixDate.substring(5, 7));
		int day = Integer.parseInt(fixDate.substring(8, 10));
		if(day >= 1 && day <= 10) {
			day = 1;
		}else if(day >=11 && day <=20) {
			day = 11;
		}else if(day >= 21 && day <=31) {
			day = 21;
		}
		Calendar date = Calendar.getInstance();
		date.set(year, month - 1, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = formatter.format(date.getTime()).toString();
		return sysDate;
	}
	
	/** 
	 *  获取当前日期所属旬的结束日期
	 * @param 
	 * @return
	 */
	public static String getXunEndDate(String fixDate) {
		int year = Integer.parseInt(fixDate.substring(0, 4));
		int month = Integer.parseInt(fixDate.substring(5, 7));
		int day = Integer.parseInt(fixDate.substring(8, 10));
		if(day >= 1 && day <= 10) {
			day = 10;
		}else if(day >=11 && day <=20) {
			day = 20;
		}else if(day >= 21 && day <=31) {
			Calendar date = Calendar.getInstance();
			date.set(year, month, 1);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String sysDate = CalendarUtil.getFixedDate(formatter.format(date.getTime()).toString(),-1);
			return sysDate;
		}
		Calendar date = Calendar.getInstance();
		date.set(year, month - 1, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = formatter.format(date.getTime()).toString();
		return sysDate;
	}
	
	/** 
	 *  获取上一旬的开始日期
	 * @param fixDate
	 * @return
	 */
	public static String getBeforeXun(String fixDate) {
		int year = Integer.parseInt(fixDate.substring(0, 4));
		int month = Integer.parseInt(fixDate.substring(5, 7));
		int day = Integer.parseInt(fixDate.substring(8, 10));
		if (day >= 1 && day <= 10) {
			day = 1;
		} else if (day >= 11 && day <= 20) {
			day = 11;
		} else if (day >= 21 && day <= 31) {
			day = 21;
		}
		int beforeYear = year;
		int beforeMonth = month;
		int beforeDay = day;
		if (day == 1) {
			beforeDay = 21;
			beforeMonth = month - 1;
			if (beforeMonth == 0) {
				beforeMonth = 12;
				beforeYear = year - 1;
			}
		} else if (day == 11 || day == 21) {
			beforeDay = day - 10;
		}
		Calendar date = Calendar.getInstance();
		date.set(beforeYear, beforeMonth - 1, beforeDay);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = formatter.format(date.getTime()).toString();
		return sysDate;
	}
	
	/**
	 *    	获取上一旬的结束日期
	 * @params fixDate 日期格式yyyy-MM-dd
	 * @return endDate
	 * */
	public static String getBeforeXunEndDate(String fixDate) {
		int year = Integer.parseInt(fixDate.substring(0, 4));
		int month = Integer.parseInt(fixDate.substring(5, 7));
		int day = Integer.parseInt(fixDate.substring(8, 10));
		if (day >= 1 && day <= 11) {
			return CalendarUtil.getFixedDate(fixDate.substring(0, 7)+"-01", -1);
		} else if (day >= 11 && day <= 20) {
			day = 10;
		} else if (day <= 31 && day >= 21) {
			day = 20;
		}
		Calendar date = Calendar.getInstance();
		date.set(year, month - 1, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = formatter.format(date.getTime()).toString();
		return endDate;
	}
	
	/** 
	 *  获取下一旬的开始日期
	 * @param fixDate 日期格式yyyy-MM-dd
	 * @return startDate
	 */
	public static String getLaterXunStartDate(String fixDate) {
		int year = Integer.parseInt(fixDate.substring(0, 4));
		int month = Integer.parseInt(fixDate.substring(5, 7));
		int day = Integer.parseInt(fixDate.substring(8, 10));
		if (day >= 1 && day <= 10) {
			day = 11;
		} else if (day >= 11 && day <= 20) {
			day = 21;
		} else if (day >= 21 && day <= 31) {
			day = 1;
			if(12==month) {
				month=1;
				year+=1;
			}else {
				month+=1;
			}
		}
		Calendar date = Calendar.getInstance();
		date.set(year, month - 1, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime());
	}
	
	/** 
	 *  获取下一旬的结束日期
	 * @param fixDate 日期格式yyyy-MM-dd
	 * @return startDate
	 */
	public static String getLaterXunEndDate(String fixDate) {
		int year = Integer.parseInt(fixDate.substring(0, 4));
		int month = Integer.parseInt(fixDate.substring(5, 7));
		int day = Integer.parseInt(fixDate.substring(8, 10));
		if (day >= 1 && day <= 10) {
			day = 20;
		} else if (day >= 11 && day <= 20) {
			return getFixedMonth(getFixedDate(fixDate.substring(0, 7)+"-01", -1),1);
		} else if (day >= 21 && day <= 31) {
			day = 10;
			if(12==month) {
				month=1;
				year+=1;
			}else {
				month+=1;
			}
		}
		Calendar date = Calendar.getInstance();
		date.set(year, month - 1, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime());
	}
	
	/**
	  * 获取与指定的日期相差i天的日期,日期格式为"yyyy-MM-dd"
	  * @param fixDate 指定的日期（格式为"yyyy-MM-dd"）
	  * @param i 相隔天数（i为整数）
	  * @return 计算后的日期
	  */
	public static String getFixedDate(String fixDate, int i) {
	  int year = Integer.parseInt(fixDate.substring(0, 4));
	  int month = Integer.parseInt(fixDate.substring(5, 7));
	  int day = Integer.parseInt(fixDate.substring(8, 10));

	  Calendar date = Calendar.getInstance();
	  date.set(year, month - 1, day);
	  date.add(Calendar.DAY_OF_MONTH, i);
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String sysDate = formatter.format(date.getTime()).toString();
	  return sysDate;
	}
	
	/**
	  * 获取与指定的日期相差i天的日期
	  * @param fixDate 指定的日期（格式为"yyyy-MM-dd"）
	  * @param i 相隔天数（i为整数）
	  * @param formName 格式
	  * @return 计算后的日期
	  */
	public static String getFixedDateByForm(String fixDate, int i, String formName) {
	  int year = Integer.parseInt(fixDate.substring(0, 4));
	  int month = Integer.parseInt(fixDate.substring(5, 7));
	  int day = Integer.parseInt(fixDate.substring(8, 10));

	  Calendar date = Calendar.getInstance();
	  date.set(year, month - 1, day);
	  date.add(Calendar.DAY_OF_MONTH, i);
	  SimpleDateFormat formatter = new SimpleDateFormat(formName);
	  String sysDate = formatter.format(date.getTime()).toString();
	  return sysDate;
	}
	
	/**
	  * 获取系统时间
	  * @return 返回系统当前时间字符串，字符串格式为：yyyy-mm-dd
	  */
	public static String getSysTimeYMD() {
	  return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	}
	
	/**
	  * 获取系统时间
	  * @return 返回系统当前时间字符串，字符串格式为：yyyy-mm-dd HH:mm:ss
	  */
	public static String getSysTimeYMDHMS() {
	  return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
	}
	
	/**
	 * 	获取指定旬的开始日期
	 * 	@param date 格式yyyy-MM-dd
	 *  @param count
	 *  @return startDate 格式yyyy-MM-dd
	 * */
	public static String getFixedXunStartDate(String date,int count) {
		if(count >0) {
			for(int i=0;i<count;i++) {
				date = TMSDateUtil.getLaterXunStartDate(date);
			}
		}else if(count<0){
			for(int i=0;i<-count;i++) {
				date = TMSDateUtil.getBeforeXun(date);
			}
		}else {
			date=getXunDate(date);
		}
		
		return date;
	}
	
	/**
	 * 	获取指定的结束日期
	 *  @param date 格式yyyy-MM-dd
	 *  @param count 向前为负，向后为正
	 *  @return startDate 格式yyyy-MM-dd 
	 * */
	public static String getFixedXunEndDate(String date,int count) {
		if(count<0) {
			for(int i=0;i<-count;i++) {
				date = TMSDateUtil.getBeforeXunEndDate(date);
			}
		}else if(count>0){
			for(int i=0;i<count;i++) {
				date = TMSDateUtil.getLaterXunEndDate(date);
			}
		}else {
			date = getXunEndDate(date);
		}
		return date;
	}

}
