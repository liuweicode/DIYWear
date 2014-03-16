/**
 * 
 */
package com.yeegol.DIYWear.util;

/**
 * Util class for String process
 * 
 * @author sharl
 * 
 */
public class StrUtil {

	private static final String TAG = StrUtil.class.getName();

	public static int StringToInt(String s) {
		int i = -1;
		try{
			i = Integer.valueOf(s);
		}catch(Exception e){
			i=0;
		}
		return i;
	}

	public static int ObjToInt(Object o) {
		return StringToInt(String.valueOf(o));
	}

	public static String objToString(Object o) {
		return String.valueOf(o);
	}

	public static String intToString(int i) {
		return String.valueOf(i);
	}

	public static String dobToString(double d) {
		return String.valueOf(d);
	}

	public static int dobToInt(double d) {
		return (int) d;
	}

	public static String charToString(CharSequence c) {
		return c.toString();
	}

	public static String longToString(long l) {
		return String.valueOf(l);
	}
}
