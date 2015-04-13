package com.ceolt.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*************************************
 * 
 * @description 日期工具类 
 * @author yejf
 * @date 2013-7-8 下午2:48:48
 * @version jdk1.6
 *
 */
public class DateUtil {

	/*****************************
	 * 获取当前的系统日期
	 * @return
	 */
	public static Date getCurrentDate(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	public static Date getDate(int y, int m, int d) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, y);
		cal.set(Calendar.MONTH, m);
		cal.set(Calendar.DAY_OF_MONTH ,d);
		return cal.getTime();
	}
	
	/*****************************
	 * 采用 yyyy-MM-dd hh24:mi:ss来格式化日期
	 * @param d 日期
	 * @return
	 */
	public static String format(Date d) {
		final String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}
	
	/******************************************
	 * 把秒钟转换成可读性更好的时长显示：
	 * 如：59 -> 59秒
	 *        2000 ->  33分钟20秒
	 *        4000 ->  1小时6分钟40秒
	 *        
	 * @param sec 秒数
	 * @return 返回可读性更好的字符串
	 */
	public static String convertSeconds(long sec) {
		final String H = "小时";
		final String M = "分钟";
		final String S = "秒";
		StringBuilder builder = new StringBuilder();
		//计算机 出小时、分钟、秒
		int h = (int)(sec / 3600);
		int m = (int)(sec % 3600 / 60);
		int s = (int)(sec % 60);
		//拼接成字符串
		if(h != 0) {
			builder.append(h).append(H);
		}
		if(m != 0) {
			builder.append(m).append(M);
		}
		builder.append(s).append(S);
		return builder.toString();
	}
	
}
