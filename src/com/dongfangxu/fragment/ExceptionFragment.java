package com.dongfangxu.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.dongfangxu.R;
import com.dongfangxu.activity.DongFangApp;
import com.dongfangxu.adapter.ExceptionListAdapter;
import com.dongfangxu.adapter.VoltageListAdapter;
import com.dongfangxu.component.MyPointBuilder;
import com.dongfangxu.component.MyProgressDialog;
import com.dongfangxu.fragment.VoltageFragment.GetVoltageAsyncTask;
import com.dongfangxu.fragment.VoltageFragment.GetVoltageByIdAsyncTask;
import com.dongfangxu.netservice.GetExceptionInfo;
import com.dongfangxu.netservice.GetVoltageInfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ExceptionFragment extends Fragment implements OnItemClickListener,OnClickListener{
	
	private View mParent;
	private FragmentActivity mActivity;
	private ListView listView;
	private View footview;
	private DongFangApp dongFangApp;
	private int dataCount,pageCount,currentPage=1,nextPage=1;
	private  int pageSize=10;
	private ImageButton previousButton,nextButton;

	private MyProgressDialog myProgressDialog;
	private ExceptionListAdapter exceptionListAdapter;
	
	private List< Map<String, Object>> mSelfData;  

	
	public static ExceptionFragment newInstance(int index) {
		ExceptionFragment exceptionFragment = new ExceptionFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		exceptionFragment.setArguments(args);

		return exceptionFragment;
	}
	
	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();
		mParent = getView();
		initview();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_exception, container, false);
		return view;
	}
	
	private void initview() {

		listView = (ListView) mParent.findViewById(R.id.lv_exception);
		footview=mActivity.getLayoutInflater().inflate(R.layout.listview_footview_loadmore, null);
		mSelfData=new ArrayList<Map<String,Object>>();
		//voltageAdapter = new VoltageAdapter(mActivity,mSelfData);
		exceptionListAdapter = new ExceptionListAdapter(mActivity, mSelfData);
		listView.setAdapter(exceptionListAdapter);
		listView.setOnItemClickListener(this);
		previousButton=(ImageButton)mParent.findViewById(R.id.previous_page);
		previousButton.setOnClickListener(this);
		nextButton=(ImageButton)mParent.findViewById(R.id.next_page);
		nextButton.setOnClickListener(this);

		myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
		new GetExceptionAsyncTask().execute();
		
	
	}
	
	public class GetExceptionAsyncTask extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			mSelfData.clear();
			int flag = GetExceptionInfo.getExceptionInfo(nextPage, mSelfData);
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			myProgressDialog.close();
			dataCount = exceptionListAdapter.getCount();
			pageCount=(dataCount/pageSize)+1;
			currentPage=nextPage;
			switch (flag) {
			case DongFangApp.SUCCESS:
				exceptionListAdapter.notifyDataSetChanged();
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
		
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		
		case R.id.next_page:
			nextPage++;
			if(nextPage<=pageCount){
				myProgressDialog=new MyProgressDialog(mActivity, "正在获取信息，请稍后...");
				new GetExceptionAsyncTask().execute();
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
				new GetExceptionAsyncTask().execute();
			}
			else{
				nextPage++;
				Toast.makeText(mActivity, "数据已到底", Toast.LENGTH_LONG).show();
			}

			break;
			
		}
		
	}

}
