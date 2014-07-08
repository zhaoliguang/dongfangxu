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
import com.dongfangxu.util.PostAndGetUtil;

public class GetExceptionInfo {
	
public static int getExceptionInfo(int page,List list){
		
		int flag;
		String url = DongFangApp.urlsMap.get("getExceptionInfoUrl")+"?page="+page;
		Log.i("url", url);
		String response = PostAndGetUtil.postAndGetDaet(url);
		if(response==null){
			flag=DongFangApp.FAIL;
		}
		else if("connect_error".equals(response)){
			flag=DongFangApp.CONNECT_ERROR;
		}else{
			jsonExceptionToList(response,list);
			flag=DongFangApp.SUCCESS;
		}
		return flag;
	}

	public static void jsonExceptionToList(String response,List list){
		try {
			JSONObject jsonObject = new JSONObject(response);
			int count = jsonObject.getInt("count");
			if(count>0){
			JSONArray exceptionInfoArray = jsonObject.getJSONArray("CVinfo");
			for(int i = 0;i < exceptionInfoArray.length();i++){
				JSONObject cvinfoObject = (JSONObject) exceptionInfoArray.get(i);
				Map map = new HashMap<String, Object>();
				
				ArrayList<String> gridList=new ArrayList<String>();
				gridList.add("相位");
				gridList.add("电压");
				gridList.add("电流");
				gridList.add("功率因数");
				gridList.add("A相");
				gridList.add(String.valueOf(cvinfoObject.get("Ua")));
				gridList.add(String.valueOf(cvinfoObject.get("Ia")));
				gridList.add(String.valueOf(cvinfoObject.get("PFa")));
				
				gridList.add("B相");
				gridList.add(String.valueOf(cvinfoObject.get("Ub")));
				gridList.add(String.valueOf(cvinfoObject.get("Ib")));
				gridList.add(String.valueOf(cvinfoObject.get("PFb")));
				
				gridList.add("C相");
				gridList.add(String.valueOf(cvinfoObject.get("Uc")));
				gridList.add(String.valueOf(cvinfoObject.get("Ic")));
				gridList.add(String.valueOf(cvinfoObject.get("PFc")));
				
				map.put("cid",cvinfoObject.get("cid"));
				map.put("update_time",cvinfoObject.get("update_time"));
				map.put("reason",cvinfoObject.get("reason"));
				map.put("sid",cvinfoObject.get("sid"));
				map.put("gridList",gridList);
				list.add(map);
			}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
