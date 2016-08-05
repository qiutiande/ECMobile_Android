package com.insthub.ecmobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Test4Activity extends Activity {
	private TextView mTextView;

	public void btnClick(View v) {
		mTextView.setText("update");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test4);
		mTextView = (TextView) findViewById(R.id.textView1);
		mTextView.setText("abc");

		System.out.println("测试添加代码");
	}

}
