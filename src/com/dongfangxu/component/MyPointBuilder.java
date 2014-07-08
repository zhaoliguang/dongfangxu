package com.dongfangxu.component;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
/**
 * һ��ȷ����ȡ������ť����ʾ�ĶԻ���
 * @author lp
 *
 */
public class MyPointBuilder extends Builder{

	AlertDialog alertDialog;
	public MyPointBuilder(Context arg0) {
		super(arg0);
	}

	/**
	 * ����һ��ֻ��һ����ť�ĶԻ���������ʾ
	 * @param context  Context ����
	 * @param title  �Ի������
	 * @param message   �Ի�����Ϣ
	 * @param negativeButtonText  ��ť�ַ�
	 */
	public MyPointBuilder(Context context,String title,String message,String negativeButtonText){
		super(context);
		this.setTitle(title);
		this.setMessage(message);
		if(negativeButtonText!=null){
			this.setNegativeButton(negativeButtonText, null);
			alertDialog=this.create();
			//��ʾ�Ի���
			alertDialog.show();
		}
	}
	/**
	 * ����һ��ֻ��һ����ť�ĶԻ��򵫲�������ʾ
	 * @param context  Context ����
	 * @param title  �Ի������
	 * @param message   �Ի�����Ϣ
	 * @param negativeButtonText  ��ť�ַ�
	 * return MyPointBuilder
	 */
	public MyPointBuilder setDetailsWithOutShow(String title,String message,String negativeButtonText) {
		this.setTitle(title);
		this.setMessage(message);
		if(negativeButtonText!=null){
			this.setNegativeButton(negativeButtonText, null);
			this.alertDialog=this.create();
		}
		return this;
	}

	/**
	 * �رնԻ���
	 */
	public void closeDialog(){
		alertDialog.dismiss();
	}
}
