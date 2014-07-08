package com.dongfangxu.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.dongfangxu.R;
import com.dongfangxu.activity.DongFangApp;
import com.dongfangxu.adapter.SwitchListAdapter;
import com.dongfangxu.component.MyPointBuilder;
import com.dongfangxu.component.MyProgressDialog;
import com.dongfangxu.fragment.VoltageFragment.GetVoltageAsyncTask;
import com.dongfangxu.fragment.VoltageFragment.GetVoltageByIdAsyncTask;
import com.dongfangxu.netservice.GetSwitchInfo;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SwitchFragment extends Fragment implements OnItemClickListener,OnClickListener{
	
	private View mParent;
	private FragmentActivity mActivity;
	private ListView listView;
	private SwitchListAdapter switchListAdapter;
	private List<Map<String, Object>> mSelfData; 
	private DongFangApp dongFangApp;
	private String suo_id,gui_id;
	private ImageButton searchButton,previousButton,nextButton;
	private EditText ed_suo_id,ed_gui_id;
	private boolean searchFlag=false,firstFlag=false;//标志是否点击查询按钮
	private MyProgressDialog myProgressDialog;
	private View footview;
	private int dataCount,pageCount,currentPage=1,nextPage=1;	
	private  int pageSize=10;

	private  Timer timerGetSwitch,timerGetSwitchById; 
	private TimerTask getSwitchTask,getSwitchByIdTask;
	private Handler handlerGetAllSwitch,handlerGetById;
	
	
	public static SwitchFragment newInstance(int index) {
		SwitchFragment switchFragment = new SwitchFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		switchFragment.setArguments(args);

		return switchFragment;
	}
	
	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i("SwitchFragment:", "onActivityCreated");
		mActivity = getActivity();
		mParent = getView();
		initview();
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("SwitchFragment", "onStart");
		
		if(firstFlag)
			startTimerGetSwitch();
		if(searchFlag)
			startTimerGetSwitchById();
		firstFlag=true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("SwitchFragment:", "onCreateView");
		View view = inflater.inflate(R.layout.fragment_switch, container, false);
		return view;
	}
	
	private void initview() {
		
		listView = (ListView) mParent.findViewById(R.id.switch_listview);
		footview=mActivity.getLayoutInflater().inflate(R.layout.listview_footview_loadmore, null);
		listView.addFooterView(footview);
		mSelfData=new ArrayList<Map<String,Object>>();
		switchListAdapter = new SwitchListAdapter(mActivity,mSelfData);
		listView.setAdapter(switchListAdapter);
		listView.setOnItemClickListener(this);
		searchButton=(ImageButton)mParent.findViewById(R.id.search_button);
		searchButton.setOnClickListener(this);
		previousButton=(ImageButton)mParent.findViewById(R.id.previous_page);
		previousButton.setOnClickListener(this);
		nextButton=(ImageButton)mParent.findViewById(R.id.next_page);
		nextButton.setOnClickListener(this);
		ed_suo_id = (EditText) mParent.findViewById(R.id.ed_suo_id);
		ed_gui_id = (EditText) mParent.findViewById(R.id.ed_gui_id);
		
		handlerGetAllSwitch =new Handler(){
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				new GetSwitchAsyncThreadTask().execute();
			};
		};
		handlerGetById=new Handler(){
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				new GetSwitchByIdThreadAsyncTask().execute();
			};
		};
		myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
		new GetSwitchAsyncTask().execute();
	
	}
	
	public class GetSwitchAsyncTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			mSelfData.clear();

			int flag = new GetSwitchInfo().getSwitchInfo(nextPage, mSelfData);
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			myProgressDialog.close();
			dataCount = switchListAdapter.getCount();
			if(dataCount % pageSize==0)
				pageCount=dataCount/pageSize;
			else
				pageCount=(dataCount/pageSize)+1;
			currentPage=nextPage;
			switch (flag) {
			case DongFangApp.SUCCESS:
				footview.setVisibility(View.GONE);
				switchListAdapter.notifyDataSetChanged();
				System.out.println("GetVoltageAsyncTask:"  );
				startTimerGetSwitch();
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
	
	public class GetSwitchAsyncThreadTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			mSelfData.clear();
			int flag = new GetSwitchInfo().getSwitchInfo(currentPage, mSelfData);
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			
			switch (flag) {
			case DongFangApp.SUCCESS:
				switchListAdapter.notifyDataSetChanged();
				
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
	
	public class GetSwitchByIdAsyncTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			mSelfData.clear();
			int flag = GetSwitchInfo.getSwitchInfoById(suo_id,gui_id, mSelfData);

			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			
			myProgressDialog.close();
			
			switch (flag) {
			case DongFangApp.SUCCESS:
				footview.setVisibility(View.VISIBLE);
				switchListAdapter.notifyDataSetChanged();
				
				startTimerGetSwitchById();
				break;
			case DongFangApp.FAIL:
				new MyPointBuilder(mActivity, "提示", "暂无数据", "确定");
				listView.addFooterView(footview);
				
				break;
			case DongFangApp.CONNECT_ERROR:
					Toast.makeText(mActivity, "网络不通，请稍候再试", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}
	
	public class GetSwitchByIdThreadAsyncTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			mSelfData.clear();
			int flag = GetSwitchInfo.getSwitchInfoById(suo_id,gui_id, mSelfData);
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {

			switch (flag) {
			case DongFangApp.SUCCESS:
				switchListAdapter.notifyDataSetChanged();
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
			if(arg2==mSelfData.size()){
				ed_suo_id.setText("");
				ed_gui_id.setText("");
				stopTimerGetSwtichById();
				searchFlag=false;
				nextPage=1;
						
					myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
					new GetSwitchAsyncTask().execute();
				}
		
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		stoptimerGetSwitch();
		stopTimerGetSwtichById();
	}
	public void stoptimerGetSwitch(){
		if(timerGetSwitch!=null){
			timerGetSwitch.cancel();
			System.out.println("stoptimerGetSwitch.cancel()"  );
			timerGetSwitch=null;
			getSwitchTask=null;
		}
	}
	public void stopTimerGetSwtichById(){
		if(timerGetSwitchById!=null){
			timerGetSwitchById.cancel();
			System.out.println(" stopTimerGetSwtichById.cancel()"  );
			timerGetSwitchById=null;
			getSwitchByIdTask=null;
		}
	}
	public void startTimerGetSwitch(){
		getSwitchTask=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Message message = new Message();
				 handlerGetAllSwitch.sendMessage(message);
				 System.out.println("getSwitchTaskId:"+Thread.currentThread().getId());
			}
		};
		System.out.println("starttimerGetSwitch:"  );
		timerGetSwitch=null;
		timerGetSwitch=new Timer();
		timerGetSwitch.schedule(getSwitchTask, 0, 30000);
	}
	public void startTimerGetSwitchById(){
		getSwitchByIdTask=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Message message = new Message();
				 handlerGetById.sendMessage(message);
				 System.out.println("starttimerGetSwitchById:"+Thread.currentThread().getId());
			}
		};
			timerGetSwitchById=null;
			timerGetSwitchById=new Timer();
			timerGetSwitchById.schedule(getSwitchByIdTask, 0, 30000);
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
			
			stoptimerGetSwitch();
			
			myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
			new GetSwitchByIdAsyncTask().execute();
			break;
		case R.id.next_page:
			nextPage++;
			if(nextPage<=pageCount){
				myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
				stoptimerGetSwitch();

				new GetSwitchAsyncTask().execute();
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
				stoptimerGetSwitch();
				new GetSwitchAsyncTask().execute();
			}
			else{
				nextPage++;
				Toast.makeText(mActivity, "数据已到底", Toast.LENGTH_LONG).show();
			}

			break;
			
		
		}
	}
}
