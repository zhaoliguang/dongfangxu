package com.dongfangxu.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Utils {
	
	public static boolean isWifiActive(Context icontext) {
		Context context = icontext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info;
		if (connectivity != null) {
			info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("WIFI")
							&& info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static String getWifiMac(Context icontext){
		WifiManager wifi = (WifiManager) icontext.getSystemService(Context.WIFI_SERVICE); 
		WifiInfo info = wifi.getConnectionInfo(); 
		return info.getMacAddress();
	}
	
	public static String dateFormat(String datetime){
		try{
    		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date d = f.parse(datetime);
			return f.format(d);
    	}catch(Exception e){
    		return datetime;
    	}
	}
	
	public static void toastAlert(Context context,String msg){
    	Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
    }
	
	public static String formatTime() {
		java.util.Date d = new java.util.Date();
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String s = f.format(d);
		return s;
	}
	
	public static String formatTime(int x)
    {
	    String s=""+x;
	    if(s.length()==1) s="0"+s;
	    return s;
	}
	/**
	 * ¼ì²é´æ´¢¿¨ÊÇ·ñ²åÈë
	 * 
	 * @return
	 */
	public static boolean isHasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ÅÐ¶Ï×Ö·û´®ÊÇ·ñ°üº¬ºº×Ö
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str){
		return str.getBytes().length == str.length() ? false:true;
	}
}
