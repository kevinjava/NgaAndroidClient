package com.kevinjava.ngaclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class TextViewColorUtil {
	private static Pattern pattern = Pattern.compile("\\[[^\\]]*\\]");
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

	public static SpannableStringBuilder getForumBodyStyle(String text) {
		SpannableStringBuilder style = new SpannableStringBuilder(text);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			style.setSpan(new ForegroundColorSpan(Color.GRAY), start, end,
					Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		return style;
	}
	
	public static String formatDate(String time){
		long timeL = Long.parseLong(time);
		if((new Date().getTime() - timeL)< 60000){
			return "刚刚";
		}
		Date date = new Date(Long.parseLong(time));
		return dateFormat.format(date);
	}
}
