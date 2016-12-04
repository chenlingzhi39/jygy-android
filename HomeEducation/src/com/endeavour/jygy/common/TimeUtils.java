package com.endeavour.jygy.common;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtils {
	public static final String FORMAT_YEAR = "yyyy";
	public static final String FORMAT_MONTH_DAY = "MM月dd日";
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_TIME = "HH:mm";
	public static final String FORMAT_MONTH_DAY_TIME = "MM月dd日  hh:mm";
	public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm";
	public static final String FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	private static final int YEAR = 365 * 24 * 60 * 60;
	private static final int MONTH = 30 * 24 * 60 * 60;
	private static final int WEEK = 7 * 24 * 60 * 60;
	private static final int THREE_DAY = 3 * 24 * 60 * 60;
	private static final int DAY = 24 * 60 * 60;
	private static final int HOUR = 60 * 60;
	private static final int MINUTE = 60;

	/**
	 * 根据时间戳获取描述性时间，如3分钟前，1天前
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @return 时间字符串
	 */
	public static String getDescriptionTimeFromTimestamp(long timestamp) {
		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		// System.out.println("timeGap: " + timeGap);
		String timeStr = null;
		if (timeGap > YEAR) {
			timeStr = timeGap / YEAR + "年前";
		} else if (timeGap > MONTH) {
			timeStr = timeGap / MONTH + "个月前";
		} else if (timeGap > DAY) {// 1天以上
			timeStr = timeGap / DAY + "天前";
		} else if (timeGap > HOUR) {// 1小时-24小时
			timeStr = timeGap / HOUR + "小时前";
		} else if (timeGap > MINUTE) {// 1分钟-59分钟
			timeStr = timeGap / MINUTE + "分钟前";
		} else {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	/**
	 * 根据时间戳获取完整的描述性时间，如3分钟前，1天前
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @return 时间字符串
	 */
	public static String getDescriptionTimeFromTimestampFull(long timestamp) {
		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		// System.out.println("timeGap: " + timeGap);
		String timeStr = null;
		if (timeGap > YEAR) {
			timeStr = timeGap / YEAR + "年前";
		} else if (timeGap > MONTH) {
			timeStr = timeGap / MONTH + "个月前";
		} else if (timeGap > DAY) {// 1天以上
			timeStr = timeGap / DAY + "天前";
		} else if (timeGap > HOUR) {// 1小时-24小时
			timeStr = timeGap / HOUR + "小时前";
		} else if (timeGap > MINUTE) {// 1分钟-59分钟
			timeStr = timeGap / MINUTE + "分钟前";
		} else {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	/**
	 * 获取描述性时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getDescriptionTimeFromTimestamp(String date) {
		if (TextUtils.isEmpty(date)) {
			return "";
		}
		date = translateTime(date);
		try {

			long timelong = stringToLong(date, "yy-MM-dd HH:mm:ss");

			long currentTime = System.currentTimeMillis();
			long timeGap = (currentTime - timelong) / 1000;
			if (timeGap > THREE_DAY) {
				return date.substring(0, 10);
			} else if (timeGap > DAY) {
				return timeGap / DAY + "天前";
			} else if (timeGap > HOUR) {
				return timeGap / HOUR + "小时前";
			} else if (timeGap > MINUTE) {
				return timeGap / MINUTE + "分钟前";
			} else if (timeGap > 0) {
				return "刚刚";
			} else {
				return date.substring(0, 10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取描述性时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getDescriptionTimeFromTimestampFood(String date) {
		if (TextUtils.isEmpty(date)) {
			return "";
		}
		date = translateTime(date);
		try {

			long timelong = stringToLong(date, "yy-MM-dd");
			long currentTime = System.currentTimeMillis();
			long timeGap = Math.abs((currentTime - timelong) / 1000);
			if (timeGap > YEAR) {
				return date;
			} else {
				return date.substring(5, 10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取描述性时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getDescriptionTimeFromDate(String date) {
		if (TextUtils.isEmpty(date)) {
			return "";
		}
		date = translateTime(date);
		try {

			long timelong = stringToLong(date, "yy-MM-dd");

			long currentTime = System.currentTimeMillis();
			long timeGap = (currentTime - timelong) / 1000;
			if (timeGap > THREE_DAY) {
				return date.substring(0, 10);
			} else if (timeGap > DAY) {
				return timeGap / DAY + "天前";
			} else if (timeGap > HOUR) {
				return timeGap / HOUR + "小时前";
			} else if (timeGap > MINUTE) {
				return timeGap / MINUTE + "分钟前";
			} else if (timeGap > 0) {
				return "刚刚";
			} else {
				return date.substring(0, 10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当前日期的指定格式的字符串
	 * 
	 * @param format
	 *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getCurrentTime(String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE_TIME);
		} else {
			sdf.applyPattern(format);
		}
		return sdf.format(new Date());
	}

	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	public static String longToString(long currentTime) {
		String strTime = "";
		Date date = longToDate(currentTime, "yy-MM-dd HH:mm");// long类型转成Date类型
		strTime = dateToString(date, "yy-MM-dd HH:mm"); // date类型转成String
		return strTime;
	}

	public static String longToString_ymd(long currentTime) {
		String strTime = "";
		Date date = longToDate(currentTime, "yy-MM-dd");// long类型转成Date类型
		strTime = dateToString(date, "yy-MM-dd"); // date类型转成String
		return strTime;
	}

	public static String longToString(long currentTime, String formatType) {
		String strTime = "";
		Date date = longToDate(currentTime, formatType);// long类型转成Date类型
		strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	public static Date stringToDate(String strTime, String formatType) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		try {
			date = formatter.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date longToDate(long currentTime, String formatType) {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

	public static long stringToLong(String strTime, String formatType) {
		Date date = stringToDate(strTime, formatType); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	public static long dateToLong(Date date) {
		return date.getTime();
	}

	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(new Date(time));
	}

	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}

	public static String getChatTime(long timesamp) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(timesamp);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		switch (temp) {
		case 0:
			result = "今天 " + getHourAndMin(timesamp);
			break;
		case 1:
			result = "昨天 " + getHourAndMin(timesamp);
			break;
		case 2:
			result = "前天 " + getHourAndMin(timesamp);
			break;

		default:
			result = getTime(timesamp);
			break;
		}

		return result;
	}

	/**
	 * 格式化日期字符串
	 * 
	 * @param sdate
	 *            日期字符串
	 * @param format
	 *            格式
	 * @return
	 */
	public static String dateFormat(String sdate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		java.sql.Date date = java.sql.Date.valueOf(sdate);
		String dateString = formatter.format(date);

		return dateString;
	}

	/**
	 * 返回日期间隔天数
	 * 
	 * @param sd
	 *            起始日期
	 * @param ed
	 *            结束日期
	 * @return
	 */
	public static long getIntervalDays(String sd, String ed) {
		return ((java.sql.Date.valueOf(ed)).getTime() - (java.sql.Date
				.valueOf(sd)).getTime()) / (3600 * 24 * 1000);
	}

	/**
	 * 返回格式化 日期
	 * 
	 * @param sDate
	 * @param dateFormat
	 * @return
	 */
	public static Date getDate(String sDate, String dateFormat) {
		SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
		ParsePosition pos = new ParsePosition(0);

		return fmt.parse(sDate, pos);
	}

	/**
	 * 返回当前日期 无时间
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 返回当前日期+时间
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDate(Date date) {
		return getFormatDateTime(date, "yyyy-MM-dd");
	}
	
	/**
	 * 格式化日期 yyyy-MM-dd
	 * 
	 * @param Long
	 * @return
	 */
	public static String getFormatDate(Long ldate) {
		Date day = new Date(ldate);
		return getFormatDateTime(day, "yyyy-MM-dd");
	}
	
	/**
	 * 格式化当前日期
	 * 
	 * @param format
	 *            格式串
	 * @return
	 */
	public static String getFormatDate(String format) {
		return getFormatDateTime(new Date(), format);
	}

	/**
	 * 格式化
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormatDateTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		try {
			String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
					"星期六" };
			String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			return weekDaysName[intWeek];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(String date) {
		try {
			String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
					"星期六" };
			String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(string2Data(date));
			int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			return weekDaysName[intWeek];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Date string2Data(String date) {
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String data2String(Date date) {
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String formatBirthday(int selectYear, int selectMonth,
			int selectDay) {
		String birthday = "";
		if (selectYear < 9) {
			birthday = "0" + selectYear;
		} else {
			birthday = "" + selectYear;
		}
		if (selectMonth < 9) {
			birthday = birthday + "-" + "0" + selectMonth;
		} else {
			birthday = birthday + "-" + selectMonth;
		}
		if (selectDay < 9) {
			birthday = birthday + "-0" + selectDay;
		} else {
			birthday = birthday + "-" + selectDay;
		}
		return birthday;
	}

	public static String translateTime(String time) {
		try {
			if (time.contains("T")) {
				time = time.replace("T", " ");
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return time;
	}

	public static String translateTimeOnly(String time) {
		try {
			if (time.contains("T")) {
				time = time.replace("T", " ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	public static String translateTimeOnlyData(String time) {
		try {
			if (time.contains("T")) {
				return time.substring(0, time.indexOf("T"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	public static String translateTimeOnlyTime(String time) {
		try {
			if (time.contains("T")) {
				return time.substring(time.indexOf("T") + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 日期格式化处理
	 * 
	 * @param formatType
	 * @return
	 */
	public static SimpleDateFormat getFormatPatternByType(String formatType) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		if ("time".equals(formatType)) {
			sdf.applyPattern("HH:mm:ss");
			// sdf.applyPattern("HH:mm");
		} else if ("date".equals(formatType)) {
			sdf.applyPattern("yyyy-MM-dd");
		} else {
			sdf.applyPattern("yyyy-MM-dd HH:mm");
		}
		return sdf;
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return List<Date>
	 */
	@SuppressWarnings("unchecked")
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List lDate = new ArrayList();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

	/**
	 * 是否在时间范围内 (时间格式:10:20)
	 * 
	 * @return
	 */
	public static boolean isTimeRange(String startTime, String endTime) {
		long longStartTime;
		if (TextUtils.isEmpty(startTime)) {
			longStartTime = System.currentTimeMillis();
		} else {
			longStartTime = stringToLong(startTime, "yyyy-MM-dd HH:mm");
		}
		long longEndTime;
		if (TextUtils.isEmpty(endTime)) {
			longEndTime = System.currentTimeMillis();
		} else {
			longEndTime = stringToLong(endTime, "yyyy-MM-dd HH:mm");
		}
		if (longStartTime < longEndTime) {
			return false;
		}
		return isTimeRange(startTime, endTime);
	}

	/**
	 * 是否在时间范围内 (时间格式:10:20)
	 * 
	 * @return
	 */
	public static boolean isTimeRange(long startTime, long endTime) {
		long currentTimeLong = System.currentTimeMillis();
		if (currentTimeLong >= startTime && currentTimeLong <= endTime) {
			return true;
		}
		return false;
	}

}