package com.dongfangxu.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.dongfangxu.R;
import com.dongfangxu.component.MyPointBuilder;
import com.dongfangxu.component.MyProgressDialog;
import com.dongfangxu.netservice.Login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginActivity extends Activity implements OnClickListener {
	ImageButton iBtnLogin, iBtnRegister;
	EditText etAccount, etPassword;
	SharedPreferences settings;
	CheckBox cbSavepwd, cbAutologin;
	String userName,passWord;
	private MyProgressDialog myProgressDialog;
	private DongFangApp dongFangApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		dongFangApp=(DongFangApp) getApplication();
		iBtnLogin = (ImageButton) findViewById(R.id.ibtn_login);
		iBtnRegister = (ImageButton) findViewById(R.id.ibtn_register);
		settings =  getSharedPreferences("SETTING_SAVE_PASS_CHECK", 0);
		etAccount = (EditText) findViewById(R.id.etAccount);
		etPassword = (EditText) findViewById(R.id.etPassword);
		cbSavepwd = (CheckBox) findViewById(R.id.cbSavepwd);
		cbAutologin = (CheckBox) findViewById(R.id.cbAutologin);

		iBtnLogin.setOnClickListener(this);
		iBtnRegister.setOnClickListener(this);
		cbAutologin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				 if (isChecked == true) {
					cbSavepwd.setChecked(true);

				}

			}

		});
		boolean isChecked = settings.getBoolean("SAVE_PASS_CHECK", false);
		boolean isAutologin = settings.getBoolean("SAVE_PASS_AUTOLOGIN", false);
		if (isChecked) {
			String userName = settings.getString("SAVE_PASS_USERName", "");
			String userMm = settings.getString("SAVE_PASS_USERMM", "");
			etAccount.setText(userName);
			etPassword.setText(userMm);
			etAccount.setSelection(etAccount.length());
			cbSavepwd.setChecked(true);
            
//			if (isAutologin) {
//				cbAutologin.setChecked(true);
//				Toast.makeText(getApplicationContext(),
//						"自动登录！", 500).show();
//				login();
//			}
		}
	}
	private void login()
	{
		
		userName=etAccount.getText().toString();
		passWord=etPassword.getText().toString();
		
	//	startActivity(new Intent(LoginActivity.this,MainActivity.class));
		myProgressDialog=new MyProgressDialog(this, "正在登录，请稍后...");
		new LoginAsyncTask().execute();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode==10086)
		{
			System.out.println("Result 登录");
			String jybh = settings.getString("SAVE_PASS_JYBH", "");
			String jymm = settings.getString("SAVE_PASS_JYMM", "");
			etAccount.setText(jybh);
			etPassword.setText(jymm);
			etAccount.setSelection(etAccount.length());
			cbSavepwd.setChecked(true);
			login();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.ibtn_login:// 登录
			 etAccount.getText().toString();
			  if("".equals( etAccount.getText().toString()) 
					  ||"".equals( etPassword.getText().toString()))
			  { Toast.makeText(getApplicationContext(), "用户名或密码不能为空！", 500).show();
			      break;
			  }
			  
			 login();
			break;

		}
	}
	
	public class LoginAsyncTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			
			int flag = Login.login(userName, passWord);
			
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			myProgressDialog.close();
			switch (flag) {
			case DongFangApp.SUCCESS:
				//SharedPreferencesUtil.addTruckRegister(TruckInfoActivity.this, true);
				//记住用户名、密码、自动登录
				settings.edit().putBoolean("SAVE_PASS_CHECK", cbSavepwd.isChecked())
				.putBoolean("SAVE_PASS_AUTOLOGIN", cbAutologin.isChecked())
				.putString("SAVE_PASS_USERName", etAccount.getText().toString())
				.putString("SAVE_PASS_USERMM", etPassword.getText().toString())
				.commit();
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				
				LoginActivity.this.finish();
				break;
			case DongFangApp.FAIL:
				new MyPointBuilder(LoginActivity.this, "提示", "用户名或密码错误", "确定");
				break;
			case DongFangApp.CONNECT_ERROR:
				Toast.makeText(LoginActivity.this, "网络不通，请稍候再试", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}
	
	
}
