package com.example.activitydata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	private EditText et_number;
	private EditText et_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_number = (EditText) findViewById(R.id.et_number);
		et_content = (EditText) findViewById(R.id.et_content);

	}

	public void selectContact(View view) {
		Intent intent = new Intent();
		intent.setClass(this, SelectContactActivity.class);
		// startActivity(intent);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (data != null) {
			String number = data.getStringExtra("number");
			et_number.setText(number);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void sendsms(View view) {
		String number = et_number.getText().toString().trim();
		String content = et_content.getText().toString().trim();
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(number, null, content, null, null);
		Toast.makeText(this, "发送成功", 0).show();
		finish();
	}

}
