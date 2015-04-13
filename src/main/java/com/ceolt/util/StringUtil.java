package com.ceolt.util;

public class StringUtil {


	private static final String REGEX = ",";
	
	/******************************
	 * 把一串通过 REGEX 连接起来的字符串，切割成字符串数组
	 * @param str
	 * @return
	 */
	public static String[] toStringArray(String str){
		return str.split(REGEX);
	}

	/*************************
	 * 把字符串数组中的每个元素通过　REGEX　拼接起来，形成一个新的字符串
	 * @param strArr
	 * @return
	 */
	public static String toString(String[] strArr){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<strArr.length;i++) {
			
			if(strArr[i].trim().length() == 0){
				System.out.println("---------------------"+strArr[i]);
			}
			
			builder.append(strArr[i]);
			if(i != strArr.length - 1){
				builder.append(REGEX);
			}
		}
		return builder.toString();
	}
	
}
