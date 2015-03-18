package com.example.bcservice;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = new Intent(this,MyService.class);
		startService(intent);
	}

	public void call(View view) {
		//发送自定义广播
		
		Intent intent = new Intent();
		intent.setAction("com.example.callmethod");
		sendBroadcast(intent);
	}

}
