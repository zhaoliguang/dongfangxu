package com.dongfangxu.netservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.dongfangxu.activity.DongFangApp;
import com.dongfangxu.util.JsonUtil;
import com.dongfangxu.util.PostAndGetUtil;;

public class GetSwitchInfo {
	
	
	public static int getSwitchInfo(int page,List list){
		
		int flag;
		
		String url = DongFangApp.urlsMap.get("getSwitchInfoUrl")+"?page="+page;
		Log.i("url", url);
		String response = PostAndGetUtil.postAndGetDaet(url);
		if(response==null){
			flag=DongFangApp.FAIL;
		}
		else if("connect_error".equals(response)){
			flag=DongFangApp.CONNECT_ERROR;
		}else{
			jsonSwitchToList(response,list);
			flag=DongFangApp.SUCCESS;
		}
		return flag;
	}
	
	public static int getSwitchInfoById(String suoId,String guiId,List list){
		
		int flag;
		String url = DongFangApp.urlsMap.get("getSwitchInfoUrl")+"?sid="+suoId+"&cnum="+guiId;
		Log.i("url", url);
		String response = PostAndGetUtil.postAndGetDaet(url);
		if(response==null){
			flag=DongFangApp.FAIL;
		}
		else if("connect_error".equals(response)){
			flag=DongFangApp.CONNECT_ERROR;
		}else{
			jsonSwitchToList(response,list);
			flag=DongFangApp.SUCCESS;
		}
		return flag;
	}
	public static void jsonSwitchToList(String response,List list){
		try {
			JSONObject jsonObject = new JSONObject(response);
			int count = jsonObject.getInt("count");
			if(count>0){
			JSONArray CVinfoArray = jsonObject.getJSONArray("SWinfo");
			for(int i = 0;i < CVinfoArray.length();i++){
				JSONObject cvinfoObject = (JSONObject) CVinfoArray.get(i);
				Map map = new HashMap<String, Object>();
				map.put("cid",cvinfoObject.get("cid"));
				map.put("update_time",cvinfoObject.get("update_time"));
				map.put("sid",cvinfoObject.get("sid"));
				
				ArrayList<String> gridList=new ArrayList<String>();
				gridList.add("S1:"+boolToString(cvinfoObject.get("S1")));
				gridList.add("S2:"+boolToString(cvinfoObject.get("S2")));
				gridList.add("S3:"+boolToString(cvinfoObject.get("S3")));
				gridList.add("S4:"+boolToString(cvinfoObject.get("S4")));
				gridList.add("S5:"+boolToString(cvinfoObject.get("S5")));
				gridList.add("S6:"+boolToString(cvinfoObject.get("S6")));
				gridList.add("S7:"+boolToString(cvinfoObject.get("S7")));
				gridList.add("S8:"+boolToString(cvinfoObject.get("S8")));
				gridList.add("S9:"+boolToString(cvinfoObject.get("S9")));
				gridList.add("S10:"+boolToString(cvinfoObject.get("S10")));
				gridList.add("S11:"+boolToString(cvinfoObject.get("S11")));
				gridList.add("S12:"+boolToString(cvinfoObject.get("S12")));
				gridList.add("S13:"+boolToString(cvinfoObject.get("S13")));
				gridList.add("S14:"+boolToString(cvinfoObject.get("S14")));
				gridList.add("S15:"+boolToString(cvinfoObject.get("S15")));
				gridList.add("S16:"+boolToString(cvinfoObject.get("S16")));
				map.put("gridList",gridList);
				list.add(map);
			}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String boolToString(Object info){
		boolean turn =(Boolean) info;
		if(turn)
			return "¿ª";
		else
			return "¹Ø";
		
	}

}
