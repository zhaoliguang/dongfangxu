package com.dongfangxu.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonUtil {

	public static boolean isLoginSuccess(String response){
		boolean success=false;
		try{
			JSONObject jsonObject=new JSONObject(response);
			String is_success=jsonObject.getString("result");
			if(is_success.equals("success")){
				success=true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}


}
