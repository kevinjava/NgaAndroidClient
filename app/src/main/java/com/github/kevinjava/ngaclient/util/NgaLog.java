package com.github.kevinjava.ngaclient.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class NgaLog {
	static final boolean LOG = true;

	public static void i(String tag, String msg) {
		if (LOG) {
			i(tag, msg,false);
		}
	}
	
	private static final String TAG = "NgaLog:-";
	
	/**
	 * 
	 * @param tag
	 * @param string
	 * @param forceTraceLog true: forceTrace for uploading to choreo
	 */
	public static void i(String tag, String string, boolean forceTraceLog){
		if (LOG)
			android.util.Log.i(TAG + tag + "-"+ Thread.currentThread().getName(), string);
	}

	public static void e(String tag, String string) {
		if (LOG)
			android.util.Log.e(TAG + tag, string);
	}

	public static void e(String tag, String string, Throwable e) {
		if (LOG)
			android.util.Log.e(TAG + tag, string, e);
	}
	
	public static void e(String tag, Throwable e) {
		if (LOG)
			android.util.Log.e(TAG + tag, "", e);
	}

	public static void d(String tag, String string) {
		d(tag, string, false);
	}
	
	/**
	 * 
	 * @param tag
	 * @param string
	 * @param forceTraceLog true: forceTrace for uploading to choreo
	 */
	public static void d(String tag, String string, boolean forceTraceLog) {
		if (LOG)
			android.util.Log.d(TAG + tag + "-"+ Thread.currentThread().getName(), string);
	}

	public static void v(String tag, String string) {
		if (LOG)
			android.util.Log.v(TAG + tag, string);
	}

	public static void w(String tag, String string) {
		if (LOG)
			android.util.Log.w(TAG + tag, string);
	}
	
	public static String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		return sw.toString();
	}
}
