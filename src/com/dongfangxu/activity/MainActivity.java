package com.dongfangxu.activity;
import com.dongfangxu.R;
import com.dongfangxu.component.MyPointBuilder;
import com.dongfangxu.fragment.FragmentIndicator;
import com.dongfangxu.fragment.FragmentIndicator.OnIndicateListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public class MainActivity extends FragmentActivity{

	public static Fragment[] mFragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setFragmentIndicator(0);
	
	}
	
	private void setFragmentIndicator(int whichIsDefault) {
		mFragments = new Fragment[3];
		mFragments[0] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_voltage);
		mFragments[1] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_switch);
		mFragments[2] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_exception);
		
		
		getSupportFragmentManager().beginTransaction().hide(mFragments[0])
		.hide(mFragments[1]).hide(mFragments[2])
		.show(mFragments[whichIsDefault]).commit();

		FragmentIndicator mIndicator = (FragmentIndicator) findViewById(R.id.indicator);
		FragmentIndicator.setIndicator(whichIsDefault);
		mIndicator.setOnIndicateListener(new OnIndicateListener() {
			@Override
			public void onIndicate(View v, int which) {
				getSupportFragmentManager().beginTransaction()
				.hide(mFragments[0]).hide(mFragments[1])
				.hide(mFragments[2])
				.show(mFragments[which]).commit();
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		exit();
	}
	void exit(){
		new AlertDialog.Builder(this).setTitle("提示").setMessage("确认退出吗？")
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}

		})
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				MainActivity.this.finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			}

		}).show();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
