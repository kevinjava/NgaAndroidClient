package com.kevinjava.ngaclient.util;

public class TextViewColorUtil {
/**
 * TextView textView = (TextView)findViewById(R.id.testview);  
  
String text = String.format(getResources().getString(R.string.baoxiang), 2,18,"银宝箱");  
       int index[] = new int[3];  
       index[0] = text.indexOf("2");  
       index[1] = text.indexOf("18");  
       index[2] = text.indexOf("银宝箱");  
  
 SpannableStringBuilder style=new SpannableStringBuilder(text);     
           style.setSpan(new ForegroundColorSpan(Color.RED),index[0],index[0]+1,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);      
           style.setSpan(new ForegroundColorSpan(Color.RED),index[1],index[1]+2,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);      
           style.setSpan(new BackgroundColorSpan(Color.RED),index[2],index[2]+3,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);      
           textView.setText(style);  
 */
}
