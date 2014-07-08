package com.dongfangxu.fragment;

import com.dongfangxu.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * @author  sundroid
 * tab
 */
public class FragmentIndicator extends LinearLayout implements OnClickListener{

	private int mDefaultIndicator = 0;

	private static int mCurIndicator;

	private static LinearLayout[] mIndicators;

	private OnIndicateListener mOnIndicateListener;

	private static final String TAG_ICON_0 = "icon_tag_0";
	private static final String TAG_ICON_1 = "icon_tag_1";
	private static final String TAG_ICON_2 = "icon_tag_2";
//	private static final String TAG_ICON_3 = "icon_tag_3";


	private static final String TAG_TEXT_0 = "text_tag_0";
	private static final String TAG_TEXT_1 = "text_tag_1";
	private static final String TAG_TEXT_2 = "text_tag_2";
	//private static final String TAG_TEXT_3 = "text_tag_3";

	//color??
	private static final int COLOR_UNSELECT = Color.GRAY;
	//Color.argb(100, 0xff, 0xff, 0xff);
	private static final int COLOR_SELECT = Color.BLUE;

	private FragmentIndicator(Context context) {
		super(context);
	}

	public FragmentIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);

		mCurIndicator = mDefaultIndicator;
		setOrientation(LinearLayout.HORIZONTAL);
		init();
	}

	private LinearLayout createIndicator(int iconResID, int stringResID, int stringColor, 
			String iconTag, String textTag) {
		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		linearLayout.setGravity(Gravity.CENTER_VERTICAL);

		LinearLayout view = new LinearLayout(getContext());
		view.setOrientation(LinearLayout.VERTICAL);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		view.setGravity(Gravity.CENTER_HORIZONTAL);

		ImageView iconView = new ImageView(getContext());
		iconView.setTag(iconTag);
		iconView.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		iconView.setImageResource(iconResID);
		
		

		TextView textView =new TextView(getContext()); 
		textView.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		textView.setText(stringResID);
		view.addView(iconView);
		//view.addView(textView);
		linearLayout.addView(view);

		return linearLayout;

	}

	private ImageView getDevideView(){

		ImageView devideView = new ImageView(getContext());
//		devideView.setLayoutParams(new LinearLayout.LayoutParams(
//				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
//		devideView.setMaxHeight(40);
		devideView.setLayoutParams(new LinearLayout.LayoutParams(
				1, 45));
		devideView.setImageResource(R.drawable.ic_back_devide);
		return devideView;
	}
	
	private void init() {

		mIndicators = new LinearLayout[3];
		mIndicators[0] = createIndicator(R.drawable.ic_voltage,
				R.string.tab_home, COLOR_SELECT, TAG_ICON_0, TAG_TEXT_0);
		mIndicators[0].addView(getDevideView());
		//mIndicators[1].setBackgroundColor(Color.alpha(0));
		mIndicators[0].setTag(Integer.valueOf(0));
		mIndicators[0].setOnClickListener(this);
		addView(mIndicators[0]);

		mIndicators[1] = createIndicator(R.drawable.ic_switch,
				R.string.tab_inspection, COLOR_UNSELECT, TAG_ICON_1, TAG_TEXT_1);
		mIndicators[1].addView(getDevideView());
		mIndicators[1].setBackgroundColor(Color.alpha(0));
		mIndicators[1].setTag(Integer.valueOf(1));
		mIndicators[1].setOnClickListener(this);
		addView(mIndicators[1]);

		mIndicators[2] = createIndicator(R.drawable.ic_exception,
				R.string.tab_conserve, COLOR_UNSELECT, TAG_ICON_2, TAG_TEXT_2);
		mIndicators[2].addView(getDevideView());
		mIndicators[2].setBackgroundColor(Color.alpha(0));
		mIndicators[2].setTag(Integer.valueOf(2));
		mIndicators[2].setOnClickListener(this);
		addView(mIndicators[2]);
	}

	public static void setIndicator(int which) {
		mIndicators[mCurIndicator].setBackgroundColor(Color.alpha(0));
		ImageView prevIcon;
		//		TextView prevText;
		switch(mCurIndicator) {
		case 0:
			prevIcon =(ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_0);
			prevIcon.setImageResource(R.drawable.ic_voltage);
			//			prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_0);
			//			prevText.setTextColor(COLOR_UNSELECT);
			break;
		case 1:
			prevIcon =(ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_1);
			prevIcon.setImageResource(R.drawable.ic_switch);
			//			prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_1);
			//			prevText.setTextColor(COLOR_UNSELECT);
			break;
		case 2:
			prevIcon =(ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_2);
			prevIcon.setImageResource(R.drawable.ic_exception);
			//			prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_2);
			//			prevText.setTextColor(COLOR_UNSELECT);
			break;
//		case 3:
//			prevIcon =(ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_3);
//			prevIcon.setImageResource(R.drawable.ic_set_normal);
//			//			prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_3);
//			//			prevText.setTextColor(COLOR_UNSELECT);
//			break;

		}

		// update current status.
		//mIndicators[which].setBackgroundResource(R.drawable.indic_select);
		ImageView currIcon;
		TextView currText;
		switch(which) {
		case 0:
			currIcon =(ImageView) mIndicators[which].findViewWithTag(TAG_ICON_0);
			currIcon.setImageResource(R.drawable.ic_voltage_press);
						//currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_0);
						//currText.setTextColor(COLOR_SELECT);
			break;
		case 1:
			currIcon =(ImageView) mIndicators[which].findViewWithTag(TAG_ICON_1);
			currIcon.setImageResource(R.drawable.ic_switch_switch);
			//			currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_1);
			//			currText.setTextColor(COLOR_SELECT);
			break;
		case 2:
			currIcon =(ImageView) mIndicators[which].findViewWithTag(TAG_ICON_2);
			currIcon.setImageResource(R.drawable.ic_exception_press);
			//			currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_2);
			//			currText.setTextColor(COLOR_SELECT);
			break;
//		case 3:
//			currIcon =(ImageView) mIndicators[which].findViewWithTag(TAG_ICON_3);
//			currIcon.setImageResource(R.drawable.ic_set_focus);
//			//			currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_3);
//			//			currText.setTextColor(COLOR_SELECT);
//			break;
		}

		mCurIndicator = which;
	}

	public interface OnIndicateListener {
		public void onIndicate(View v, int which);
	}

	public void setOnIndicateListener(OnIndicateListener listener) {
		mOnIndicateListener = listener;
	}

	@Override
	public void onClick(View v) {
		if (mOnIndicateListener != null) {
			int tag = (Integer) v.getTag();
			switch (tag) {
			case 0:
				if (mCurIndicator != 0) {
					mOnIndicateListener.onIndicate(v, 0);
					setIndicator(0);
				}
				break;
			case 1:
				if (mCurIndicator != 1) {
					mOnIndicateListener.onIndicate(v, 1); 
					setIndicator(1);
				}
				break;
			case 2:
				if (mCurIndicator != 2) {
					mOnIndicateListener.onIndicate(v, 2);
					setIndicator(2);
				}
				break;
//			case 3:
//				if (mCurIndicator != 3) {
//					mOnIndicateListener.onIndicate(v, 3);
//					setIndicator(3);
//				}
//				break;
			default:
				break;
			}
		}
	}

}
