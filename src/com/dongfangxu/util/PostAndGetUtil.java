package com.dongfangxu.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class PostAndGetUtil {
	private static String SESSIONID=null;
	static DefaultHttpClient httpClient;

	
	public static String postAndGetDaet(String url){
		String response=null;
		
		System.out.println(url);
		try{
			
			if(httpClient==null){
				httpClient=new DefaultHttpClient();
			}
			
				
			HttpGet httpGet=new HttpGet(url);
			//httpPost.setHeader("Cookie", "JSESSIONID=" + SESSIONID);
			HttpResponse httpResponse=httpClient.execute(httpGet);
			List<Cookie> cookies = httpClient.getCookieStore().getCookies();
//			 if (cookies.isEmpty()) {
//	                System.out.println("None");
//	            } else {
//	                for (int i = 0; i < cookies.size(); i++) {
//	                    System.out.println("- " + cookies.get(i).toString());
//	                }
//	            }
		//	System.out.println("httpResponse:"+httpResponse.getStatusLine());
			if(httpResponse.getStatusLine().getStatusCode()==200){
				response=EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
			//	System.out.println("response:"+response);
			}
			System.out.println("postAndGetDaet response:"+response);
	            }catch (Exception e) 
	            {
			
			System.out.println("error ");
			response="connect_error";
			e.printStackTrace();
		}
		return response;
	}
//	public static String postAndGetJson(String url){
//		String response=null;
//		if(httpClient!=null){
//			System.out.println("shutdown");
//			httpClient.getConnectionManager().shutdown();
//		}
//		System.out.println(url);
//		
//		try{
//			HttpPost httpPost=new HttpPost(url);
//			httpPost.setHeader("Cookie", "JSESSIONID=" + SESSIONID);
//			StringEntity s = new StringEntity(jsonString, "UTF-8");   // 中文乱码在此解决
//			s.setContentType("application/json");
//			post.setEntity(s);
//			httpClient=new DefaultHttpClient();
//			
//			HttpResponse httpResponse=httpClient.execute(httpPost);
//			if(httpResponse.getStatusLine().getStatusCode()==200){
//				response=EntityUtils.toString(httpResponse.getEntity());
//			}
//			System.out.println("postAndGetDaet response:"+response);
//		}catch (Exception e) {
//			YaoheApp.CONNECT_ERROR_TRUCK_LIST=true;
//			System.out.println("error ");
//			response="connect_error";
//			e.printStackTrace();
//		}
//		return response;
//	}
	public static String register(String url) {
		String response=null;
		//System.out.println(url);
		try{
			HttpPost httpPost=new HttpPost(url);
			DefaultHttpClient httpClient=new DefaultHttpClient();
			HttpResponse httpResponse=httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				response=EntityUtils.toString(httpResponse.getEntity());
			}
		}catch (Exception e) {
			System.out.println("error ");
			e.printStackTrace();
		}
		return response;
	}

}
