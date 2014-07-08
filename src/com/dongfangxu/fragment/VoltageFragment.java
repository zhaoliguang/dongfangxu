package com.dongfangxu.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.dongfangxu.R;
import com.dongfangxu.activity.DongFangApp;
import com.dongfangxu.activity.LoginActivity.LoginAsyncTask;
import com.dongfangxu.adapter.VoltageListAdapter;
import com.dongfangxu.component.MyPointBuilder;
import com.dongfangxu.component.MyProgressDialog;
import com.dongfangxu.netservice.GetExceptionInfo;
import com.dongfangxu.netservice.GetVoltageInfo;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class VoltageFragment extends Fragment implements OnItemClickListener,OnClickListener{
	
	private View mParent;
	private FragmentActivity mActivity;
	private ListView listView;
	//private VoltageAdapter voltageAdapter;
	private VoltageListAdapter voltageListAdapter;
	private List< Map<String, Object>> mSelfData; 
	private DongFangApp dongFangApp;
	private MyProgressDialog myProgressDialog;
	private boolean searchFlag=false;//标志是否点击查询按钮
	private boolean firstFlag=false;//判断程序是不是第一次运行
	private ImageButton searchButton,previousButton,nextButton;
	private String suo_id,gui_id;
	private EditText ed_suo_id,ed_gui_id;
	private View footview;
	private int dataCount,pageCount,currentPage=1,nextPage=1;
	private  int pageSize=10;
	private  Timer timerGetValtage,timerGetValtaveById; 
	private TimerTask getValtageTask,getValtaveByIdTask;
	private Handler handlerGetAllVoltage,handlerGetById;
	
	public static VoltageFragment newInstance(int index) {
		VoltageFragment voltageFragment = new VoltageFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		voltageFragment.setArguments(args);

		return voltageFragment;
	}
	
	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i("VoltageFragment:", "onActivityCreated");
		mActivity = getActivity();
		mParent = getView();
		
		initview();
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("VoltageFragment", "onStart");
		
		if(firstFlag)
			startTimerGetValtage();
		if(searchFlag)
			startTimerGetValtageById();
		firstFlag=true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("VoltageFragment:", "onCreateView");
		View view = inflater.inflate(R.layout.fragment_voltage, container, false);
		return view;
	} 
	
	
	private void initview() {
		
		listView = (ListView) mParent.findViewById(R.id.lv_voltage);
		footview=mActivity.getLayoutInflater().inflate(R.layout.listview_footview_loadmore, null);
		mSelfData=new ArrayList<Map<String,Object>>();
		//voltageAdapter = new VoltageAdapter(mActivity,mSelfData);
		voltageListAdapter = new VoltageListAdapter(mActivity, mSelfData);
		searchButton=(ImageButton)mParent.findViewById(R.id.search_button);
		searchButton.setOnClickListener(this);
		previousButton=(ImageButton)mParent.findViewById(R.id.previous_page);
		previousButton.setOnClickListener(this);
		nextButton=(ImageButton)mParent.findViewById(R.id.next_page);
		nextButton.setOnClickListener(this);

		ed_suo_id = (EditText) mParent.findViewById(R.id.ed_suo_id);
		ed_gui_id = (EditText) mParent.findViewById(R.id.ed_gui_id);
		listView.addFooterView(footview);
		listView.setAdapter(voltageListAdapter);
		
		handlerGetAllVoltage =new Handler(){
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				new GetVoltageThreadAsyncTask().execute();
			};
		};
		handlerGetById=new Handler(){
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				new GetVoltageByIdThreadAsyncTask().execute();
			};
		};
		listView.setOnItemClickListener(this);
		myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
		new GetVoltageAsyncTask().execute();

	}
	
	public class GetVoltageAsyncTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			mSelfData.clear();
			int flag = GetVoltageInfo.getVoltageinfo(nextPage, mSelfData);
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			myProgressDialog.close();

			dataCount = voltageListAdapter.getCount();
			if(dataCount % pageSize==0)
				pageCount=dataCount/pageSize;
			else
				pageCount=(dataCount/pageSize)+1;
			currentPage=nextPage;
			switch (flag) {
			case DongFangApp.SUCCESS:
				footview.setVisibility(View.GONE);
				voltageListAdapter.notifyDataSetChanged();
				System.out.println("GetVoltageAsyncTask:"  );
				startTimerGetValtage();
				break;
			case DongFangApp.FAIL:
					new MyPointBuilder(mActivity, "提示", "暂无数据", "确定");
				break;
			case DongFangApp.CONNECT_ERROR:
					Toast.makeText(mActivity, "网络不通，请稍候再试", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}
	
	public class GetVoltageByIdAsyncTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			mSelfData.clear();
			int flag = GetVoltageInfo.getVoltageinfoById(suo_id,gui_id, mSelfData);

			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			myProgressDialog.close();
			switch (flag) {
			case DongFangApp.SUCCESS:
				footview.setVisibility(View.VISIBLE);
				voltageListAdapter.notifyDataSetChanged();
				
				startTimerGetValtageById();
				break;
			case DongFangApp.FAIL:
				new MyPointBuilder(mActivity, "提示", "暂无数据", "确定");
				break;
			case DongFangApp.CONNECT_ERROR:
					Toast.makeText(mActivity, "网络不通，请稍候再试", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}
	
	public class GetVoltageByIdThreadAsyncTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			mSelfData.clear();

			int flag = GetVoltageInfo.getVoltageinfoById(suo_id,gui_id, mSelfData);
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {

			switch (flag) {
			case DongFangApp.SUCCESS:

				voltageListAdapter.notifyDataSetChanged();
				
				break;
			case DongFangApp.FAIL:
				new MyPointBuilder(mActivity, "提示", "暂无数据", "确定");
				break;
			case DongFangApp.CONNECT_ERROR:
				Toast.makeText(mActivity, "网络不通，请稍候再试", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}
	
	public class GetVoltageThreadAsyncTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			mSelfData.clear();
			int flag = GetVoltageInfo.getVoltageinfo(currentPage, mSelfData);
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			
			switch (flag) {
			case DongFangApp.SUCCESS:
				voltageListAdapter.notifyDataSetChanged();
				break;
			case DongFangApp.FAIL:
				new MyPointBuilder(mActivity, "提示", "暂无数据", "确定");
				break;
			case DongFangApp.CONNECT_ERROR:
				Toast.makeText(mActivity, "网络不通，请稍候再试", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(arg2==mSelfData.size()){
			ed_suo_id.setText("");
			ed_gui_id.setText("");
			stopTimerGetVlatageById();
			searchFlag=false;
			nextPage=1;
			myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
			new GetVoltageAsyncTask().execute();
		}
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		stopTimerGetValtage();
		stopTimerGetVlatageById();
	}
	public void stopTimerGetValtage(){
		if(timerGetValtage!=null){
			timerGetValtage.cancel();
			System.out.println("stopTimerGetValtage.cancel()"  );
			timerGetValtage=null;
			getValtageTask=null;
		}
	}
	public void stopTimerGetVlatageById(){
		if(timerGetValtaveById!=null){
			timerGetValtaveById.cancel();
			System.out.println(" stopTimerGetVlatageById.cancel()"  );
			timerGetValtaveById=null;
			getValtaveByIdTask=null;
		}
	}
	public void startTimerGetValtage(){
		getValtageTask=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Message message = new Message();
				 handlerGetAllVoltage.sendMessage(message);
				 System.out.println("getValtageTaskId:"+Thread.currentThread().getId());
			}
		};
		System.out.println("startTimerGetValtage:"  );
		timerGetValtage=null;
		timerGetValtage=new Timer();
		timerGetValtage.schedule(getValtageTask, 0, 30000);
	}
	public void startTimerGetValtageById(){
		getValtaveByIdTask=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Message message = new Message();
				 handlerGetById.sendMessage(message);
				 System.out.println("startTimerGetValtageById:"+Thread.currentThread().getId());
			}
		};
		timerGetValtaveById=null;
		timerGetValtaveById=new Timer();
		timerGetValtaveById.schedule(getValtaveByIdTask, 0, 30000);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.search_button:
			mSelfData.clear();
			searchFlag=true;
			
			suo_id = ed_suo_id.getText().toString().trim();
			gui_id = ed_gui_id.getText().toString().trim();
		
			if(suo_id.equals("")){
				Toast.makeText(mActivity, "请输入所号", Toast.LENGTH_LONG).show();
				return;
			}
			if(gui_id.equals("")){
				Toast.makeText(mActivity, "请输入柜号", Toast.LENGTH_LONG).show();
				return;
			}
			
			stopTimerGetValtage();
			
			myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
			new GetVoltageByIdAsyncTask().execute();
			break;
		case R.id.next_page:
			nextPage++;
			if(nextPage<=pageCount){
				myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
				stopTimerGetValtage();

				new GetVoltageAsyncTask().execute();
			}
			else{
				nextPage--;
				Toast.makeText(mActivity, "数据已到底", Toast.LENGTH_LONG).show();
				
			}
			break;
			
		case R.id.previous_page:
			nextPage--;
			if(nextPage>=1){
				myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
				stopTimerGetValtage();
				new GetVoltageAsyncTask().execute();
			}
			else{
				nextPage++;
				Toast.makeText(mActivity, "数据已到底", Toast.LENGTH_LONG).show();
			}

			break;
			
		}
	}
}
