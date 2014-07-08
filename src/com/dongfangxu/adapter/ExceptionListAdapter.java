package com.dongfangxu.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dongfangxu.R;
import com.dongfangxu.component.MyGridView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ExceptionListAdapter extends BaseAdapter {
	
	private LayoutInflater mLayoutInflater;  
	private List< Map<String, Object>> mSelfData;  
	private ArrayList<String> arrayList;
	private ExceptionGridAdapter exceptionGridAdapter ;
	
	public ExceptionListAdapter(Context context, List< Map<String, Object>> data) {
		// TODO Auto-generated constructor stub
		 this.mSelfData = data;  
	     this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	     exceptionGridAdapter = new ExceptionGridAdapter(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		 return mSelfData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mSelfData.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(position>=mSelfData.size())
			return null;
		if(convertView==null){
			convertView=mLayoutInflater.inflate(R.layout.exception_grid_info, null);
		}
		TextView tx_suo_id = (TextView) convertView.findViewById(R.id.tx_suo_id);
		TextView tx_gui_id = (TextView) convertView.findViewById(R.id.tx_gui_id);
		TextView tx_update_time = (TextView) convertView.findViewById(R.id.tx_update_time);
		TextView tx_exception_reason = (TextView) convertView.findViewById(R.id.tx_exception_reason);
		MyGridView myGridView=(MyGridView) convertView.findViewById(R.id.grid_View);
	
		tx_suo_id.setText(mSelfData.get(position).get("sid").toString());
		tx_gui_id.setText(mSelfData.get(position).get("cid").toString());
		tx_update_time.setText(mSelfData.get(position).get("update_time").toString());
		tx_exception_reason.setText(mSelfData.get(position).get("reason").toString());
		arrayList=(ArrayList)mSelfData.get(position).get("gridList");
		exceptionGridAdapter.setArrayList(arrayList);
		myGridView.setAdapter(exceptionGridAdapter);
	
		return convertView;
	}
}
