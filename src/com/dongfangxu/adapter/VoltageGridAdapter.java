package com.dongfangxu.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VoltageGridAdapter extends BaseAdapter {
	
	
	private  ArrayList< String> mSelfData;  
	private Context context;
	
	public VoltageGridAdapter(Context context) {
		// TODO Auto-generated constructor stub
		 this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSelfData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mSelfData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView result = new TextView(context);  
        result.setText(mSelfData.get(position));  
        result.setTextColor(Color.BLACK);  
        result.setTextSize(16);  
        result.setLayoutParams(new AbsListView.LayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));  
        result.setGravity(Gravity.CENTER);  
        result.setBackgroundColor(Color.WHITE); //…Ë÷√±≥æ∞—’…´  
        return result;  

	}
	
	public void setArrayList( ArrayList< String> data){
		
		this.mSelfData = data;
	}

}
