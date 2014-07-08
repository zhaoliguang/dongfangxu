package com.dongfangxu.netservice;

import android.util.Log;

import com.dongfangxu.activity.DongFangApp;
import com.dongfangxu.util.JsonUtil;
import com.dongfangxu.util.PostAndGetUtil;

public class Login {
	
	public static int login(String username,String password){
		int flag;
		String url = DongFangApp.urlsMap.get("loginUrl")+"?username="+username+"&password="+password;
		String response = PostAndGetUtil.postAndGetDaet(url);
		//Log.i("response", response);
		if("connect_error".equals(response)){
			flag=DongFangApp.CONNECT_ERROR;
		}else{
			if(JsonUtil.isLoginSuccess(response)){
				flag=DongFangApp.SUCCESS;
			}else{
				flag=DongFangApp.FAIL;
			}
		}
		return flag;
	}

}
