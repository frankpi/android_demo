package com.example.bcservice;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

	
	private MyReceiver receiver;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}
	private class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("我是serivce内部和广播接收者");
			serviceMethod();
		}
		
	}
	@Override
	public void onCreate() {
		//在清单文件中注册 receiver
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.callmethod");
		registerReceiver(receiver, filter);
		super.onCreate();
	}
	
	private void serviceMethod() {
		Toast.makeText(this, "我是服务方法", 0).show();
		
	}
	

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		receiver=null;
		super.onDestroy();
	}
	

}
