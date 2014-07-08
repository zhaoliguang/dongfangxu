package com.dongfangxu.activity;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.dongfangxu.util.Utils;

import android.app.Application;
import android.widget.Toast;

public class DongFangApp extends Application {
	
	public static DongFangApp mApp = null;
	private Properties props;
	public static Map<String,String>  urlsMap;
	public static int ConnectTimeoutInMillis;
	public static final int SUCCESS=0;
	public static final int FAIL=1;
	public static final int CONNECT_ERROR=2;
	
	public static String SDCARD_ROOT;
	public static String SAVE_ROOT;
	public static String PHOTO_PATH;
	public static String UPLOAD_PATH;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
		initApp();
	}

	private static DongFangApp getInstance() {
		return mApp;
	}
	
	public void initApp(){
		
	
		 new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					// 1�����������ļ�=================== ==========
					props = new Properties();  
				    InputStream in = DongFangApp.class.getResourceAsStream("config.properties");  
				    try {  
				        props.load(in);  
				        in.close();
				     } catch (IOException e) {  
				        e.printStackTrace();  
				    }
//				     handler.sendEmptyMessage(1);
//						sleep(300);
						
				     // 2����ʼ������=============================
				     SDCARD_ROOT = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
				     urlsMap = new HashMap<String, String>();
				     urlsMap.put("loginUrl", props.getProperty("loginUrl")) ;
				     urlsMap.put("getSuoInfoUrl", props.getProperty("getSuoInfoUrl")) ;
				     urlsMap.put("getSwitchgearUrl", props.getProperty("getSwitchgearUrl")) ;
				     urlsMap.put("getVoltageUrl", props.getProperty("getVoltageUrl")) ;
				     urlsMap.put("getSwitchInfoUrl", props.getProperty("getSwitchInfoUrl")) ;
				     urlsMap.put("getExceptionInfoUrl", props.getProperty("getExceptionInfoUrl")) ;
					 String timeout = props.getProperty("ConnectTimeoutInMillis");
					 ConnectTimeoutInMillis = Integer.valueOf(timeout);
					 SAVE_ROOT = props.getProperty("SAVE_ROOT");
					 PHOTO_PATH = props.getProperty("PHOTO_PATH");
					 UPLOAD_PATH = props.getProperty("UPLOAD_PATH");
						
					// 6������ϵͳ�ļ�Ŀ¼=============================
						if(Utils.isHasSdcard()){
							
							System.out.println("create directory...");
							// ������Ŀ¼
						    File destDir = new File(SDCARD_ROOT+SAVE_ROOT);
						    if (!destDir.exists()) {
						        destDir.mkdirs();
						    }
						    // ����.nomedia�ļ���
						    File nomedia = new File(SDCARD_ROOT+SAVE_ROOT+".nomedia/");
						    if (!nomedia.exists()) {
						    	nomedia.mkdirs();
						    }
						    // ������Ƭ�ļ���
						    File portraitDir = new File(SDCARD_ROOT+SAVE_ROOT+PHOTO_PATH);
						    if (!portraitDir.exists()) {
						    	portraitDir.mkdirs();
						    }
						    // �����ϴ��ļ���
						    File uploadDir = new File(SDCARD_ROOT+SAVE_ROOT+UPLOAD_PATH);
						    if (!uploadDir.exists()) {
						    	uploadDir.mkdirs();
						    }
						}
						
//						handler.sendEmptyMessage(6);
//						sleep(500);
						
					// 7�����������������=============================
//						handler.sendEmptyMessage(7);
//						sleep(320);
//						startMinaClient();
					
					// 8�����
//						handler.sendEmptyMessage(8);
//						sleep(200);
					// 9�������¼����
//						handler.sendEmptyMessage(9);
						
						//new getRttThread().start();
					 
				}catch(Exception e){
					System.out.println("initApp Error:"+e.getMessage());
				}
			}
			 
		 }.start();
	    
	}
	
	public void exit(){
		// ��������
        android.os.Process.killProcess(android.os.Process.myPid());
	}
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

}
